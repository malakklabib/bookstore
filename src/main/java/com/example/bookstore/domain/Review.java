package com.example.bookstore.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

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
    private String bookId;

    @NonNull
    private String userId;

    @NonNull
    @Min(value = 1, message = "minimum rating is 1")
    @Max(value = 5, message = "maximum rating is 5")
    private int rating;

    @NonNull
    @Size(max = 200, message = "maximum limit exceeded")
    private String body;
}
