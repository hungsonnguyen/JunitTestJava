package com.zeusbe.repository;

import com.zeusbe.model.Image;
import com.zeusbe.model.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Image entity.
 */
@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByProduct(Product product);
    void deleteByProduct(Product product);
}
