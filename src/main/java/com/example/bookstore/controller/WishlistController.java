package com.example.bookstore.controller;

import com.example.bookstore.domain.Book;
import com.example.bookstore.domain.Users;
import com.example.bookstore.service.BookService;
import com.example.bookstore.service.UserService;
import com.example.bookstore.service.WishlistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping("/items")
    public ResponseEntity<List<Book>> getWishlistItems(Authentication authentication) {
        try {
            List<Book> shoppingCartItems = wishlistService.getWishlistBooks(authentication);
            return ResponseEntity.ok(shoppingCartItems);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addToWishlist(@Valid @RequestBody Book book, BindingResult bindingResult, Authentication authentication) {

        if(bindingResult.hasErrors())
            return ResponseEntity.notFound().build();

        try {
            wishlistService.addToWishlist(book, authentication);
            return ResponseEntity.ok("Book added to wishlist.");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeFromWishlist(@Valid @RequestBody Book book, BindingResult bindingResult, Authentication authentication) {

        if(bindingResult.hasErrors())
            return ResponseEntity.notFound().build();

        try {
            wishlistService.removeFromWishlist(book, authentication);
            return ResponseEntity.ok("Book removed from wishlist.");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
