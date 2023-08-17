package com.example.bookstore.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
@Getter
@Setter
public class Wishlist {

    @Id
    private String wishListId;

    @NonNull
    private List<Book> wishlistItems;

    public void addItem(Book b){
        wishlistItems.add(b);
    }

    public void removeItem(Book b) {
        wishlistItems.remove(b);
    }
}
