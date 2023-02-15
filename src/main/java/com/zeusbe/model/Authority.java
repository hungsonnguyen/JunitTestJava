package com.zeusbe.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

/**
 * An authority (a security role) used by Spring Security.
 */
@Entity
@Getter
@Setter
@Table(name = "jhi_authority")
public class Authority {

    @Id
    @Size(max = 50)
    @Column(length = 50)
    private String name;

}
