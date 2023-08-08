package com.example.bookstore;

import com.example.bookstore.domain.Book;
import com.example.bookstore.domain.Users;
import com.example.bookstore.repo.BookRepository;
import com.example.bookstore.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookstoreApplication implements CommandLineRunner{

	BookRepository bookRepository;
	UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {

	}
}
