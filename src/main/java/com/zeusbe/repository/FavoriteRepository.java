package com.zeusbe.repository;

import com.zeusbe.model.Favorite;
import com.zeusbe.model.Product;
import com.zeusbe.model.Profile;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Favorite entity.
 */

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Favorite findByProductAndProfile(Product product, Profile profile);

    List<Favorite> findByProfile(Profile profile, Pageable pageable);

    List<Favorite> findByProfile(Profile profile);

    void deleteByProduct(Product product);

    boolean existsByProductAndProfile(Product product, Profile profile);
}
