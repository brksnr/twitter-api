package com.example.demo.controller;


import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.RegisterUser;
import com.example.demo.entity.user.User;
import com.example.demo.exceptions.ApiException;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AuthController {


    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    @Autowired
    public AuthController(AuthenticationService authenticationService, JwtService jwtService) {
        this.authenticationService = authenticationService;
        this.jwtService = jwtService;
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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        User user = authenticationService.login(loginRequest.email(), loginRequest.password());

        if(user != null){
            String token = jwtService.generateToken(user.getEmail());

            LoginResponse loginResponse = new LoginResponse(loginRequest.email(), token);
            return ResponseEntity.ok(loginResponse);
        } else {
            throw new ApiException("Kullanıcı mevcut değil!", HttpStatus.BAD_REQUEST);
        }
    }
}
