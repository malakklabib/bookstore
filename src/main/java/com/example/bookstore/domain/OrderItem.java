package com.example.bookstore.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderItem {

    @Id
    private String id;

    @NonNull
    private String isbn;

    @NonNull
    private double price;

}
