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

    public void addToCart(Users user, Book book){
        ShoppingCart shoppingCart = user.getShoppingCart();
        shoppingCart.addItem(book);
        save(shoppingCart);
    }

    public String removeFromCart(Users u, Book b) {
        ShoppingCart shoppingCart = u.getShoppingCart();
        if(shoppingCart.getShoppingCartItems().contains(b)) {
            shoppingCart.removeItem(b);
            save(shoppingCart);
            return b.getTitle() + " removed from cart.";
        }
        return b.getTitle() + " not found in your cart.";
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
