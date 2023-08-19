package com.example.bookstore.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Wishlist {

    @Id
    private String wishListId;

    private List<Book> wishlistItems = new ArrayList<>();

    public void addItem(Book b){
        wishlistItems.add(b);
    }

    public void removeItem(Book b) {
        wishlistItems.remove(b);
    }
}
