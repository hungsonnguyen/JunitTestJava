package com.zeusbe.repository;

import com.zeusbe.model.Profile;
import com.zeusbe.model.Purchase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Purchase entity.
 */
@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    Page<Purchase> findByProfile(Profile profile, Pageable pageable);
}
