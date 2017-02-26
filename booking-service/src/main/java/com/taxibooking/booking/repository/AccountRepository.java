package com.taxibooking.booking.repository;

import com.taxibooking.booking.model.Account;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * A AccountRepository for handling and managing event
 * related data requested, updated, and processed in the application and
 * maintained in the database.
 *
 * @author vinodkandula
 */
@Transactional
public interface AccountRepository extends JpaRepository<Account, String> {
}
