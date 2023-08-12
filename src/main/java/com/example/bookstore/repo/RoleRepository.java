package com.example.bookstore.repo;

import com.example.bookstore.domain.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {
    Role findByName(String roleUser);
}
