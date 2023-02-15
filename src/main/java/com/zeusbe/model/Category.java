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
 * A Category.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String code;

    private String description;

    private String thumbnail;

    private Boolean active;

    private Integer parentId;

    @OneToOne
    @JoinColumn(unique = true)
    private File thumbnailFile;

    @OneToMany(mappedBy = "category")
    @JsonIgnoreProperties(value = {"category", "product"}, allowSetters = true)
    private Set<ProductCategory> productCategories = new HashSet<>();

}
