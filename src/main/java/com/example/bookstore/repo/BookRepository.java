package com.example.bookstore.repo;

import com.example.bookstore.domain.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
}
