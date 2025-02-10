package com.example.demo.service;


import com.example.demo.entity.user.User;
import com.example.demo.exceptions.ApiException;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Optional;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Autowired
    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public User register(String userName, String email, String password) {
        if (userName == null || userName.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
            throw new ApiException("Tüm alanları doldurunuz!", HttpStatus.BAD_REQUEST);
        }

        if (userRepository.findUserByEmail(email).isPresent()) {
            throw new ApiException("Bu email başka bir kullanıcı tarafından alınmış!", HttpStatus.BAD_REQUEST);
        }
        if (userRepository.findUserByUserName(userName).isPresent()) {
            throw new ApiException("Bu username başka bir kullanıcı tarafından alınmış!", HttpStatus.BAD_REQUEST);
        }

        String encodedPassword = passwordEncoder.encode(password);
        User user = new User();
        user.setUserName(userName);
        user.setEmail(email);
        user.setPassword(encodedPassword);

        return userRepository.save(user);
    }


    public User login(String email, String password){
        Optional<User> userOptional = userRepository.findUserByEmail(email);
        if(!userOptional.isPresent()){
            throw new ApiException("Bilgilere ait kullanıcı bulunamadı!", HttpStatus.BAD_REQUEST);
        }
        User user = userOptional.get();
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new ApiException("Email veye Şifre hatalı!", HttpStatus.BAD_REQUEST);
        }
        return user;
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyToken(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);

        if (jwtService.validateToken(token)) {
            String username = jwtService.extractUsername(token);
            return ResponseEntity.ok("Token valid for user: " + username);
        } else {
            return ResponseEntity.status(401).body("Invalid token");
        }
    }

}
