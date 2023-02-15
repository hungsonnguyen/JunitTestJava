package com.zeusbe.repository;

import com.zeusbe.model.Profile;
import com.zeusbe.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Profile entity.
 */
@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findOneByPhone(String phone);

    Optional<Profile> findOneByUser(User user);

    Optional<Profile> findOneByUid(String uid);

    boolean existsByUserAndPhone(User user, String phone);

    boolean existsByPhone(String phone);
}
