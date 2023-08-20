package com.example.bookstore.service;

import com.example.bookstore.domain.Book;
import com.example.bookstore.domain.ShoppingCart;
import com.example.bookstore.domain.Users;
import com.example.bookstore.repo.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {

    private final UserService userService;
    private final ShoppingCartRepository shoppingCartRepository;

    public void addToCart(Users u, Book b){
        ShoppingCart shoppingCart = u.getShoppingCart();
        shoppingCart.addItem(b);
        u.setShoppingCart(shoppingCart);
        save(shoppingCart);
        userService.save(u);
    }

    public void removeFromCart(Users u, Book b){
        ShoppingCart shoppingCart = u.getShoppingCart();
        shoppingCart.removeItem(b);
        u.setShoppingCart(shoppingCart);
        save(shoppingCart);
        userService.save(u);
    }

    public void updateShoppingCarts(Book old, Book updated) {
        List <ShoppingCart> shoppingCarts = findAll();
        for(ShoppingCart c: shoppingCarts) {
            List<Book> books = c.getShoppingCartItems();
            if(books.contains(old)) {
                books.add(updated);
                books.remove(old);
                c.setShoppingCartItems(books);
                save(c);
                Users u = userService.findByShoppingCartId(c.getShoppingCartId());
                u.setShoppingCart(c);
                userService.save(u);
            }
        }
    }
    public void deleteFromShoppingCarts(Book book) {
        List <ShoppingCart> shoppingCarts = findAll();
        for(ShoppingCart c: shoppingCarts) {
            List<Book> books = c.getShoppingCartItems();
            books.removeAll(books.stream().filter(book1 -> book1.equals(book)).toList());
            c.setShoppingCartItems(books);
            save(c);
            Users u = userService.findByShoppingCartId(c.getShoppingCartId());
            u.setShoppingCart(c);
            userService.save(u);
        }
    }

    public ShoppingCart save(ShoppingCart shoppingCart) {
         return shoppingCartRepository.save(shoppingCart);
    }


    public List<ShoppingCart> findAll() {
        return shoppingCartRepository.findAll();
    }

    public Optional<ShoppingCart> findById(String shoppingCartId) {
        return shoppingCartRepository.findById(shoppingCartId);
    }
}
