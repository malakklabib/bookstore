package com.example.bookstore.controller;

import com.example.bookstore.domain.Book;
import com.example.bookstore.domain.Users;
import com.example.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("")
    public ResponseEntity<List<Book>> catalog(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size, @RequestParam(required = false) Optional<String> sort) {
        Page<Book> books;
        if(sort.isPresent())
            books = bookService.getBooksByPage(PageRequest.of(page, size, Sort.by(sort.get())));
        else
            books = bookService.getBooksByPage(PageRequest.of(page, size));
        return ResponseEntity.ok(books.stream().toList());
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Book> readBook(@PathVariable String bookId){
        Optional<Book> book = bookService.findById(bookId);
        if(book.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(book.get());
    }

    @PostMapping("/search")
    public ResponseEntity<List<Book>> search(@RequestParam(required = false) Optional<String> title, @RequestParam(required = false) Optional<String> author,
                                             @RequestParam(required = false) Optional<Double> priceMin, @RequestParam(required = false) Optional<Double> priceMax,
                                             @RequestParam(required = false) Optional<Date> dateMin, @RequestParam(required = false) Optional<Date> dateMax,
                                             @RequestParam(required = false) Optional<Double> avgRating){
        List<Book> res = bookService.findByParameters(title, author, priceMin, priceMax, dateMin, dateMax, avgRating);
        if(res.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(res);
    }

}
