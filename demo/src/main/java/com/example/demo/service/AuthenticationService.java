package com.example.demo.service;


import com.example.demo.entity.user.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(String userName, String email, String password){
        if(userRepository.findUserByEmail(email).isPresent()){
            throw new RuntimeException("email alınmış!");
        }
        if(userRepository.findUserByUserName(userName).isPresent()){
            throw new RuntimeException("kullanıcı adı alınmış!");
        }
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User();
        user.setUserName(userName);
        user.setEmail(email);
        user.setPassword(encodedPassword);

        return userRepository.save(user);
    }

}
