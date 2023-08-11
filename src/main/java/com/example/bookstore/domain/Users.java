package com.example.bookstore.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Document
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
@Getter
@Setter
public class Users {

    @Id
    private String id;

    @NonNull
    private String name;

    @NonNull
    private String password;

    @NonNull
    @Transient
    private String confirmPassword;

    @NonNull
    private String email;

    private ShoppingCart shoppingCart;

    private Wishlist wishlist;

    private Review review;

    private Set<Role> roles = new HashSet<>();

    public void addRole(Role role) {
        roles.add(role);
    }
}
