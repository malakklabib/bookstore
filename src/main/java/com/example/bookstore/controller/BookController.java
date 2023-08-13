package com.example.bookstore.controller;

import com.example.bookstore.domain.Book;
import com.example.bookstore.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String home(Model m){
        m.addAttribute("books", bookService.findAll());
        return "catalog";
    }

    @GetMapping("/{id}")
    public Optional<Book> readBook(@PathVariable String id){
        return bookService.findById(id);
    }

}
