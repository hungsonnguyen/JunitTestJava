package com.zeusbe.repository;

import com.zeusbe.model.Nationality;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Nationality entity.
 */
@Repository
public interface NationalityRepository extends JpaRepository<Nationality, Long> {}
