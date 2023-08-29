package com.example.bookstore;

import com.example.bookstore.domain.Book;
import com.example.bookstore.domain.ShoppingCart;
import com.example.bookstore.domain.Users;
import com.example.bookstore.repo.ShoppingCartRepository;
import com.example.bookstore.service.ShoppingCartService;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ShoppingCartServiceTest {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @MockBean
    private ShoppingCartRepository shoppingCartRepository;

    @Test
    public void bookIsAddedToCart(){
        Users user = new Users();
        ShoppingCart shoppingCart = new ShoppingCart();
        List<Book> shoppingCartItems = new ArrayList<>();
        shoppingCart.setShoppingCartItems(shoppingCartItems);
        user.setShoppingCart(shoppingCart);
        Book book = new Book();
        book.setTitle("title");
        String message = shoppingCartService.addToCart(user, book);
        when(shoppingCartRepository.save(shoppingCart)).thenReturn(shoppingCart);
        assertEquals(book.getTitle() + " added to cart.", message);
    }

    @Test
    public void bookRemovedWhenNotInCart(){
        Users user = new Users();
        ShoppingCart shoppingCart = new ShoppingCart();
        List<Book> shoppingCartItems = new ArrayList<>();
        shoppingCart.setShoppingCartItems(shoppingCartItems);
        user.setShoppingCart(shoppingCart);
        Book book = new Book();
        book.setTitle("title");
        String message = shoppingCartService.removeFromCart(user, book);
        when(shoppingCartRepository.save(shoppingCart)).thenReturn(shoppingCart);
        assertEquals(book.getTitle() + " not found in your cart.", message);
    }

    @Test
    public void bookExistsAndRemovedFromCart(){
        Users user = new Users();
        ShoppingCart shoppingCart = new ShoppingCart();
        List<Book> shoppingCartItems = new ArrayList<>();
        Book book = new Book();
        book.setTitle("title");
        shoppingCartItems.add(book);
        shoppingCart.setShoppingCartItems(shoppingCartItems);
        user.setShoppingCart(shoppingCart);
        String message = shoppingCartService.removeFromCart(user, book);
        when(shoppingCartRepository.save(shoppingCart)).thenReturn(shoppingCart);
        assertEquals(book.getTitle() + " removed from cart.", message);
    }


}
