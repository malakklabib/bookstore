package com.example.bookstore.controller;

import com.example.bookstore.domain.LoginRequest;
import com.example.bookstore.domain.LoginResponse;
import com.example.bookstore.domain.Users;
import com.example.bookstore.security.JwtIssuer;
import com.example.bookstore.service.AuthService;
import com.example.bookstore.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
public class AuthController {

    private final UserService userService;
    private final AuthService authService;
    private final JwtIssuer jwtIssuer;

    public AuthController(UserService userService, AuthService authService, JwtIssuer jwtIssuer) {
        this.userService = userService;
        this.authService = authService;
        this.jwtIssuer = jwtIssuer;
    }

    @GetMapping("/secured")
    public String sec(){
        return "hi";
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Validated LoginRequest request) {
        return authService.attemptLogin(request.getEmail(), request.getPassword());
    }

    @PostMapping("/register")
    public ResponseEntity<Users> registerNewUser(@RequestBody @Valid Users user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        Users savedUser = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }
}
