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
public class ShoppingCart {

    @Id
    private String shoppingCartId;

    @NonNull
    private List<Book> shoppingCartItems;


    public ShoppingCart reset(){
        shoppingCartItems = new ArrayList<>();
        return this;
    }

    public void addItem(Book b){
        shoppingCartItems.add(b);
    }

    public void removeItem(Book b){
        shoppingCartItems.remove(b);
    }

}
