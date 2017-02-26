package com.taxibooking.booking.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taxibooking.booking.model.Location;

/**
 * 
 *
 * @author vinodkandula
 */
@Transactional
public interface LocationRepository extends JpaRepository<Location, Long> {
}
