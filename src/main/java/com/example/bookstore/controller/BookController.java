package com.example.bookstore.controller;

import com.example.bookstore.domain.Book;
import com.example.bookstore.domain.Users;
import com.example.bookstore.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping("/")
    public ResponseEntity<List<Book>> catalog() {
        List<Book> books = bookService.findAll();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Book> readBook(@PathVariable String bookId){
        Optional<Book> book = bookService.findById(bookId);
        if(book.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(book.get());
    }

}
