package com.example.bookstore.service;

import com.example.bookstore.domain.Role;
import com.example.bookstore.domain.ShoppingCart;
import com.example.bookstore.domain.Users;
import com.example.bookstore.domain.Wishlist;
import com.example.bookstore.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final ShoppingCartService shoppingCartService;
    private final WishlistService wishlistService;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();;


    public Users save(Users user){
        return userRepository.save(user);
    }

    public Optional<Users> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<Users> findById(String userId) {
        return userRepository.findById(userId);
    }


    public Users getUser(Authentication auth){
        String email = auth.getName();
        Optional<Users> user = findByEmail(email);
        return user.get();
    }

    public List<Users> findAll() {
        return userRepository.findAll();
    }

    public Users register(Users user) {
        ShoppingCart sc = new ShoppingCart(new ArrayList<>());
        Wishlist ws = new Wishlist(new ArrayList<>());
        Role role = roleService.findByName("ROLE_USER");
        String secret = encoder.encode(user.getPassword());

        Users newUser = new Users();

        newUser.setRole(role);
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(secret);
        newUser.setConfirmPassword(secret);
        newUser.setWishlist(ws);
        newUser.setShoppingCart(sc);

        shoppingCartService.save(sc);
        wishlistService.save(ws);
        save(newUser);
        return newUser;
    }
}
