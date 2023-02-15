package com.zeusbe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;

/**
 * A ProductCategory.
 */
@Entity
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @ManyToOne
    @JsonIgnoreProperties(value = { "thumbnailFile", "productCategories" }, allowSetters = true)
    private Category category;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "images", "originExchanges", "currentExchanges", "productCategories", "favorites", "profile" },
        allowSetters = true
    )
    private Product product;

}
