package com.example.bookstore.service;

import com.example.bookstore.domain.Book;
import com.example.bookstore.domain.ShoppingCart;
import com.example.bookstore.domain.Users;
import com.example.bookstore.domain.Wishlist;
import com.example.bookstore.repo.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final UserService userService;
    private final WishlistRepository wishlistRepository;

    public List<Book> getWishlistBooks(Authentication auth) throws Exception {
        Users u = userService.validate(auth);
        return u.getWishlist().getWishlistItems();
    }


    public void addToWishlist(Book b, Authentication auth) throws Exception {
        Users u = userService.validate(auth);
        Wishlist wishlist = u.getWishlist();

        wishlist.addItem(b);
        save(wishlist);
        userService.save(u);
    }


    public void removeFromWishlist(Book b, Authentication auth) throws Exception {
        Users u = userService.validate(auth);
        Wishlist wishlist = u.getWishlist();

        wishlist.removeItem(b);
        save(wishlist);
        userService.save(u);
    }

    public void save(Wishlist wishlist) {
        wishlistRepository.save(wishlist);
    }
}
