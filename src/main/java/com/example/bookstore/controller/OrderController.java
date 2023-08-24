package com.example.bookstore.controller;


import com.example.bookstore.domain.*;
import com.example.bookstore.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
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
    public ResponseEntity<Order> proceedToCheckout(@RequestParam String address, @RequestParam Long phoneNo,
                                                   Authentication authentication) throws Exception {
        Users u = userService.validate(authentication);
        if (u.getShoppingCart().getShoppingCartItems().isEmpty())
            return ResponseEntity.badRequest().build();
        Order o = orderService.createOrder(u, address, phoneNo);
        mailService.sendConfirmationEmail(u, o);
        return ResponseEntity.status(HttpStatus.CREATED).body(o);
    }

    @GetMapping("/profile/viewOrderHistory")
    public ResponseEntity<List<Order>> viewAllOrders(Authentication authentication) throws Exception {
        Users u = userService.validate(authentication);
        return ResponseEntity.ok(orderService.findAllByEmail(u.getEmail()));
    }

    @GetMapping("/profile/viewOrderHistory/{orderId}")
    public ResponseEntity<Order> trackOrder(@PathVariable String orderId) {
        Optional<Order> o = orderService.findById(orderId);
        return o.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/profile/viewOrderHistory/{orderId}/review/{orderItemId}")
    public ResponseEntity<String> leaveReview(@PathVariable String orderId, @PathVariable String orderItemId,
                                              @RequestParam int rating, @RequestParam String body) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users u = userService.validate(authentication);
        Optional<Order> order = orderService.findById(orderId);

        if (order.isEmpty())
            return ResponseEntity.notFound().build();

        Optional<OrderItem> item = order.get().findOrderItem(orderItemId);

        if (item.isEmpty())
            return ResponseEntity.notFound().build();

        Optional<Book> b = bookService.findById(orderItemId);
        if (b.isEmpty())
            return ResponseEntity.notFound().build();

        if (rating < 1 || rating > 5)
            return ResponseEntity.badRequest().body("rating should lie between 1 and 5 stars");
        if (body.length() > 200)
            return ResponseEntity.badRequest().body("review exceeded maximum limit");

        Review newReview = new Review(orderItemId, u.getId(), rating, body);
        reviewService.save(newReview);

        Book ratedBook = b.get();
        List<Review> reviews = reviewService.findAllByBookId(orderItemId);
        int count = 0;
        double ratings = 0;
        for (Review review : reviews) {
            count++;
            ratings += review.getRating();
        }

        ratedBook.setAvgRating(ratings / count);
        bookService.save(ratedBook);
        return ResponseEntity.ok("your response has been submitted!");
    }

}
