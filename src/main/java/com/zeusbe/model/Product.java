package com.zeusbe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zeusbe.model.enumeration.Condition;
import com.zeusbe.model.enumeration.Status;
import com.zeusbe.model.enumeration.Unit;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A Product.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Unit unit;

    private String productName;

    private String productDescription;

    private String notice;

    private String location;

    private Boolean digitalDelivery;

    private Boolean localPickup;

    private String verifyPhone;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Condition productCondition;

    private Boolean active;

    private String shipping;

    private String thumbnail;

    private Integer point;

    private Integer requestCount;

    private Integer receiveCount;

    private Integer favoriteCount;

    private Boolean isExchanged;

    private Boolean isWaitingExchange;

    private Boolean isSwapAvailable;

    private Double latitude;

    private Double longitude;

    private ZonedDateTime createdDate;

    private ZonedDateTime updatedDate;

    @OneToMany(mappedBy = "product")
    @JsonIgnoreProperties(value = {"productImage", "product"}, allowSetters = true)
    private Set<Image> images = new HashSet<>();

    @OneToMany(mappedBy = "sendProduct")
    @JsonIgnoreProperties(value = {"sendProduct", "receiveProduct"}, allowSetters = true)
    private Set<Exchange> sendExchanges = new HashSet<>();

    @OneToMany(mappedBy = "receiveProduct")
    @JsonIgnoreProperties(value = {"sendProduct", "receiveProduct"}, allowSetters = true)
    private Set<Exchange> receiveExchanges = new HashSet<>();

    @OneToMany(mappedBy = "product")
    @JsonIgnoreProperties(value = {"category", "product"}, allowSetters = true)
    private Set<ProductCategory> productCategories = new HashSet<>();

    @OneToMany(mappedBy = "product")
    @JsonIgnoreProperties(value = {"product", "profile"}, allowSetters = true)
    private Set<Favorite> favorites = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = {"nationality", "user", "favorites", "products", "level"}, allowSetters = true)
    private Profile profile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"province"}, allowSetters = true)
    private City city;

}