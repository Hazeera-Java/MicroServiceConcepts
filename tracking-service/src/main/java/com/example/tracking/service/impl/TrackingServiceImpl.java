package com.example.tracking.service.impl;

import com.example.tracking.dto.TrackingRequestDTO;
import com.example.tracking.dto.TrackingResponseDTO;
import com.example.tracking.entity.Tracking;
import com.example.tracking.exception.TrackingNotFoundException;
import com.example.tracking.repository.TrackingRepository;
import com.example.tracking.service.TrackingService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackingServiceImpl implements TrackingService {

	private static final Logger logger = LoggerFactory.getLogger(TrackingServiceImpl.class);

	private final TrackingRepository trackingRepository;

	public TrackingServiceImpl(TrackingRepository trackingRepository) {
		this.trackingRepository = trackingRepository;
	}

	@Override
	public TrackingResponseDTO createTracking(TrackingRequestDTO request) {

		logger.info("Creating tracking for shipmentId: {}", request.getShipmentId());

		Tracking tracking = new Tracking();

		tracking.setShipmentId(request.getShipmentId());
		tracking.setLocation(request.getLocation());
		tracking.setStatus(request.getStatus());

		Tracking savedTracking = trackingRepository.save(tracking);

		logger.info("Tracking created successfully with id: {}", savedTracking.getId());

		return convertToResponse(savedTracking);
	}

	@Override
	public TrackingResponseDTO getTrackingById(Long id) {

		logger.info("Fetching tracking with id: {}", id);

		Tracking tracking = trackingRepository.findById(id).orElseThrow(() -> {
			logger.error("Tracking not found with id: {}", id);

			return new TrackingNotFoundException("Tracking not found with id: " + id);
		});

		return convertToResponse(tracking);
	}

	@Override
	public List<TrackingResponseDTO> getAllTracking() {

		logger.info("Fetching all tracking records");

		return trackingRepository.findAll().stream().map(this::convertToResponse).toList();
	}

	@Override
	public TrackingResponseDTO updateTracking(Long id, TrackingRequestDTO request) {

		logger.info("Updating tracking with id: {}", id);

		Tracking tracking = trackingRepository.findById(id).orElseThrow(() -> {
			logger.error("Tracking not found with id: {}", id);

			return new TrackingNotFoundException("Tracking not found with id: " + id);
		});

		tracking.setShipmentId(request.getShipmentId());
		tracking.setLocation(request.getLocation());
		tracking.setStatus(request.getStatus());

		Tracking updatedTracking = trackingRepository.save(tracking);

		logger.info("Tracking updated successfully with id: {}", id);

		return convertToResponse(updatedTracking);
	}

	@Override
	public void deleteTracking(Long id) {

		logger.info("Deleting tracking with id: {}", id);

		Tracking tracking = trackingRepository.findById(id).orElseThrow(() -> {
			logger.error("Tracking not found with id: {}", id);

			return new TrackingNotFoundException("Tracking not found with id: " + id);
		});

		trackingRepository.delete(tracking);

		logger.info("Tracking deleted successfully with id: {}", id);
	}

	private TrackingResponseDTO convertToResponse(Tracking tracking) {

		TrackingResponseDTO response = new TrackingResponseDTO();

		response.setId(tracking.getId());
		response.setShipmentId(tracking.getShipmentId());
		response.setLocation(tracking.getLocation());
		response.setStatus(tracking.getStatus());

		return response;
	}
}