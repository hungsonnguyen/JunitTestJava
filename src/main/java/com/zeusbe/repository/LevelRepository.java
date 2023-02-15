package com.zeusbe.repository;

import com.zeusbe.model.Level;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Level entity.
 */
@Repository
public interface LevelRepository extends JpaRepository<Level, Long> {}
