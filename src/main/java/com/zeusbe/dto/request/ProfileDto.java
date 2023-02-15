package com.zeusbe.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto {
    private String firstName;
    private String lastName;
    private String displayName;
    private String avatar;
    private String phone;
    private String email;
    private String username;
    private String location;
    private ZonedDateTime dob;
    private Long cityId;
}
