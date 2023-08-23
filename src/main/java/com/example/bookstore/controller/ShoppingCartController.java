package com.example.bookstore.controller;

import com.example.bookstore.domain.Book;
import com.example.bookstore.domain.ShoppingCart;
import com.example.bookstore.domain.Users;
import com.example.bookstore.service.BookService;
import com.example.bookstore.service.ShoppingCartService;
import com.example.bookstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;
    private final UserService userService;
    private final BookService bookService;


    @GetMapping("/cart")
    public ResponseEntity<List<Book>> getShoppingCartItems() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users u = userService.validate(authentication);
        Optional<ShoppingCart> cart = shoppingCartService.findById(u.getShoppingCart().getShoppingCartId());
        if(cart.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(cart.get().getShoppingCartItems());
    }

    @PostMapping("/books/{bookId}/addToCart")
    public ResponseEntity<String> addToCart(@PathVariable String bookId) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users u = userService.validate(authentication);

        Optional<Book> b = bookService.findById(bookId);
        if (b.isEmpty())
            return ResponseEntity.notFound().build();

        shoppingCartService.addToCart(u, b.get());
        return ResponseEntity.ok("Book added to cart.");
    }

    @DeleteMapping("/cart/{bookId}/removeFromCart")
    public ResponseEntity<String> removeFromCart(@PathVariable String bookId) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users u = userService.validate(authentication);
        Optional<Book> b = bookService.findById(bookId);

        if(b.isEmpty())
            return ResponseEntity.notFound().build();

        shoppingCartService.removeFromCart(u, b.get());
        return ResponseEntity.ok("Book removed from cart.");
    }
}
