package com.example.bookstore.service;

import com.example.bookstore.domain.Book;
import com.example.bookstore.domain.ShoppingCart;
import com.example.bookstore.domain.Users;
import com.example.bookstore.repo.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {

    private final UserService userService;
    private final ShoppingCartRepository shoppingCartRepository;

    public List<Book> getCartContents(Authentication auth) throws Exception {

        Users u = userService.validate(auth);

        return u.getShoppingCart().getShoppingCartItems();
    }

    public void addToCart(Book b, Authentication auth) throws Exception {

        Users u = userService.validate(auth);
        ShoppingCart shoppingCart = u.getShoppingCart();

        shoppingCart.addItem(b);
        save(shoppingCart);
        userService.save(u);
    }

    public void removeFromCart(Book b, Authentication auth) throws Exception {
        Users u = userService.validate(auth);
        ShoppingCart shoppingCart = u.getShoppingCart();

        shoppingCart.removeItem(b);
        save(shoppingCart);
        userService.save(u);

    }

    public ShoppingCart save(ShoppingCart shoppingCart) {
         return shoppingCartRepository.save(shoppingCart);
    }
}
