package com.zeusbe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Nationality.
 */
@Entity
@Getter
@Setter
public class Nationality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String flag;

    private String code;

    @OneToMany(mappedBy = "nationality")
    @JsonIgnoreProperties(value = {"nationality"}, allowSetters = true)
    private Set<Province> provinces = new HashSet<>();

}