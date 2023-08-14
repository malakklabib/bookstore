package com.example.bookstore.controller;

import com.example.bookstore.domain.Book;
import com.example.bookstore.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public ResponseEntity<List<Book>> listBooks() {
        List<Book> books = bookService.findAll();
        return ResponseEntity.ok(books);
    }

//    @GetMapping("/{id}")
//    public Optional<Book> readBook(@PathVariable String id, Model model){
//        model.addAttribute("book",bookService.findById(id));
//        return "";
//    }

}
