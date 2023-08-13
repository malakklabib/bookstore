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
public class Review {

    @Id
    private String id;

    @NonNull
    private int rating;

    @NonNull
    private Users user;

    @NonNull
    private String body;
}
