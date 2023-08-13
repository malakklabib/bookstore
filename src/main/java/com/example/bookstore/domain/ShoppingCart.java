package com.example.bookstore.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
//@RequiredArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class ShoppingCart {

    List<Book> shoppingCartItems = new ArrayList<>();

}
