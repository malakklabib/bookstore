package com.example.bookstore.repo;

import com.example.bookstore.domain.OrderItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends MongoRepository<OrderItem, String> {

}
