package com.example.tracking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TrackingRequestDTO {

	@NotNull(message = "Shipment ID is required")
	private Long shipmentId;

	@NotBlank(message = "Location is required")
	private String location;

	@NotBlank(message = "Status is required")
	private String status;

	public Long getShipmentId() {
		return shipmentId;
	}

	public void setShipmentId(Long shipmentId) {
		this.shipmentId = shipmentId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}