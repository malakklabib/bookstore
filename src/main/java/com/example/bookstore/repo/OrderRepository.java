package com.example.bookstore.repo;

import com.example.bookstore.domain.Order;
import com.example.bookstore.domain.OrderItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findTop3ByUserEmail(String email);
}
