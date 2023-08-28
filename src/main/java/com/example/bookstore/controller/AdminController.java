package com.example.bookstore.controller;

import com.example.bookstore.domain.*;
import com.example.bookstore.service.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final BookService bookService;
    private final OrderService orderService;
    private final MailService mailService;
    private final ReviewService reviewService;


    @PostMapping("/addBook")
    public ResponseEntity<?> createBook(@RequestBody @Valid Book book, BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            String s = "";
            for(int i = 0 ; i < bindingResult.getErrorCount(); i++)
                s+=bindingResult.getAllErrors().get(i).getDefaultMessage() + "\n";
            return ResponseEntity.badRequest().body(s);
        }

        Book savedBook = bookService.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable String id, @RequestBody @Valid Book updatedBook, BindingResult bindingResult) {

        Optional<Book> existingBook = bookService.findById(id);
        if (existingBook.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.");

        if(bindingResult.hasErrors()){
            String s = "";
            for(int i = 0 ; i < bindingResult.getErrorCount(); i++)
                s+=bindingResult.getAllErrors().get(i).getDefaultMessage() + "\n";
            return ResponseEntity.badRequest().body(s);
        }

        updatedBook.setIsbn(id);
        Book savedBook = bookService.save(updatedBook);

        return ResponseEntity.ok(savedBook);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable String id){

        Optional<Book> existingBook = bookService.findById(id);
        if (existingBook.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.");

        bookService.delete(existingBook.get());
        reviewService.deleteAllReviews(existingBook.get());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Book successfully deleted.");
    }

    @GetMapping("/updateTracking")
    public ResponseEntity<List<Order>> updateTracking(){
        return ResponseEntity.ok(orderService.findAll());
    }

    @PutMapping("/updateTracking/{orderId}")
    public ResponseEntity<?> updateTracking(@PathVariable String orderId){
        Optional<Order> o = orderService.findById(orderId);
        if(o.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found.");

        Order order = o.get();

        String userEmail = order.getUserEmail();
        Optional<Users> u = userService.findByEmail(userEmail);
        if(u.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");

        Users user = u.get();
        Status orderStatus = order.getStatus();
        if(orderStatus == Status.PLACED){
            orderStatus = Status.SHIPPING;
            mailService.sendShipmentEmail(user, order);
        }
        else orderStatus = Status.DELIVERED;

        order.setStatus(orderStatus);
        orderService.save(order);
        return ResponseEntity.ok().body(order);
    }

    @GetMapping("/popularGenres")
    public ResponseEntity<String> popularGenres(){
        String popularGenres = bookService.findMostPopularGenres();
        if(popularGenres.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No books have been sold yet.");
        return ResponseEntity.ok(popularGenres);
    }

    @GetMapping("/topSellingBooks")
    public ResponseEntity<String> topSellingBooks(){
        String topSellingBooks = bookService.findMostPopularBooks();
        if(topSellingBooks.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No books have been sold yet.");
        return ResponseEntity.ok(topSellingBooks);
    }






}
