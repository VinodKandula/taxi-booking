package com.taxibooking.booking.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taxibooking.booking.model.Vehicle;

/**
 *
 *
 * @author vinodkandula
 */
@Transactional
public interface VehicleRepository extends JpaRepository<Vehicle, String> {
}
