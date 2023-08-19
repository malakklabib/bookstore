package com.example.bookstore.service;

import com.example.bookstore.domain.*;
import com.example.bookstore.repo.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Order save(Order o){ return orderRepository.save(o); }

    public List<Order> findAllByUserId(String userId){
        return orderRepository.findAll().stream().filter(order -> order.getUserId().equals(userId)).toList();
    }

    public Optional<Order> findById(String orderId) {
        return orderRepository.findById(orderId);
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order createOrder(Users u) {

        List<Book> shoppingCartItems = u.getShoppingCart().getShoppingCartItems();
        List<OrderItem> orderItems = new ArrayList<>();
        int total = 0;

        for(Book b : shoppingCartItems){
            OrderItem item = new OrderItem(b.getIsbn(), b.getPrice());
            orderItems.add(item);
            total+=b.getPrice();
        }

        Order order = new Order(u.getId(), Status.PLACED, total);
        order.getOrderItems().addAll(orderItems);

        save(order);
        return order;
    }

}
