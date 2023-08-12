package com.example.bookstore;

import com.example.bookstore.domain.Role;
import com.example.bookstore.domain.Users;
import com.example.bookstore.service.RoleService;
import com.example.bookstore.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookstoreApplication{

	private static final Logger log = LoggerFactory.getLogger(BookstoreApplication.class);
	private final RoleService roleService;
	private final UserService userService;

	public BookstoreApplication(RoleService roleService, UserService userService) {
		this.roleService = roleService;
		this.userService = userService;
	}


	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

}
