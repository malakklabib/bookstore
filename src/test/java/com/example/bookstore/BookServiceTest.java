package com.example.bookstore;

import com.example.bookstore.domain.Book;
import com.example.bookstore.repo.BookRepository;
import com.example.bookstore.service.BookService;
import org.junit.Test;
//import org.testng.annotations.Test;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    @MockBean
    private BookRepository bookRepository;

    @Test
    public void bookIsCreated(){
        Book book = new Book();
        when(bookRepository.save(book)).thenReturn(book);
        assertEquals(book,bookService.save(book));
    }

    @Test
    public void bookIsFound(){
        String isbn = "1";
        Book book = new Book();
        book.setIsbn(isbn);
        when(bookRepository.findById(isbn)).thenReturn(Optional.of(book));
        assertEquals(book, bookService.findById(isbn).get());
    }

//    @Test
//    public void booksAreFound(){
//        when(bookRepository.findById(isbn)).thenReturn(Optional.of(book));
//        assertEquals(book,bookService.findById(isbn).get());
//    }

    @Test
    public void bookIsDeleted(){
        Book book = new Book();
        bookService.delete(book);
        verify(bookRepository, times(1)).delete(book);
    }

    @Test
    public void bookIsUpdated(){
        String isbn = "1";
        Book book = new Book();
        book.setIsbn(isbn);
        when(bookRepository.save(book)).thenReturn(book);
        assertEquals(book, bookService.update(isbn, book));

    }
}
