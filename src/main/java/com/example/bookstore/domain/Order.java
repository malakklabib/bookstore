package com.example.bookstore.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
@RequiredArgsConstructor
@NoArgsConstructor
@ToString
@Getter @Setter
public class Order {

    @Id
    private String id;

    @NonNull
    private String status;

    @NonNull
    private List<OrderItem> orderItems= new ArrayList<>();
}
