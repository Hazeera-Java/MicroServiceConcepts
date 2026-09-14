package com.example.shipment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shipment.entity.Shipment;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {

}
