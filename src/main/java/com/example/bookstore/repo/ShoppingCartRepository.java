package com.example.bookstore.repo;

import com.example.bookstore.domain.Book;
import com.example.bookstore.domain.ShoppingCart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartRepository extends MongoRepository<ShoppingCart, String> {

}
