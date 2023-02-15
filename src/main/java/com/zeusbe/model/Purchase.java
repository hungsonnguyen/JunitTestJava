package com.zeusbe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zeusbe.model.enumeration.MoneyUnit;
import com.zeusbe.model.enumeration.PurchaseType;

import java.time.ZonedDateTime;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A Purchase.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PurchaseType purchaseType;

    private Double money;

    @Enumerated(EnumType.STRING)
    private MoneyUnit unit;

    @Column(columnDefinition = "boolean default true")
    private Boolean isConfirmed;

    private ZonedDateTime dateConfirmed;

    @ManyToOne
    @JsonIgnoreProperties(value = {"nationality", "user", "favorites", "products", "level"}, allowSetters = true)
    private Profile profile;

}
