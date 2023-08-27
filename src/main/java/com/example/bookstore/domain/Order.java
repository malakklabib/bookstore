package com.example.bookstore.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Document
@RequiredArgsConstructor
@NoArgsConstructor
@ToString
@Getter @Setter
public class Order {

    @Id
    private String id;

    @NonNull
    private Status status;

    @NonNull
    private double total;

    @NonNull
    private String address;

    @NonNull
    private String userEmail;

    @NonNull
    private String phoneNumber;

    @DBRef
    private List<OrderItem> orderItems= new ArrayList<>();

    public Optional<OrderItem> findOrderItem(String orderItemId){
        return orderItems.stream().filter(orderItem -> orderItem.getIsbn().equals(orderItemId)).findFirst();
    }
}
