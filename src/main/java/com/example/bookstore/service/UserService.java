package com.example.bookstore.service;


import com.example.bookstore.domain.Users;
import com.example.bookstore.repo.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Users save(Users user){
        return userRepository.save(user);
    }
}
