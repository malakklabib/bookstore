package com.example.bookstore.domain;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class AuthenticationRequest {

    @NotEmpty(message = "Email must not be empty")
    private String email;

    @NotEmpty(message = "Password must not be empty")
    private String password;
}
