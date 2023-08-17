package com.example.bookstore.controller;

import com.example.bookstore.domain.Book;
import com.example.bookstore.service.ShoppingCartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("")
    public ResponseEntity<List<Book>> getShoppingCartItems(Authentication authentication) {
        try {
            List<Book> shoppingCartItems = shoppingCartService.getCartContents(authentication);
            return ResponseEntity.ok(shoppingCartItems);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@Valid @RequestBody Book book, BindingResult bindingResult, Authentication authentication) {

        if(bindingResult.hasErrors())
            return ResponseEntity.notFound().build();

        try {
            shoppingCartService.addToCart(book, authentication);
            return ResponseEntity.ok("Book added to cart.");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeFromCart(@Valid @RequestBody Book book, BindingResult bindingResult, Authentication authentication) {

        if(bindingResult.hasErrors())
            return ResponseEntity.notFound().build();

        try {
            shoppingCartService.removeFromCart(book, authentication);
            return ResponseEntity.ok("Book removed from cart.");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
