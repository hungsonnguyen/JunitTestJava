package com.zeusbe.repository;

import com.zeusbe.model.NotificationToken;
import com.zeusbe.model.Profile;
import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationTokenRepository extends JpaRepository<NotificationToken, Long> {
    List<NotificationToken> findByProfile(Profile profile);

    @Modifying
    @Query(value = "DELETE FROM notification_token WHERE token = ?1 AND profile_id = ?2", nativeQuery = true)
    void deleteByTokenAndProfileId(String token, Long profileId);

    boolean existsByTokenAndProfile(String token, Profile profile);
}
