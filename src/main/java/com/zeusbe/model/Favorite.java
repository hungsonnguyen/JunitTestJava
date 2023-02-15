package com.zeusbe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.ZonedDateTime;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A Favorite.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private ZonedDateTime createdDate;

    @ManyToOne
    @JsonIgnoreProperties(
            value = {"images", "originExchanges", "currentExchanges", "productCategories", "favorites", "profile"},
            allowSetters = true
    )
    private Product product;

    @ManyToOne
    @JsonIgnoreProperties(value = {"nationality", "user", "favorites", "products", "level"}, allowSetters = true)
    private Profile profile;

}