package com.example.bookstore.controller;

import com.example.bookstore.domain.Book;
import com.example.bookstore.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@Secured("ROLE_ADMIN")
@RequestMapping("/admin")
public class AdminController {

    private final BookService bookService;

    private final Logger logger = LoggerFactory.getLogger(AdminController.class);

    public AdminController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Book>> manageBooks(){
        return ResponseEntity.ok(bookService.findAll());
    }

    @PostMapping("/addBook")
    public ResponseEntity<Book> createBook(@RequestBody @Valid Book book, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        Book savedBook = bookService.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable String id, @RequestBody @Valid Book updatedBook, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Book> existingBook = bookService.findById(id);
        if (!existingBook.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        updatedBook.setIsbn(id);
        Book savedBook = bookService.save(updatedBook);
        return ResponseEntity.ok(savedBook);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable String id){

        Optional<Book> b = bookService.findById(id);

        if (!b.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        bookService.delete(b.get());

        return ResponseEntity.noContent().build();
    }



}
