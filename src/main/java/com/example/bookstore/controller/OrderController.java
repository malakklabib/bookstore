package com.example.bookstore.controller;


import com.example.bookstore.domain.*;
import com.example.bookstore.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final UserService userService;
    private final OrderService orderService;
    private final BookService bookService;
    private final ReviewService reviewService;
    private final MailService mailService;

    @PostMapping("/cart/checkout")
    public ResponseEntity<?> proceedToCheckout(@RequestParam String address, @RequestParam Long phoneNo,
                                                   Authentication authentication){
        Users user = userService.getUser(authentication);
        if (user.getShoppingCart().getShoppingCartItems().isEmpty())
            return ResponseEntity.badRequest().body("Cannot checkout an empty cart.");
        Order order = orderService.createOrder(user, address, phoneNo);
        mailService.sendConfirmationEmail(user, order);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @GetMapping("/profile/viewOrderHistory")
    public ResponseEntity<?> viewAllOrders(Authentication authentication){
        Users u = userService.getUser(authentication);
        List<Order> allOrders = orderService.findAllByEmail(u.getEmail());
        if(allOrders.isEmpty())
            return ResponseEntity.ok("You haven't placed any orders yet.");
        return ResponseEntity.ok(allOrders);
    }

    @GetMapping("/profile/viewOrderHistory/{orderId}")
    public ResponseEntity<?> trackOrder(@PathVariable String orderId) {
        Optional<Order> order = orderService.findById(orderId);
        if(order.isPresent())
            return ResponseEntity.ok(order.get());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found.");
    }

    @PostMapping("/profile/viewOrderHistory/{orderId}/review/{orderItemId}")
    public ResponseEntity<String> leaveReview(Authentication authentication, @PathVariable String orderId, @PathVariable String orderItemId,
                                              @RequestParam int rating, @RequestParam String body){
        Users u = userService.getUser(authentication);

        Optional<Order> order = orderService.findById(orderId);
        if (order.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found.");

        Optional<OrderItem> item = order.get().findOrderItem(orderItemId);
        if (item.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order item not found.");

        Optional<Book> book = bookService.findById(item.get().getIsbn());
        if (book.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found.");

        if (rating < 1 || rating > 5)
            return ResponseEntity.badRequest().body("Rating should lie between 1 and 5 stars.");
        if (body.length() > 200)
            return ResponseEntity.badRequest().body("Review exceeded maximum limit.");

        Review newReview = new Review(orderItemId, u.getId(), rating, body);
        reviewService.save(newReview);

        Book ratedBook = book.get();
        reviewService.calculateAvgRating(ratedBook);
        bookService.save(ratedBook);
        return ResponseEntity.ok("Your response has been submitted!");
    }

}
