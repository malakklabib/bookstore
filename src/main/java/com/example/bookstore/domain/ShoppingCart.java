package com.example.bookstore.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
@NoArgsConstructor
@ToString
@Getter
@Setter
public class ShoppingCart {

    private List<Book> shoppingCartItems = new ArrayList<>();

    public void addItem(Book b){
        shoppingCartItems.add(b);
    }

}
