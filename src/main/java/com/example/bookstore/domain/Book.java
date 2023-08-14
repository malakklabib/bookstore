package com.example.bookstore.domain;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

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
    @Size(min = 1, max = 100)
    @NotEmpty(message = "please add the book's title")
    String title;

    @NonNull
    @Size(min = 1, max = 100)
    @NotEmpty(message = "please add the book's author")
    String author;

    @NonNull
    @Min(value = 1, message = "please add an appropriate price")
    @Max(value = 100, message = "please add an appropriate price")
    int price;


}
