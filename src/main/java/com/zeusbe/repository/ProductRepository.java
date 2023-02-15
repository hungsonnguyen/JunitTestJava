package com.zeusbe.repository;

import com.zeusbe.model.Product;
import com.zeusbe.model.Profile;
import com.zeusbe.model.enumeration.Status;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Product entity.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    List<Product> findByIdIn(List<Long> ids);

    List<Product> findByProductNameContains(String name);

    List<Product> findByIdInAndProductNameContains(List<Long> ids, String name);

    List<Product> findByIdIn(List<Long> ids, Pageable pageable);

    List<Product> findByProductNameContains(String name, Pageable pageable);

    List<Product> findByIdInAndProductNameContains(List<Long> ids, String name, Pageable pageable);

    List<Product> findByProfile(Profile profile);

    List<Product> findByProfile(Profile profile, Pageable pageable);

    List<Product> findByProfileAndStatus(Profile profile, Status status, Pageable pageable);

    List<Product> findByIdAndProfile(Long productId, Profile profile);

    @Query(
            value = "select p.* from product p\n" +
                    "inner join product_category pc on p.id = pc.product_id\n" +
                    "inner join category c on pc.category_id = c.id\n" +
                    "where p.status = 'AVAILABLE' and category_id IN :categoryIds and p.id <> :productId and p.profile_id <> :profileId \n" +
                    "order by created_date desc\n" +
                    "limit 10",
            nativeQuery = true
    )
    List<Product> findSimilarProducts(
            @Param("categoryIds") Collection<Long> categoryIds,
            @Param("productId") Long productId,
            @Param("profileId") Long profileId
    );

    @Query(value = "select distinct location from product order by location asc", nativeQuery = true)
    List<String> findAllProductLocations();
}
