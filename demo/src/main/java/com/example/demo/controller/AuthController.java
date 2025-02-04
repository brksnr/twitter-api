package com.example.demo.controller;


import com.example.demo.dto.RegisterUser;
import com.example.demo.entity.user.User;
import com.example.demo.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AuthController {


    private final AuthenticationService authenticationService;

    @Autowired
    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterUser registerUser){

         authenticationService
                .register(registerUser.userName(), registerUser.email(), registerUser.password());
        User users = new User();
        users.setUserName(registerUser.userName());
        users.setEmail(registerUser.email());
        return ResponseEntity.ok(users);
    }
}
