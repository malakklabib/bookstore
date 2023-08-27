package com.example.bookstore.service;

import com.example.bookstore.domain.*;
import com.example.bookstore.repo.OrderItemRepository;
import com.example.bookstore.repo.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ShoppingCartService shoppingCartService;
    private final UserService userService;

    public Order save(Order o) {
        return orderRepository.save(o);
    }

    public List<Order> findAllByEmail(String email) {
        return orderRepository.findAll().stream().filter(order -> order.getUserEmail().equals(email)).toList();
    }

    public Optional<Order> findById(String orderId) {
        return orderRepository.findById(orderId);
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public List<Order> findTop3ByEmail(String email) { return orderRepository.findTop3ByUserEmail(email); }

    public double calculateTotal(Users u){
        List<Book> shoppingCartItems = u.getShoppingCart().getShoppingCartItems();
        double total = 0;

        for (Book b : shoppingCartItems)
            total += b.getPrice();
        return total;
    }

    public Order createOrder(Users u, String address, String phoneNo, double total) {

        List<Book> shoppingCartItems = u.getShoppingCart().getShoppingCartItems();
        List<OrderItem> orderItems = new ArrayList<>();

        for (Book b : shoppingCartItems) {
            OrderItem item = new OrderItem(b.getIsbn(), b.getPrice());
            orderItemRepository.save(item);
            orderItems.add(item);
        }

        u.getShoppingCart().reset();
        shoppingCartService.save(u.getShoppingCart());
        userService.save(u);

        Order order = new Order(Status.PLACED, total, address, u.getEmail(), phoneNo);
        order.getOrderItems().addAll(orderItems);

        save(order);
        return order;
    }

}
