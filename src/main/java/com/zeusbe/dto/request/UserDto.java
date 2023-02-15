package com.zeusbe.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class    UserDto {
    @NotBlank
    @JsonIgnoreProperties("userName")
    private String userName;

    @NotBlank
    @Size(min = 1,max = 50)
    private String firstName;

    @NotBlank
    @Size(min = 1,max = 50)
    private String lastName;

    @Size(min = 5,max = 100)
    @NotBlank
    private String password;

    @Email
    private String email;

    @NotBlank
    private String phone;

    @NotNull
    private Long countryId;
}
