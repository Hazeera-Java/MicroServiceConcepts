package com.example.tracking.controller;

import com.example.tracking.dto.TrackingRequestDTO;
import com.example.tracking.dto.TrackingResponseDTO;
import com.example.tracking.service.TrackingService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tracking")
public class TrackingController {

	private final TrackingService trackingService;

	public TrackingController(TrackingService trackingService) {
		this.trackingService = trackingService;
	}

	@PostMapping
	public ResponseEntity<TrackingResponseDTO> createTracking(@Valid @RequestBody TrackingRequestDTO request) {

		TrackingResponseDTO response = trackingService.createTracking(request);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<TrackingResponseDTO> getTrackingById(@PathVariable Long id) {

		return ResponseEntity.ok(trackingService.getTrackingById(id));
	}

	@GetMapping
	public ResponseEntity<List<TrackingResponseDTO>> getAllTracking() {

		return ResponseEntity.ok(trackingService.getAllTracking());
	}

	@PutMapping("/{id}")
	public ResponseEntity<TrackingResponseDTO> updateTracking(@PathVariable Long id,
			@Valid @RequestBody TrackingRequestDTO request) {

		return ResponseEntity.ok(trackingService.updateTracking(id, request));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTracking(@PathVariable Long id) {

		trackingService.deleteTracking(id);

		return ResponseEntity.noContent().build();
	}
}