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
public class Book{

    @Id
    String isbn;

    @NonNull
    @NotEmpty(message = "Please add the book's title.")
    String title;

    @NonNull
    @NotEmpty(message = "Please add the book's author.")
    String author;

    @NonNull
    @Min(value = 1, message = "Please add an appropriate price.")
    double price;

    @NonNull
    @NotEmpty(message = "Please add the book's category.")
    private String category;

    @NonNull
    private Date publicationDate;

    @NonNull
    private double avgRating;

//    public boolean equals(Object o){
//        if(o instanceof Book)
//            return this.isbn.equals(((Book)o).isbn);
//        return false;
//    }
}
