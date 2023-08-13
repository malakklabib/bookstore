package com.example.bookstore.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Document(collection = "users")
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
@Getter
@Setter
public class Users {

    @Id
    private String id;

    @NonNull
    private ShoppingCart shoppingCart;

    @NonNull
    private Wishlist wishlist;

    @NonNull
    private Review review;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    private String email;

    @NonNull
    private String password;

    @Transient
    @NonNull
    private String confirmPassword;

    private Set<Role> roles = new HashSet<>();
}
