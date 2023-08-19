package com.example.bookstore.controller;

import com.example.bookstore.domain.Book;
import com.example.bookstore.domain.Users;
import com.example.bookstore.service.BookService;
import com.example.bookstore.service.ShoppingCartService;
import com.example.bookstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;
    private final UserService userService;
    private final BookService bookService;


    @GetMapping("/cart")
    public ResponseEntity<List<Book>> getShoppingCartItems(Authentication authentication) {
        try {
            Users u = userService.validate(authentication);
            List<Book> shoppingCartItems = shoppingCartService.getCartContents(u);
            return ResponseEntity.ok(shoppingCartItems);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("{bookId}/addToCart")
    public ResponseEntity<String> addToCart(@PathVariable String bookId, Authentication authentication) {

        Users u;
        try {
            u = userService.validate(authentication);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<Book> b = bookService.findById(bookId);
        if (b.isEmpty())
            return ResponseEntity.notFound().build();

        shoppingCartService.addToCart(u, b.get());
        return ResponseEntity.ok("Book added to cart.");
    }

    @DeleteMapping("{bookId}/removeFromCart")
    public ResponseEntity<String> removeFromCart(@PathVariable String bookId, Authentication authentication) {

        Users u;
        try {
            u = userService.validate(authentication);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Optional<Book> b = bookService.findById(bookId);
        if(b.isEmpty())
            return ResponseEntity.notFound().build();

        shoppingCartService.removeFromCart(u, b.get());
        return ResponseEntity.ok("Book removed from cart.");
    }
}
