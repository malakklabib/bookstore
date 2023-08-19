package com.example.bookstore.controller;

import com.example.bookstore.domain.Book;
import com.example.bookstore.domain.Order;
import com.example.bookstore.domain.Status;
import com.example.bookstore.service.BookService;
import com.example.bookstore.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final BookService bookService;
    private final OrderService orderService;

    @GetMapping("")
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

    @GetMapping("/updateTracking")
    public ResponseEntity<List<Order>> updateTracking(){
        return ResponseEntity.ok(orderService.findAll());
    }

    @PutMapping("/updateTracking/{orderId}")
    public ResponseEntity<Void> updateTracking(@PathVariable String orderId){
        Optional<Order> o = orderService.findById(orderId);
        if(o.isEmpty())
            return ResponseEntity.notFound().build();
        Status s = o.get().getStatus() == Status.PLACED? Status.SHIPPING : Status.DELIVERED;
        o.get().setStatus(s);
        orderService.save(o.get());
        return ResponseEntity.ok().build();
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
