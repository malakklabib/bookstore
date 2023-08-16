package com.example.bookstore.service;


import com.example.bookstore.domain.Users;
import com.example.bookstore.repo.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final RoleService roleService;

    public UserService(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        encoder = new BCryptPasswordEncoder();
    }

    public Users save(Users user){
        return userRepository.save(user);
    }

    public Users register(Users user) {
        String secret = "{bcrypt}" + encoder.encode(user.getPassword());
        user.setPassword(secret);

        user.setConfirmPassword(secret);

        user.setRole(roleService.findByName("ROLE_USER"));

//        user.setActivationCode(UUID.randomUUID().toString());
//
//        user.setEnabled(false);
//
        save(user);
//
//        sendActivationEmail(user);

        return user;
    }


    public Users findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }
}
