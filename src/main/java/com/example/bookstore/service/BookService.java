package com.example.bookstore.service;

import com.example.bookstore.controller.AdminController;
import com.example.bookstore.repo.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.bookstore.domain.Book;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    private final Logger logger = LoggerFactory.getLogger(BookService.class);


    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    public Book save(Book b){
        return bookRepository.save(b);
    }

    public Optional<Book> findById(String id) {
        Optional<Book> b = bookRepository.findById(id);

        if(!b.isPresent())
            logger.info("Book is not found");

        return b;
    }

    public void delete(Book book){
        bookRepository.delete(book);
    }
}
