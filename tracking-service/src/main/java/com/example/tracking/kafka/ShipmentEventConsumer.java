package com.example.tracking.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.tracking.entity.Tracking;
import com.example.tracking.event.ShipmentCreatedEvent;
import com.example.tracking.repository.TrackingRepository;

@Service
public class ShipmentEventConsumer {

	private static final Logger logger = LoggerFactory.getLogger(ShipmentEventConsumer.class);

	private final TrackingRepository trackingRepository;

	public ShipmentEventConsumer(TrackingRepository trackingRepository) {

		this.trackingRepository = trackingRepository;
	}

	@KafkaListener(topics = "shipment-created", groupId = "tracking-service-group")
	public void consumeShipmentCreated(ShipmentCreatedEvent event) {

		logger.info("Received ShipmentCreatedEvent for shipmentId: {}", event.getShipmentId());

		Tracking tracking = new Tracking();

		tracking.setShipmentId(event.getShipmentId());

		tracking.setStatus(event.getStatus());

		tracking.setLocation(event.getOrigin());

		trackingRepository.save(tracking);

		logger.info("Initial tracking record created for shipmentId: {}", event.getShipmentId());
	}
}