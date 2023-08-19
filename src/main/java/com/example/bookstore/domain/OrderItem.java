package com.example.bookstore.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderItem {

    @NonNull
    private String isbn;

    @NonNull
    private int price;

}
