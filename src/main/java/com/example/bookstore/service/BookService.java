package com.example.bookstore.service;

import com.example.bookstore.domain.Order;
import com.example.bookstore.domain.OrderItem;
import com.example.bookstore.domain.Users;
import com.example.bookstore.repo.BookRepository;
import lombok.RequiredArgsConstructor;
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

    public List<Book> findByParameters(Optional<String> title, Optional<String> author, Optional<Double> priceMin,
                                       Optional<Double> priceMax, Optional<Date> dateMin, Optional<Date> dateMax, Optional<Double> avgRating) {
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

    public String getRecommendedBooks(String category) {
        String s = "";
        List<String> books = bookRepository.findTop5ByCategoryOrderByAvgRatingDesc(category).stream().map(Book::getTitle).toList();

        for(String b : books){
            if(books.indexOf(b)== books.size()-1)
                s += b;
            else
                s += b + ", ";
        }
        return s;
    }
    public String findMostCommonCategory(Users user) {
        List<Order> orders = orderService.findTop3ByEmail(user.getEmail());
        Map<String, Integer> categoryCountMap = new HashMap<>();

        for (Order order : orders) {
            for (OrderItem orderItem : order.getOrderItems()) {
                Optional<Book> bookOptional = findById(orderItem.getIsbn());
                if (bookOptional.isPresent()) {
                    String category = bookOptional.get().getCategory();
                    categoryCountMap.put(category, categoryCountMap.getOrDefault(category, 0) + 1);
                }
            }
        }

        if (!categoryCountMap.isEmpty()) {
            String mostCommonCategory = Collections.max(categoryCountMap.entrySet(), Map.Entry.comparingByValue()).getKey();
            return mostCommonCategory;
        }

        return "";
    }


//    public String findMostCommonCategory(Users user) {
//        List <Order> orders = orderService.findTop3ByEmail(user.getEmail());
//        List<String> books = new ArrayList<>();
//
//        for(Order order : orders){
//            for(OrderItem orderItem : order.getOrderItems()){
//                Optional<Book> b = findById(orderItem.getIsbn());
//                if(b.isEmpty())
//                    continue;
//                books.add(b.get().getCategory());
//            }
//        }
//        List<Book> mostCommonCategory = bookRepository.findAllByIsbnInOrderByCategoryDesc(books);
//        if(!mostCommonCategory.isEmpty())
//            return bookRepository.findAllByIsbnInOrderByCategoryDesc(books).get(0).getCategory();
//        return "";
//    }

}
