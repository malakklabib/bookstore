package com.example.bookstore.controller;

import com.example.bookstore.domain.*;
import com.example.bookstore.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
@Secured("ROLE_ADMIN")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final BookService bookService;
    private final OrderService orderService;
    private final MailService mailService;
    private final ReviewService reviewService;

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
        if (existingBook.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        updatedBook.setIsbn(id);
        Book savedBook = bookService.save(updatedBook);

        return ResponseEntity.ok(savedBook);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable String id){

        Optional<Book> b = bookService.findById(id);

        if (b.isEmpty())
            return ResponseEntity.notFound().build();

        bookService.delete(b.get());
        reviewService.deleteAllReviews(b.get());

        return ResponseEntity.noContent().build();
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

        Order order = o.get();
        Status s = order.getStatus();
        Users user = userService.findByEmail(order.getUserEmail()).get();

        if(s == Status.PLACED){
            s = Status.SHIPPING;
            mailService.sendShipmentEmail(user, order);
        }
        else s = Status.DELIVERED;

        o.get().setStatus(s);
        orderService.save(o.get());
        return ResponseEntity.ok().build();
    }

}
