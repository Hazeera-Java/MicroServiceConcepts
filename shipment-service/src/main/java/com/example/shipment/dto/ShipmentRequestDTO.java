package com.example.shipment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ShipmentRequestDTO {

	@NotNull(message = "User ID is required")
	private Long userId;

	@NotBlank(message = "Tracking number is required")
	@Size(min = 5, max = 30, message = "Tracking number must be between 5 and 30 characters")
	private String trackingNumber;

	@NotBlank(message = "Origin is required")
	private String origin;

	@NotBlank(message = "Destination is required")
	private String destination;

	@NotBlank(message = "Status is required")
	private String status;

	public ShipmentRequestDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ShipmentRequestDTO(Long userId, String trackingNumber, String origin, String destination, String status) {
		super();
		this.userId = userId;
		this.trackingNumber = trackingNumber;
		this.origin = origin;
		this.destination = destination;
		this.status = status;
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
