package com.example.bookstore;

import com.example.bookstore.domain.Role;
import com.example.bookstore.domain.ShoppingCart;
import com.example.bookstore.domain.Users;
import com.example.bookstore.domain.Wishlist;
import com.example.bookstore.service.RoleService;
import com.example.bookstore.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class BookstoreApplication{

//	private final BCryptPasswordEncoder encoder;
//	private final UserService userService;
//
//	public BookstoreApplication(UserService userService) {
//		this.encoder = new BCryptPasswordEncoder();
//		this.userService = userService;
//	}


	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		Users u = new Users("admin", "password", "admin@gmail.com", new ShoppingCart(), new Wishlist(), new Role("ROLE_ADMIN"));
//		String secret = encoder.encode(u.getPassword());
//		u.setPassword(secret);
//		u.setConfirmPassword(secret);
//		userService.save(u);
//
//		Users user = new Users("malak", "password", "malak@gmail.com", new ShoppingCart(), new Wishlist(), new Role("ROLE_USER"));
//		String secret2 = encoder.encode(user.getPassword());
//		user.setPassword(secret2);
//		user.setConfirmPassword(secret2);
//		userService.save(user);
//
//	}
}
