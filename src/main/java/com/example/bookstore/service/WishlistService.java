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

    public List<Book> getWishlistBooks(Users u) {
        return u.getWishlist().getWishlistItems();
    }


    public void addToWishlist(Users u, Book b) {
        Wishlist wishlist = u.getWishlist();
        wishlist.addItem(b);
        save(wishlist);
        userService.save(u);
    }

    public String removeFromWishlist(Users u, Book b){
        Wishlist wishlist = u.getWishlist();
        if(!wishlist.getWishlistItems().contains(b))
            return "Book is not in your wishlist";
        wishlist.removeItem(b);
        save(wishlist);
        userService.save(u);
        return "Book removed from wishlist.";
    }

    public void updateWishLists(Book old, Book updated) {
        List<Wishlist> wishlists = findAll();
        for(Wishlist w : wishlists) {
            List<Book> books = w.getWishlistItems();
            if(books.contains(old)){
                books.add(updated);
                books.remove(old);
                w.setWishlistItems(books);
                save(w);
                Users u = userService.findByWishlistId(w.getWishListId());
                u.setWishlist(w);
                userService.save(u);
            }
        }
    }

    public void deleteFromWishlists(Book book) {
        List<Wishlist> wishlists = findAll();
        for(Wishlist w : wishlists) {
            List<Book> books = w.getWishlistItems();
            books.removeAll(books.stream().filter(book1 -> book1.equals(book)).toList());
            w.setWishlistItems(books);
            save(w);
            Users u = userService.findByWishlistId(w.getWishListId());
            u.setWishlist(w);
            userService.save(u);
        }

    }

    public void save(Wishlist wishlist) {
        wishlistRepository.save(wishlist);
    }

    public List<Wishlist> findAll() {
        return wishlistRepository.findAll();
    }


}
