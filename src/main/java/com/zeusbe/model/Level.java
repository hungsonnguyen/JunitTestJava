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
 * A Level.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Level {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String levelName;

    @Column(columnDefinition = "integer default 0")
    private Integer limitSwap;

    @OneToMany(mappedBy = "level")
    @JsonIgnoreProperties(value = { "nationality", "user", "favorites", "products", "level" }, allowSetters = true)
    private Set<Profile> profiles = new HashSet<>();

}
