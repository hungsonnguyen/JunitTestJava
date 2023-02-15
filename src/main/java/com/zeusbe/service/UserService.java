package com.zeusbe.service;

import com.zeusbe.dto.request.ProfileDto;
import com.zeusbe.dto.request.SignInForm;
import com.zeusbe.dto.request.UserDto;
import com.zeusbe.dto.response.JwtResponse;
import com.zeusbe.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {

    Optional<User> getByUsername(String username);

    Boolean existsByLogin(String login);

    Boolean existsByEmail(String email);

    User save(UserDto userDto);

    User updateUser(ProfileDto profileDto);

    Optional<User> getCurrentUser();



}
