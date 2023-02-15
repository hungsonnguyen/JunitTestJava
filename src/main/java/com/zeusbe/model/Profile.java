package com.zeusbe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A Profile.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Profile{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String displayName;

    private Integer accessTime;

    @Column(columnDefinition = "Double default 0")
    private Double balance;

    private Integer pointsAvailable;

    private Integer pointsUsed;

    private Double latitude;

    private Double longitude;

    private String avatar;

    @Column(columnDefinition = "varchar(50) default '+84'")
    private String countryCode;

    @Column(columnDefinition = "varchar(255) default ''")
    private String location;

    @Column(unique = true)
    private String phone;

    @Column(unique = true)
    private String uid;

    private ZonedDateTime dob;

    private Long updatedBy;

    private ZonedDateTime updatedDate;

    @JsonIgnoreProperties(value = { "province" }, allowSetters = true)
    @ManyToOne
    private City city;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "profile")
    @JsonIgnoreProperties(value = { "product", "profile" }, allowSetters = true)
    private Set<Favorite> favorites = new HashSet<>();

    @OneToMany(mappedBy = "profile")
    @JsonIgnoreProperties(
        value = { "images", "originExchanges", "currentExchanges", "productCategories", "favorites", "profile" },
        allowSetters = true
    )
    private Set<Product> products = new HashSet<>();

    @OneToMany(mappedBy = "profile")
    @JsonIgnoreProperties(value = {}, allowSetters = true)
    private Set<Purchase> purchases = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "purchases", "profiles" }, allowSetters = true)
    private Level level;

    @OneToMany(mappedBy = "profile")
    @JsonIgnoreProperties(value = {}, allowSetters = true)
    private Set<NotificationToken> notificationToken = new HashSet<>();

}
