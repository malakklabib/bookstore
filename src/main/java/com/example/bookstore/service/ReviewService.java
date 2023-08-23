package com.example.bookstore.service;

import com.example.bookstore.domain.Book;
import com.example.bookstore.domain.Review;
import com.example.bookstore.repo.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    public Review save(Review r){
        return reviewRepository.save(r);
    }

    public List<Review> findAllByBookId(String bookId) {
        return reviewRepository.findAll().stream().filter(review -> review.getBookId().equals(bookId)).toList();
    }

    public void deleteAllReviews(Book book) {
        if(isEmpty())
            return;
        findAllByBookId(book.getIsbn()).removeIf(review -> review.getBookId().equals(book.getIsbn()));
    }

    public boolean isEmpty(){ return reviewRepository.findAll().isEmpty(); }
}
