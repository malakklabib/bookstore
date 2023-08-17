package com.example.bookstore;

import com.example.bookstore.domain.Role;
import com.example.bookstore.domain.ShoppingCart;
import com.example.bookstore.domain.Users;
import com.example.bookstore.domain.Wishlist;
import com.example.bookstore.service.RoleService;
import com.example.bookstore.service.ShoppingCartService;
import com.example.bookstore.service.UserService;
import com.example.bookstore.service.WishlistService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class BookstoreApplication {

//	private final BCryptPasswordEncoder encoder;
//	private final UserService userService;
//	private final ShoppingCartService shoppingCartService;
//	private final WishlistService wishlistService;
//	private final RoleService roleService;
//
//	public BookstoreApplication(UserService userService, ShoppingCartService shoppingCartService, WishlistService wishlistService, RoleService roleService) {
//		this.shoppingCartService = shoppingCartService;
//		this.wishlistService = wishlistService;
//		this.roleService = roleService;
//		this.encoder = new BCryptPasswordEncoder();
//		this.userService = userService;
//	}


	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		ShoppingCart sc1 = new ShoppingCart();
//		Wishlist ws1 = new Wishlist();
//		Role admin = new Role("ROLE_ADMIN");
//		Users u = new Users("admin", "password", "admin@gmail.com", sc1, ws1, admin);
//		String secret = encoder.encode(u.getPassword());
//		u.setPassword(secret);
//		u.setConfirmPassword(secret);
//		userService.save(u);
//		shoppingCartService.save(sc1);
//		wishlistService.save(ws1);
//
//		ShoppingCart sc2 = new ShoppingCart();
//		Wishlist ws2 = new Wishlist();
//		Role user = new Role("ROLE_USER");
//		Users u2 = new Users("malak", "password", "malak@gmail.com", sc2, ws2, user);
//		String secret2 = encoder.encode(u.getPassword());
//		u2.setPassword(secret2);
//		u2.setConfirmPassword(secret2);
//		userService.save(u2);
//		shoppingCartService.save(sc2);
//		wishlistService.save(ws2);
//
//		roleService.save(admin);
//		roleService.save(user);
//
//	}
}
