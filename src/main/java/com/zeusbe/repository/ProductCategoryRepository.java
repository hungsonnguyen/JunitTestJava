package com.zeusbe.repository;

import com.zeusbe.model.Category;
import com.zeusbe.model.Product;
import com.zeusbe.model.ProductCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductCategory entity.
 */
@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    List<ProductCategory> findByCategory(Category category);
    List<ProductCategory> findByProduct(Product product);
    void deleteByProduct(Product product);
}
