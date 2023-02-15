package com.zeusbe.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.sql.Struct;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String status;
    private String name;
    private List<String> authorities;

    public JwtResponse(String token, String name,String status, List<String> authorities) {
        this.token = token;
        this.name = name;
        this.status = status;
        this.authorities = authorities;
    }
}
