package com.taxibooking.booking.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taxibooking.booking.model.Route;

/**
 *
 *
 * @author vinodkandula
 */
@Transactional
public interface RouteRepository extends JpaRepository<Route, Long> {
}
