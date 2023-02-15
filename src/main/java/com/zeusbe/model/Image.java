package com.zeusbe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A Image.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String link;

    private String text;

    @OneToOne
    @JoinColumn(unique = true)
    private File productImage;

    @ManyToOne
    @JsonIgnoreProperties(
            value = {"images", "originExchanges", "currentExchanges", "productCategories", "favorites", "profile"},
            allowSetters = true
    )
    private Product product;

}