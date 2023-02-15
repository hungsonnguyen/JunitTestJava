package com.zeusbe.security.UserPrinciple;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zeusbe.model.Authority;
import com.zeusbe.model.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@AllArgsConstructor
public class UserPrinciple implements UserDetails {
    private Long id;
    private String login;
    @JsonIgnore
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private Boolean activated;
    private String langKey;
    private String imageUrl;
    @JsonIgnore
    private String activationKey;
    @JsonIgnore
    private String resetKey;
    private Instant resetDate = null;
    private Collection<? extends GrantedAuthority> authorities;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public UserPrinciple() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static UserPrinciple build(User user) {
        List<GrantedAuthority> authorityList = user.getAuthorities().stream().map(authority ->
                new SimpleGrantedAuthority(authority.getName())).collect(Collectors.toList());
        return new UserPrinciple(
                user.getId(),
                user.getLogin(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.isActivated(),
                user.getLangKey(),
                user.getImageUrl(),
                user.getActivationKey(),
                user.getResetKey(),
                user.getResetDate(),
                authorityList
        );
    }
}
