package com.zeusbe.model;

import com.zeusbe.model.enumeration.NotificationType;

import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;

    private String content;

    private Long senderId;

    private Long receiverId;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    private Long sendProductId;

    private Long receiveProductId;

    private ZonedDateTime createdDate;

    @Column(columnDefinition = "boolean default false")
    private Boolean isSeen;

}
