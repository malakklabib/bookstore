package com.example.bookstore.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
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
    private int total;

    @NonNull
    @NotEmpty
    private String address;

    @NonNull
    private String userEmail;

    @NonNull
    @NotEmpty
    private Long phoneNumber;

    private List<OrderItem> orderItems= new ArrayList<>();

    public Optional<OrderItem> findOrderItem(String orderItemId){
        return orderItems.stream().filter(orderItem -> orderItem.getIsbn().equals(orderItemId)).findFirst();
    }
}
