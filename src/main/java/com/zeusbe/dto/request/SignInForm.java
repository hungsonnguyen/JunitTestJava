package com.zeusbe.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignInForm {

    @NotNull
    @NotBlank(message = "userName must not be blank")
    private String userName;
    @NotNull
    @NotBlank(message = "password must not be blank")
    private String password;

    private boolean rememberMe;


}
