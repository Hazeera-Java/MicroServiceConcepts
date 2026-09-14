package com.example.shipment.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.shipment.client.UserClient;
import com.example.shipment.dto.ShipmentRequestDTO;
import com.example.shipment.dto.ShipmentResponseDTO;
import com.example.shipment.dto.UserResponse;
import com.example.shipment.entity.Shipment;
import com.example.shipment.event.ShipmentCreatedEvent;
import com.example.shipment.exception.ShipmentNotFoundException;
import com.example.shipment.kafka.ShipmentEventProducer;
import com.example.shipment.repository.ShipmentRepository;
import com.example.shipment.service.ShipmentService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class ShipmentServiceImpl implements ShipmentService {

	private static final Logger logger = LoggerFactory.getLogger(ShipmentServiceImpl.class);

	private final ShipmentRepository shipmentRepository;

	private final UserClient userClient;

	private final ShipmentEventProducer shipmentEventProducer;

	public ShipmentServiceImpl(ShipmentRepository shipmentRepository, UserClient userClient,
			ShipmentEventProducer shipmentEventProducer) {

		this.shipmentRepository = shipmentRepository;
		this.userClient = userClient;
		this.shipmentEventProducer = shipmentEventProducer;
	}

	@CircuitBreaker(name = "userService", fallbackMethod = "userServiceFallback")
	private UserResponse getUser(Long userId) {

		return userClient.getUserById(userId);
	}

	private UserResponse userServiceFallback(Long userId, Exception exception) {

		logger.error("User Service unavailable for userId: {}", userId, exception);

		throw new RuntimeException("User Service is currently unavailable");
	}

	@Override
	public ShipmentResponseDTO createShipment(ShipmentRequestDTO request) {

		logger.info("Creating shipment for userId: {}", request.getUserId());

		UserResponse userResponse = getUser(request.getUserId());

		logger.info("User received from User Service: id={}, name={}", userResponse.getId(), userResponse.getName());

		Shipment shipment = new Shipment();

		shipment.setUserId(userResponse.getId());
		shipment.setTrackingNumber(request.getTrackingNumber());
		shipment.setOrigin(request.getOrigin());
		shipment.setDestination(request.getDestination());
		shipment.setStatus(request.getStatus());

		Shipment savedShipment = shipmentRepository.save(shipment);

		ShipmentCreatedEvent event = new ShipmentCreatedEvent(savedShipment.getId(), savedShipment.getUserId(),
				savedShipment.getTrackingNumber(), savedShipment.getOrigin(), savedShipment.getDestination(),
				savedShipment.getStatus());

		shipmentEventProducer.publishShipmentCreated(event);

		logger.info("Shipment created successfully with id: {}", savedShipment.getId());

		return convertToResponse(savedShipment);
	}

	@Override
	public ShipmentResponseDTO getShipmentById(Long id) {

		logger.info("Fetching shipment with id: {}", id);

		Shipment shipment = shipmentRepository.findById(id).orElseThrow(() -> {

			logger.error("Shipment not found with id: {}", id);

			return new ShipmentNotFoundException("Shipment not found with id: " + id);
		});

		return convertToResponse(shipment);
	}

	@Override
	public List<ShipmentResponseDTO> getAllShipments() {

		logger.info("Fetching all shipments");

		return shipmentRepository.findAll().stream().map(this::convertToResponse).toList();
	}

	@Override
	public ShipmentResponseDTO updateShipment(Long id, ShipmentRequestDTO request) {

		logger.info("Updating shipment with id: {}", id);
		UserResponse userResponse = userClient.getUserById(request.getUserId());

		Shipment shipment = shipmentRepository.findById(id)
				.orElseThrow(() -> new ShipmentNotFoundException("Shipment not found with id: " + id));

		shipment.setUserId(userResponse.getId());
		shipment.setTrackingNumber(request.getTrackingNumber());
		shipment.setOrigin(request.getOrigin());
		shipment.setDestination(request.getDestination());
		shipment.setStatus(request.getStatus());

		Shipment updatedShipment = shipmentRepository.save(shipment);

		logger.info("Shipment updated successfully with id: {}", id);

		return convertToResponse(updatedShipment);
	}

	@Override
	public void deleteShipment(Long id) {

		logger.info("Deleting shipment with id: {}", id);

		Shipment shipment = shipmentRepository.findById(id)
				.orElseThrow(() -> new ShipmentNotFoundException("Shipment not found with id: " + id));

		shipmentRepository.delete(shipment);

		logger.info("Shipment deleted successfully with id: {}", id);
	}

	private ShipmentResponseDTO convertToResponse(Shipment shipment) {

		ShipmentResponseDTO response = new ShipmentResponseDTO();

		response.setId(shipment.getId());

		UserResponse userResponse = userClient.getUserById(shipment.getUserId());
		response.setUser(userResponse);

		response.setTrackingNumber(shipment.getTrackingNumber());
		response.setOrigin(shipment.getOrigin());
		response.setDestination(shipment.getDestination());
		response.setStatus(shipment.getStatus());

		return response;
	}
}