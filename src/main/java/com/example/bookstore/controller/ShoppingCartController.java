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
    public ResponseEntity<?> getShoppingCartItems(Authentication authentication){
        Users user = userService.getUser(authentication);
        List<Book> userCartItems = user.getShoppingCart().getShoppingCartItems();
        if (userCartItems.isEmpty())
            return ResponseEntity.ok("Your shopping cart is empty.");
        return ResponseEntity.ok(userCartItems);
    }

    @PostMapping("/books/{bookId}/addToCart")
    public ResponseEntity<String> addToCart(Authentication authentication, @PathVariable String bookId){
        Users user = userService.getUser(authentication);

        Optional<Book> book = bookService.findById(bookId);
        if (book.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.");

        String mssg = shoppingCartService.addToCart(user, book.get());
        return ResponseEntity.ok(mssg);
    }

    @DeleteMapping("/cart/{bookId}/removeFromCart")
    public ResponseEntity<String> removeFromCart(Authentication authentication, @PathVariable String bookId){

        Users user = userService.getUser(authentication);
        Optional<Book> book = bookService.findById(bookId);

        if (book.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.");

        String mssg = shoppingCartService.removeFromCart(user, book.get());
        return ResponseEntity.ok(mssg);
    }
}
