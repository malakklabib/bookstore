package com.example.bookstore.service;

import com.example.bookstore.repo.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.bookstore.domain.Book;

import java.util.Date;
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
        return  bookRepository.findById(id);
    }

    public void delete(Book book){
        bookRepository.delete(book);
    }

    public List<Book> findByParameters(Optional<String> title, Optional<String> author, Optional<Integer> priceMin,
                                       Optional<Integer> priceMax, Optional<Date> dateMin, Optional<Date> dateMax, Optional<Integer> avgRating) {
        List<Book> res = findAll();

        if (title.isPresent())
            res.removeIf(book -> !isPrefixOf(title.get(), book.getTitle()));

        if(author.isPresent())
            res.removeIf(book -> !isPrefixOf(author.get(), book.getAuthor()));

        if(avgRating.isPresent())
            res.removeIf(book -> book.getAvgRating()!=avgRating.get());

        if(dateMin.isPresent())
            res.removeIf(book -> book.getPublicationDate().compareTo(dateMin.get()) < 0);
        if(dateMax.isPresent())
            res.removeIf(book -> book.getPublicationDate().compareTo(dateMax.get()) > 0);

        if(priceMin.isPresent())
            res.removeIf(book -> book.getPrice() < priceMin.get());
        if(priceMax.isPresent())
            res.removeIf(book -> book.getPrice() > priceMax.get());

        return res;
    }

    private boolean isPrefixOf(String s, String title) {
        if(s.length() > title.length())
            return false;
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) != title.charAt(i))
                return false;
        }
        return true;
    }

    public Page<Book> getBooksByPage(Pageable p) {
        return bookRepository.findAll(p);
    }
}
