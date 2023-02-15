package com.zeusbe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Province.
 */
@Entity
public class Province {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JsonIgnoreProperties(value = {"provinces"}, allowSetters = true)
    private Nationality nationality;

    @OneToMany(mappedBy = "province")
    @JsonIgnoreProperties(value = {"province"}, allowSetters = true)
    private Set<City> cities = new HashSet<>();

}
