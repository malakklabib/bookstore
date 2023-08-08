package com.example.bookstore.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
@Getter
@Setter
public class Book {

    @Id
    String isbn;

    @NonNull
    String title;

    @NonNull
    String author;

    @NonNull
    int price;


}
