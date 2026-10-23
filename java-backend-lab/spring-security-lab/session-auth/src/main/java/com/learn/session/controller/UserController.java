package com.learn.session.controller;
import com.learn.session.repository.UserRepository;
import com.learn.session.service.JwtService;

import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.learn.session.entity.UserDetailsEntity;
import com.learn.session.model.UserModel;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping ("/users") 
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    UserController(
        UserRepository userRepository, 
        PasswordEncoder passwordEncoder,
        AuthenticationManager authenticationManager,
        JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @GetMapping("/hi")
    public String hi() {
        return "Hi";
    }

    @PostMapping("/signup")
    public void signup(@RequestBody UserModel userModel) {
        UserDetailsEntity userDetailsEntity = new UserDetailsEntity(
            UUID.randomUUID(),
            userModel.getUsername(),
            passwordEncoder.encode(userModel.getPassword()),
            userModel.getRoles()
        );
        userRepository.save(userDetailsEntity);    
    }   

    @PostMapping("/login")
    public String login(@RequestBody UserModel userModel) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(userModel.getUsername(), userModel.getPassword())
        );
        if (authentication.isAuthenticated()) {
            return jwtService.generateJWT(userModel, authentication.getAuthorities());
        } 
        throw new UsernameNotFoundException("No such user Found");    
    }   
}
