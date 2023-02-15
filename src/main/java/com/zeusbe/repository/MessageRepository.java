package com.zeusbe.repository;

import com.zeusbe.model.Message;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Message entity.
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {}
