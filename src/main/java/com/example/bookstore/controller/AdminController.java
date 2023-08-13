package com.example.bookstore.controller;

import com.example.bookstore.domain.Book;
import com.example.bookstore.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class AdminController {

    private final BookService bookService;

    private final Logger logger = LoggerFactory.getLogger(AdminController.class);

    public AdminController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/bookManagement")
    public String manageBooks(Model m){
        m.addAttribute("books", bookService.findAll());
        return "bookManagement";
    }

    @PostMapping("/bookManagement/addNewBook")
    public String addNewBook(@ModelAttribute Book book, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            logger.info("Not a valid book");
            model.addAttribute("book", book);
            model.addAttribute("validationErrors", bindingResult.getAllErrors());
            return "bookManagement";
        }

        bookService.save(book);
        redirectAttributes
                .addAttribute("id",book.getIsbn())
                .addFlashAttribute("success",true);

        return "redirect:/bookManagement";
    }

    @PutMapping("/bookManagement/{isbn}")
    public String updateBook(@PathVariable String isbn, @ModelAttribute Book newBook, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){

        Optional<Book> oldBook = bookService.findById(isbn);

        if(!oldBook.isPresent()){
            logger.info("Book not found");
            model.addAttribute("validationErrors", bindingResult.getAllErrors());
            return "bookManagement";
        }

        if(bindingResult.hasErrors()){
            logger.info("Not a valid book");
            model.addAttribute("newBook", newBook);
            model.addAttribute("validationErrors", bindingResult.getAllErrors());
            return "bookManagement";
        }

        bookService.delete(oldBook.get());
        bookService.save(newBook);
        redirectAttributes
                .addAttribute("id",newBook.getIsbn())
                .addFlashAttribute("success",true);

        return "redirect:/bookManagement";
    }

    @DeleteMapping ("/bookManagement/{isbn}")
    public String deleteBook(@PathVariable String isbn, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){

        Optional<Book> b = bookService.findById(isbn);

        if(!b.isPresent()){
            logger.info("Book not found");
            model.addAttribute("validationErrors", bindingResult.getAllErrors());
            return "bookManagement";
        }

        bookService.delete(b.get());
        redirectAttributes.addFlashAttribute("success",true);

        return "redirect:/bookManagement";
    }




}
