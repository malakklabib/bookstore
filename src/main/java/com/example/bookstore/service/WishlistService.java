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

    private final WishlistRepository wishlistRepository;

    public List<Book> getWishlistBooks(Users u) {
        return u.getWishlist().getWishlistItems();
    }


    public String addToWishlist(Users u, Book b) {
        Wishlist wishlist = u.getWishlist();
        if(wishlist.getWishlistItems().contains(b))
            return b.getTitle() + " is already in your wishlist.";
        wishlist.addItem(b);
        save(wishlist);
        return b.getTitle() + " successfully added to your wishlist!";
    }

    public String removeFromWishlist(Users u, Book b){
        Wishlist wishlist = u.getWishlist();
        if(!wishlist.getWishlistItems().contains(b))
            return b.getTitle() + " is not in your wishlist";
        wishlist.removeItem(b);
        save(wishlist);
        return b.getTitle() + " removed from your wishlist.";
    }

    public void save(Wishlist wishlist) {
        wishlistRepository.save(wishlist);
    }


}
