package com.example.shipment.dto;

public class ShipmentResponseDTO {

	private Long id;
	// private Long userId;
	private String trackingNumber;
	private String origin;
	private String destination;
	private String status;

	private UserResponse user;

	public ShipmentResponseDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ShipmentResponseDTO(Long id, UserResponse user, String trackingNumber, String origin, String destination,
			String status) {
		super();
		this.id = id;
		// this.userId = userId;
		this.user = user;
		this.trackingNumber = trackingNumber;
		this.origin = origin;
		this.destination = destination;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/*
	 * public Long getUserId() { return userId; }
	 * 
	 * public void setUserId(Long userId) { this.userId = userId; }
	 */

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public UserResponse getUser() {
		return user;
	}

	public void setUser(UserResponse user) {
		this.user = user;
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