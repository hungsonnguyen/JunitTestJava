package com.zeusbe.controller;

import com.zeusbe.dto.request.*;
import com.zeusbe.dto.response.JwtResponse;
import com.zeusbe.exception.ErrorResponse;
import com.zeusbe.exception.RecordNotFoundException;
import com.zeusbe.model.Nationality;
import com.zeusbe.model.User;
import com.zeusbe.repository.NationalityRepository;
import com.zeusbe.repository.UserRepository;
import com.zeusbe.security.UserPrinciple.UserPrinciple;
import com.zeusbe.security.jwt.JwtProvider;
import com.zeusbe.service.UserService;
import com.zeusbe.service.impl.EmailSenderServiceImpl;
import com.zeusbe.service.impl.OtpService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
@CrossOrigin("*")
@RequestMapping("api/user")
@Slf4j
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    EmailSenderServiceImpl emailSenderService;

    @Autowired
    OtpService otpService;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    NationalityRepository nationalityRepository;


    @PostMapping("signUp")
    public ResponseEntity<User> singUp(@Valid @RequestBody UserDto userDto) {
        User user = userService.save(userDto);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("nationality")
    public ResponseEntity<List<Nationality>> getNationality() {
        List<Nationality> nationalityList = nationalityRepository.findAll();
        return new ResponseEntity<>(nationalityList, HttpStatus.OK);
    }

    @PostMapping("/signIn")
    public ResponseEntity<JwtResponse> singIn(@Valid @RequestBody SignInForm signInForm) {
        Authentication authentication;
        String jwt = null;
        String status = "";
        String username = "";
        String token = "";
        HttpStatus httpStatus = null;
        List<String> roles = new ArrayList<>();
        if (!userService.existsByLogin(signInForm.getUserName())) {
            status = "Username not exists";
            httpStatus = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(new JwtResponse(token, username, status, roles), httpStatus);
        }
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            signInForm.getUserName(),
                            signInForm.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtProvider.createTokken(authentication);
            UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
            username = userPrinciple.getUsername();
            System.out.println("123" + token);
            Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();


            System.out.println(authentication1);
            authentication = SecurityContextHolder.getContext().getAuthentication();
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                roles.add(authority.getAuthority());
            }
            status = "Success";
            httpStatus = HttpStatus.OK;

        } catch (DisabledException disabledException) {
            status = "Account locked";
            httpStatus = HttpStatus.BAD_REQUEST;
        } catch (BadCredentialsException badCredentialsException) {
            status = "Wrong password";
            httpStatus = HttpStatus.BAD_REQUEST;
        } catch (InternalAuthenticationServiceException internalAuthenticationServiceException) {
            status = "Username not exists";
            httpStatus = HttpStatus.BAD_REQUEST;
        } catch (Exception exception) {
            status = "Error server";
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(new JwtResponse(token, username, status, roles), httpStatus);
    }


    @PostMapping("update")
    public ResponseEntity<User> updateProfile(@Valid @RequestBody ProfileDto profileDto) {

        User user = userService.updateUser(profileDto);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("send-otp-forgot-password")
    public ResponseEntity<Boolean> sendCodeResetPassword(@RequestBody RequestResetPassword userNameResetPass) {
        Optional<User> user = userService.getByUsername(userNameResetPass.getUserName());
        if (!user.isPresent()) {
            throw new RecordNotFoundException("not found user");
        }
        AtomicBoolean isCheck = new AtomicBoolean(false);
        userService.getByUsername(userNameResetPass.getUserName())
                .ifPresent((value) -> {
                            String otp = otpService.generateOTP(userNameResetPass.getUserName());
                            boolean isOtp = emailSenderService.sendEmail(value.getEmail(), "this is subject", otp);
                            if (isOtp) {
                                isCheck.set(true);
                            } else {
                                isCheck.set(false);
                            }
                        }
                );
        if (isCheck.get()) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("change-password")
    public ResponseEntity<Boolean> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
        Optional<User> user = userService.getByUsername(resetPasswordDto.getUsername());
        AtomicBoolean isCheck = new AtomicBoolean(false);
        if (!user.isPresent()) {
            throw new RecordNotFoundException("not found user");
        } else {
            String otpServer = otpService.getOTP(resetPasswordDto.getUsername());
            if (resetPasswordDto.getOtp().equals(otpServer)) {
                user.get().setPassword(passwordEncoder.encode(resetPasswordDto.getNewPassword()));
                userRepository.save(user.get());
                otpService.ClearOTP(resetPasswordDto.getUsername());
                isCheck.set(true);
            } else {
                isCheck.set(false);
            }
        }
        if (isCheck.get()) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("detail/{username}")
    public ResponseEntity<User> createUser(@PathVariable("username") String username) {
        log.debug("Get profile by uid : ", username);
        Optional<User> user = userService.getByUsername(username);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            throw new RecordNotFoundException("not found user");
        }
    }

    //    Handle Exception
    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(RecordNotFoundException ex) {
        Map<String, String> details = new HashMap<>();
        details.put("error", ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse("Record Not Found", false, details);
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ErrorResponse errorResponse = new ErrorResponse("error validation", false, errors);
        return errorResponse;
    }


    // Bắt lỗi server
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public Map<String, String> handleValidationExceptions(
//            MethodArgumentNotValidException ex) {
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getAllErrors().forEach((error) -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        });
//        return errors;
//    }

}
