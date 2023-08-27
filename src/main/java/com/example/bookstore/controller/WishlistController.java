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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;
    private final UserService userService;
    private final BookService bookService;

    @GetMapping("/wishlist")
    public ResponseEntity<?> getWishlistItems(Authentication authentication) {
        Users user = userService.getUser(authentication);
        List<Book> userWishlistItems = user.getWishlist().getWishlistItems();
        if (userWishlistItems.isEmpty())
            return ResponseEntity.ok("Your wishlist is empty.");
        return ResponseEntity.ok(userWishlistItems);
    }

    @PostMapping("/books/{bookId}/addToWishlist")
    public ResponseEntity<String> addToWishlist(@PathVariable String bookId, Authentication authentication){
        Users user = userService.getUser(authentication);

        Optional<Book> book = bookService.findById(bookId);
        if (book.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.");

        String mssg = wishlistService.addToWishlist(user, book.get());
        return ResponseEntity.ok(mssg);
    }

    @DeleteMapping("/wishlist/{bookId}/removeFromWishlist")
    public ResponseEntity<String> removeFromWishlist(@PathVariable String bookId, Authentication authentication){

        Users u = userService.getUser(authentication);

        Optional<Book> b = bookService.findById(bookId);
        if(b.isEmpty())
            return ResponseEntity.notFound().build();

        String mssg = wishlistService.removeFromWishlist(u,b.get());
        return ResponseEntity.ok(mssg);
    }
}
