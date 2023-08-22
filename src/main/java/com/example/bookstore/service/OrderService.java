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
    private final ShoppingCartService shoppingCartService;
    private final UserService userService;

    public Order save(Order o){ return orderRepository.save(o); }

    public List<Order> findAllByEmail(String email){
        return orderRepository.findAll().stream().filter(order -> order.getUserEmail().equals(email)).toList();
    }

    public Optional<Order> findById(String orderId) {
        return orderRepository.findById(orderId);
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order createOrder(Users u, String address, Long phoneNo) {

        List<Book> shoppingCartItems = u.getShoppingCart().getShoppingCartItems();
        List<OrderItem> orderItems = new ArrayList<>();
        int total = 0;

        for(Book b : shoppingCartItems){
            OrderItem item = new OrderItem(b.getIsbn(), b.getPrice());
            orderItems.add(item);
            total+=b.getPrice();
        }
        u.getShoppingCart().reset();
        //might need to remove old one???
        shoppingCartService.save(u.getShoppingCart());
        userService.save(u);
        Order order = new Order(Status.PLACED, total, address, u.getEmail(), phoneNo);
        order.getOrderItems().addAll(orderItems);

        save(order);
        return order;
    }

    public List<OrderItem> findMostCommonOrderItem() {
        List<OrderItem> l = new ArrayList<>();
        l.add(new OrderItem());
        return l;
    }
}
