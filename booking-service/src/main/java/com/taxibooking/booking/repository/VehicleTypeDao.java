package com.taxibooking.booking.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taxibooking.booking.model.VehicleType;

/**
 *
 *
 * @author vinodkandula
 */
@Transactional
public interface VehicleTypeDao extends JpaRepository<VehicleType, Long> {
}
