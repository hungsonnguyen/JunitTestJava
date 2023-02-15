package com.zeusbe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A City.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JsonIgnoreProperties(value = {"cities", "profiles", "products", "nationality"}, allowSetters = true)
    private Province province;

    @OneToMany(mappedBy = "city")
    @JsonIgnoreProperties(value = {"nationality", "user", "favorites", "products", "level"}, allowSetters = true)
    private Set<Profile> profiles = new HashSet<>();

    @OneToMany(mappedBy = "city")
    @JsonIgnoreProperties(
            value = {"images", "originExchanges", "currentExchanges", "productCategories", "favorites", "profile"},
            allowSetters = true
    )
    private Set<Product> products = new HashSet<>();

}