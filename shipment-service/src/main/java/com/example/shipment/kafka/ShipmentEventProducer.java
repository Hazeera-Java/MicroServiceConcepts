package com.example.shipment.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.shipment.event.ShipmentCreatedEvent;

@Service
public class ShipmentEventProducer {

	private static final Logger logger = LoggerFactory.getLogger(ShipmentEventProducer.class);

	private static final String TOPIC = "shipment-created";

	private final KafkaTemplate<String, ShipmentCreatedEvent> kafkaTemplate;

	public ShipmentEventProducer(KafkaTemplate<String, ShipmentCreatedEvent> kafkaTemplate) {

		this.kafkaTemplate = kafkaTemplate;
	}

	public void publishShipmentCreated(ShipmentCreatedEvent event) {

		logger.info("Publishing ShipmentCreatedEvent for shipmentId: {}", event.getShipmentId());

		kafkaTemplate.send(TOPIC, String.valueOf(event.getShipmentId()), event);

		logger.info("ShipmentCreatedEvent published successfully for shipmentId: {}", event.getShipmentId());
	}
}