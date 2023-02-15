package com.zeusbe.service.impl;

import com.zeusbe.dto.request.ProfileDto;
import com.zeusbe.dto.request.UserDto;
import com.zeusbe.exception.RecordNotFoundException;
import com.zeusbe.model.*;
import com.zeusbe.repository.AuthorityRepository;
import com.zeusbe.repository.NationalityRepository;
import com.zeusbe.repository.ProfileRepository;
import com.zeusbe.repository.UserRepository;
import com.zeusbe.security.AuthoritiesConstants;
import com.zeusbe.security.jwt.JwtProvider;
import com.zeusbe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private NationalityRepository nationalityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthorityRepository authorityRepository;
    private static final String DEFAULT_AVATAR = "http://zeusbe.emclab.tech/api/app/image/download/138";
    private static final Double DEFAULT_BALANCE = 0d;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;


    @Override
    public Optional<User> getByUsername(String username) {
        return this.userRepository.findByLogin(username);
    }

    @Override
    public Boolean existsByLogin(String login) {
        return userRepository.existsByLogin(login);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User save(UserDto userDto) {

//        check userName is existing
        userRepository.findByLogin(userDto.getUserName().toLowerCase())
                .ifPresent(exitUser -> {
                    throw new RecordNotFoundException("userName is existing");
                });
//  check phone number is existing
        profileRepository.findOneByPhone(userDto.getPhone()).ifPresent(exitPhone -> {
            throw new RecordNotFoundException("The phone number is existing");

        });
        //         check email is existing
        if (existsByEmail(userDto.getEmail())) {
            throw new RecordNotFoundException("email is existing");
        }
//        get counrtry code
        String coutryCode = null;
        if (userDto.getCountryId() != null) {
            Optional<Nationality> nationality = nationalityRepository.findById(userDto.getCountryId());
            if (!nationality.isPresent()) {
                throw new RecordNotFoundException("code nationality is not found");
            }
            coutryCode = nationality.get().getCode();
        }

//        save user authorize
        String encryptedPassword = passwordEncoder.encode(userDto.getPassword());

        Set<Authority> authorities = new HashSet<>();

        authorityRepository.findById(AuthoritiesConstants.USER).ifPresent(authorities::add);
        boolean active = true;
//        create new user
        User newUser = new User();
        newUser.setFirstName(userDto.getFirstName());
        newUser.setLastName(userDto.getLastName());
        newUser.setLogin(userDto.getUserName());
        newUser.setPassword(encryptedPassword);
        newUser.setLangKey("EN");
        newUser.setAuthorities(authorities);
        newUser.setEmail(userDto.getEmail());
        newUser.setActivated(active);
        newUser.setCreatedBy("anonymousUser");
        newUser = userRepository.save(newUser);
//        create profile for new user
        Level level = new Level();
        level.setId(1L);
        String displayName = userDto.getFirstName() + " " + userDto.getLastName();
        Profile profile = new Profile();
        profile.setUser(newUser);
        profile.setPhone(userDto.getPhone());
        profile.setLevel(level);
        profile.setAvatar(DEFAULT_AVATAR);
        profile.setDisplayName(displayName);
        profile.setCountryCode(coutryCode);
        profileRepository.save(profile);

        return newUser;
    }

    @Override
    public User updateUser(ProfileDto profileDto) {
//        check userName is existing
        Optional<User> userCurrent = userRepository.findByLogin(profileDto.getUsername());
        if (!userRepository.existsByLogin(profileDto.getUsername())){
            throw  new RecordNotFoundException("user update is not existing");
        }
        //save user
        User user = userRepository.findByLogin(profileDto.getUsername()).get();
        user.setFirstName(profileDto.getFirstName());
        user.setLastName(profileDto.getLastName());
        user.setLogin(profileDto.getUsername());
        user.setEmail(profileDto.getEmail());
        user = userRepository.save(user);
        //save profile
        Optional<Profile> profile = profileRepository.findOneByUser(userCurrent.get());
        if (!profile.isPresent()) {
            throw new RecordNotFoundException("profile update not existing");
        }
        profile.get().setUser(user);
        profile.get().setDisplayName(profileDto.getDisplayName());
        profile.get().setAvatar(profileDto.getAvatar());
            profile.get().setBalance(DEFAULT_BALANCE);
        profileRepository.save(profile.get());
        return user;


    }

    @Override
    public Optional<User> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Optional<User> user = userRepository.findByLogin(userName);
        return user;
    }


}
