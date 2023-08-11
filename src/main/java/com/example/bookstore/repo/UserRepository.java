package com.example.bookstore.repo;

import com.example.bookstore.domain.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<Users, String> {
    Optional<Users> findByEmail(String email);
}
