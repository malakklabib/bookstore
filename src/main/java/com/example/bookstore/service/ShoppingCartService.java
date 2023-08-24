package com.example.bookstore.service;

import com.example.bookstore.domain.Book;
import com.example.bookstore.domain.ShoppingCart;
import com.example.bookstore.domain.Users;
import com.example.bookstore.repo.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;

    public void addToCart(Users u, Book b){
        ShoppingCart shoppingCart = u.getShoppingCart();
        shoppingCart.addItem(b);
        save(shoppingCart);
    }

    public void removeFromCart(Users u, Book b) {
        ShoppingCart shoppingCart = u.getShoppingCart();
        shoppingCart.removeItem(b);
        save(shoppingCart);
    }

    public ShoppingCart save(ShoppingCart shoppingCart) {
         return shoppingCartRepository.save(shoppingCart);
    }


    public List<ShoppingCart> findAll() {
        return shoppingCartRepository.findAll();
    }

    public Optional<ShoppingCart> findById(String shoppingCartId) {
        return shoppingCartRepository.findById(shoppingCartId);
    }


}
