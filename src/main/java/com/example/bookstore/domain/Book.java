package com.example.bookstore.domain;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @NonNull
    private Date publicationDate;

    @NonNull
    private int avgRating;

    public boolean equals(Object o){
        if(o instanceof Book)
            return this.isbn.equals(((Book)o).isbn);
        return false;
    }


}
