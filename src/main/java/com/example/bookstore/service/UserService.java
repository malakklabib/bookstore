package com.example.bookstore.service;

import com.example.bookstore.domain.Users;
import com.example.bookstore.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Users save(Users user){
        return userRepository.save(user);
    }



    public Optional<Users> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<Users> findById(String userId) {
        return userRepository.findById(userId);
    }

    public Users findByShoppingCartId(String shoppingCartId) {
        return userRepository.findAll().stream().filter(users -> users.getShoppingCart().getShoppingCartId().equals(shoppingCartId)).findFirst().get();
    }

    public Users findByWishlistId(String wishlistId) {
        return userRepository.findAll().stream().filter(users -> users.getWishlist().getWishListId().equals(wishlistId)).findFirst().get();
    }

    public Users validate(Authentication auth) throws Exception{
        String email = auth.getName();
        Optional<Users> user = findByEmail(email);
        if (user.isEmpty()) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            throw new Exception("user not authorized");
        }
        return user.get();
    }


//    public Users register(Users user) {
//        String secret = "{bcrypt}" + encoder.encode(user.getPassword());
//        user.setPassword(secret);
//
//        user.setConfirmPassword(secret);
//
//        user.setRole(roleService.findByName("ROLE_USER"));
//
////        user.setActivationCode(UUID.randomUUID().toString());
////
////        user.setEnabled(false);
////
//        save(user);
////
////        sendActivationEmail(user);
//
//        return user;
//    }
}
