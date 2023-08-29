package com.example.bookstore.service;

import com.example.bookstore.domain.Order;
import com.example.bookstore.domain.OrderItem;
import com.example.bookstore.domain.Users;
import com.example.bookstore.repo.BookRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.bookstore.domain.Book;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final OrderService orderService;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book save(Book b) {
        return bookRepository.save(b);
    }

    public Optional<Book> findById(String id) {
        return bookRepository.findById(id);
    }

    public void delete(Book book) {
        bookRepository.delete(book);
    }

    public Book update(String id, Book updatedBook) {
        updatedBook.setIsbn(id);
        return save(updatedBook);
    }

    public List<Book> findByParameters(Optional<String> title, Optional<String> author, Optional<Double> priceMin,
                                       Optional<Double> priceMax, Optional<Date> dateMin, Optional<Date> dateMax, Optional<Double> avgRating) {
        List<Book> res = findAll();

        if (title.isPresent())
            res.removeIf(book -> !isPrefixOf(title.get(), book.getTitle()));

        if (author.isPresent())
            res.removeIf(book -> !isPrefixOf(author.get(), book.getAuthor()));

        if (avgRating.isPresent())
            res.removeIf(book -> book.getAvgRating() != avgRating.get());

        if (dateMin.isPresent())
            res.removeIf(book -> book.getPublicationDate().compareTo(dateMin.get()) < 0);
        if (dateMax.isPresent())
            res.removeIf(book -> book.getPublicationDate().compareTo(dateMax.get()) > 0);

        if (priceMin.isPresent())
            res.removeIf(book -> book.getPrice() < priceMin.get());
        if (priceMax.isPresent())
            res.removeIf(book -> book.getPrice() > priceMax.get());

        return res;
    }

    private boolean isPrefixOf(String s, String title) {
        if (s.length() > title.length())
            return false;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != title.charAt(i))
                return false;
        }
        return true;
    }

    public Page<Book> getBooksByPage(Pageable p) {
        return bookRepository.findAll(p);
    }

    public String getRecommendedBooks(String category) {
        String s = "";
        List<String> books = bookRepository.findTop5ByCategoryOrderByAvgRatingDesc(category).stream().map(Book::getTitle).toList();

        for (String b : books) {
            if (books.indexOf(b) == books.size() - 1)
                s += b;
            else
                s += b + ", ";
        }
        return s;
    }

    public String findMostCommonCategory(Users user) {
        List<Order> orders = orderService.findTop3ByEmail(user.getEmail());
        Map<String, Integer> categoryCount = categoryCount(orders);

        if (!categoryCount.isEmpty())
            return Collections.max(categoryCount.entrySet(), Map.Entry.comparingByValue()).getKey();

        return "";
    }

    public String findMostPopularGenres() {
        List<Order> orders = orderService.findAll();
        Map<String, Integer> categoryCount = categoryCount(orders);
        String result = "";

        for (int i = 0; !categoryCount.isEmpty() && i < 3; i++) {
            String category = Collections.max(categoryCount.entrySet(), Map.Entry.comparingByValue()).getKey();
            categoryCount.remove(category);
            result += category + "\n";
        }

        return result;
    }

    public String findMostPopularBooks(){
        List<Order> orders = orderService.findAll();
        Map<String, Integer> isbnCount = isbnCount(orders);
        String result = "";

        for (int i = 0; !isbnCount.isEmpty() && i < 3; i++) {
            String isbn = Collections.max(isbnCount.entrySet(), Map.Entry.comparingByValue()).getKey();
            isbnCount.remove(isbn);
            result += findById(isbn).get().getTitle() + "\n";
        }

        return result;

    }

    public Map<String, Integer> categoryCount(List<Order> orders) {
        Map<String, Integer> categoryCount = new HashMap<>();

        for (Order order : orders) {
            for (OrderItem orderItem : order.getOrderItems()) {
                Optional<Book> book = findById(orderItem.getIsbn());
                if (book.isPresent()) {
                    String category = book.get().getCategory();
                    categoryCount.put(category, categoryCount.getOrDefault(category, 0) + 1);
                }
            }
        }
        return categoryCount;
    }

    public Map<String, Integer> isbnCount(List<Order> orders) {
        Map<String, Integer> isbnCount = new HashMap<>();

        for (Order order : orders) {
            for (OrderItem orderItem : order.getOrderItems()) {
                Optional<Book> book = findById(orderItem.getIsbn());
                if (book.isPresent()) {
                    String isbn = book.get().getIsbn();
                    isbnCount.put(isbn, isbnCount.getOrDefault(isbn, 0) + 1);
                }
            }
        }
        return isbnCount;
    }


}
