package com.example.bookstore.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@RequiredArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class OrderItem {

    @Id
    private String isbn;

    @NonNull
    private int price;

}
