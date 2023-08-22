package com.example.bookstore.controller;

import com.example.bookstore.domain.Book;
import com.example.bookstore.domain.Users;
import com.example.bookstore.service.BookService;
import com.example.bookstore.service.UserService;
import com.example.bookstore.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;
    private final UserService userService;
    private final BookService bookService;

    @GetMapping("/wishlist")
    public ResponseEntity<List<Book>> getWishlistItems(Authentication authentication) {
        try {
            Users u = userService.validate(authentication);
            List<Book> wishlistBooks = wishlistService.getWishlistBooks(u);
            return ResponseEntity.ok(wishlistBooks);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("books/{bookId}/addToWishlist")
    public ResponseEntity<String> addToCart(@PathVariable String bookId, Authentication authentication) throws Exception {

        Users u = userService.validate(authentication);

        Optional<Book> b = bookService.findById(bookId);
        if (b.isEmpty())
            return ResponseEntity.notFound().build();

        wishlistService.addToWishlist(u, b.get());
        return ResponseEntity.ok("Book added to wishlist.");
    }

    @DeleteMapping("wishlist/{bookId}/removeFromWishlist")
    public ResponseEntity<String> removeFromCart(@PathVariable String bookId, Authentication authentication) throws Exception {

        Users u = userService.validate(authentication);

        Optional<Book> b = bookService.findById(bookId);
        if(b.isEmpty())
            return ResponseEntity.notFound().build();

        String mssg = wishlistService.removeFromWishlist(u,b.get());
        return ResponseEntity.ok(mssg);
    }
}
