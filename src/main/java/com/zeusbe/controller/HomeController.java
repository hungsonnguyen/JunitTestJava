package com.zeusbe.controller;

import com.zeusbe.model.User;
import com.zeusbe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("api")
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("home")
    public String home() {
        return "Hello word";
    }

    @GetMapping("user/{username}")
    public ResponseEntity<User> getByUsername(@PathVariable String username) {
        Optional<User> userOptional = this.userService.getByUsername(username);

        return userOptional.map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

}
