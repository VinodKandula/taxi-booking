package com.taxibooking.booking.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taxibooking.booking.model.Account;
import com.taxibooking.booking.model.taxi.Taxi;

/**
 * 
 *
 * @author vinodkandula
 */
@Transactional
public interface TaxiRepository extends JpaRepository<Taxi, Long> {

	public Taxi findByAccount(Account account);

}
