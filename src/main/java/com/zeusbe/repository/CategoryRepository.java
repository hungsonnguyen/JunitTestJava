package com.zeusbe.repository;

import com.zeusbe.model.Category;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Category entity.
 */

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByNameContainingIgnoreCase(String name);
    List<Category> findByActive(Boolean active);
    Optional<Category> findOneByNameIgnoreCase(String name);
}
