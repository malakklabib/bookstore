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
public class ShoppingCart {

    @Id
    private String shoppingCartId;

    private List<Book> shoppingCartItems = new ArrayList<>();


    public void addItem(Book b){
        shoppingCartItems.add(b);
    }

    public void removeItem(Book b){
        shoppingCartItems.remove(b);
    }

}
