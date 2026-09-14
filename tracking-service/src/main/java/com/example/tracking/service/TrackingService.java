package com.example.tracking.service;

import com.example.tracking.dto.TrackingRequestDTO;
import com.example.tracking.dto.TrackingResponseDTO;

import java.util.List;

public interface TrackingService {

	TrackingResponseDTO createTracking(TrackingRequestDTO request);

	TrackingResponseDTO getTrackingById(Long id);

	List<TrackingResponseDTO> getAllTracking();

	TrackingResponseDTO updateTracking(Long id, TrackingRequestDTO request);

	void deleteTracking(Long id);
}