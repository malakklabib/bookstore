package com.example.bookstore.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private String name;

    private ShoppingCart shoppingCart;

    private Wishlist wishlist;

    private Review review;
}
