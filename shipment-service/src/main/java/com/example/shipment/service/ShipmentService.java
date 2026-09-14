package com.example.shipment.service;

import java.util.List;

import com.example.shipment.dto.ShipmentRequestDTO;
import com.example.shipment.dto.ShipmentResponseDTO;

public interface ShipmentService {

	ShipmentResponseDTO createShipment(
            ShipmentRequestDTO request);

    ShipmentResponseDTO getShipmentById(Long id);

    List<ShipmentResponseDTO> getAllShipments();

    ShipmentResponseDTO updateShipment(
            Long id,
            ShipmentRequestDTO request);

    void deleteShipment(Long id);
}