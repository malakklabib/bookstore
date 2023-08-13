package com.example.bookstore.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;

@Document
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Role {

    @Id
    private Long id;

    @NonNull
    private String name;

    private Collection<Users> users;

}
