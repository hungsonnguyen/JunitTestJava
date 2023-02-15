package com.zeusbe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zeusbe.model.enumeration.ConfirmStatus;
import com.zeusbe.model.enumeration.ExchangeStatus;
import com.zeusbe.model.enumeration.ExchangeUser;

import java.time.ZonedDateTime;
import javax.persistence.*;

import lombok.*;

/**
 * A Exchange.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Exchange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ownerId;

    @Column(columnDefinition = "boolean default true")
    private Boolean active;

    @Column(columnDefinition = "boolean default false")
    private Boolean chatting;

    private String comment;

    @Enumerated(EnumType.STRING)
    private ExchangeStatus status;

    @Enumerated(EnumType.STRING)
    private ExchangeUser exchangeUser;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(15) default 'ACCEPT'")
    private ConfirmStatus ownerConfirm;

    @Enumerated(EnumType.STRING)
    @Column( columnDefinition = "varchar(15) default 'WAITING'")
    private ConfirmStatus exchangerConfirm;

    private Long exchangerId;

    private String exchangeLocation;

    private String confirmPhone;

    private ZonedDateTime createdDate;

    private Long createdBy;

    private ZonedDateTime updatedDate;

    private Long updatedBy;

    private String evidenceImg;

    @ManyToOne
    @JsonIgnoreProperties(
            value = {"images", "originExchanges", "currentExchanges", "productCategories", "favorites", "profile"},
            allowSetters = true
    )
    private Product sendProduct;

    @ManyToOne
    @JsonIgnoreProperties(
            value = {"images", "originExchanges", "currentExchanges", "productCategories", "favorites", "profile"},
            allowSetters = true
    )
    private Product receiveProduct;
}
