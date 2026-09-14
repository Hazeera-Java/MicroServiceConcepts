package com.example.shipment.event;

public class ShipmentCreatedEvent {

	private Long shipmentId;
	private Long userId;
	private String trackingNumber;
	private String origin;
	private String destination;
	private String status;

	public ShipmentCreatedEvent() {
	}

	public ShipmentCreatedEvent(Long shipmentId, Long userId, String trackingNumber, String origin, String destination,
			String status) {

		this.shipmentId = shipmentId;
		this.userId = userId;
		this.trackingNumber = trackingNumber;
		this.origin = origin;
		this.destination = destination;
		this.status = status;
	}

	public Long getShipmentId() {
		return shipmentId;
	}

	public void setShipmentId(Long shipmentId) {
		this.shipmentId = shipmentId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}