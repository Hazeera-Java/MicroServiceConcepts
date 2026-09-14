package com.example.shipment.controller;

import com.example.shipment.dto.ShipmentRequestDTO;
import com.example.shipment.dto.ShipmentResponseDTO;
import com.example.shipment.service.ShipmentService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipments")
public class ShipmentController {

	private final ShipmentService shipmentService;

	public ShipmentController(ShipmentService shipmentService) {

		this.shipmentService = shipmentService;
	}

	@PostMapping
	public ResponseEntity<ShipmentResponseDTO> createShipment(@Valid @RequestBody ShipmentRequestDTO request) {

		ShipmentResponseDTO response = shipmentService.createShipment(request);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ShipmentResponseDTO> getShipmentById(@PathVariable Long id) {

		return ResponseEntity.ok(shipmentService.getShipmentById(id));
	}

	@GetMapping
	public ResponseEntity<List<ShipmentResponseDTO>> getAllShipments() {

		return ResponseEntity.ok(shipmentService.getAllShipments());
	}

	@PutMapping("/{id}")
	public ResponseEntity<ShipmentResponseDTO> updateShipment(@PathVariable Long id,
			@Valid @RequestBody ShipmentRequestDTO request) {

		return ResponseEntity.ok(shipmentService.updateShipment(id, request));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteShipment(@PathVariable Long id) {

		shipmentService.deleteShipment(id);

		return ResponseEntity.noContent().build();
	}
}