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

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private BookService bookService;

    private final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @GetMapping("/")
    public String manageBooks(Model m){
        m.addAttribute("books", bookService.findAll());
        return "catalog";
    }

    @PostMapping("/create")
    public String addNewBook(@ModelAttribute Book book, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            logger.info("Not a valid book");
            model.addAttribute("book", book);
            model.addAttribute("validationErrors", bindingResult.getAllErrors());
            return "admin";
        }

        bookService.save(book);
        redirectAttributes
                .addAttribute("id",book.getIsbn())
                .addFlashAttribute("success",true);
        return "redirect:/admin";
    }

    @PutMapping("/{id}")
    public String updateBook(@PathVariable String id, @ModelAttribute Book newBook, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){
        Optional<Book> oldBook = bookService.findById(id);
        if(!oldBook.isPresent()){
            logger.info("Book not found");
            model.addAttribute("validationErrors", bindingResult.getAllErrors());
            return "admin";
        }
        if(bindingResult.hasErrors()){
            logger.info("Not a valid book");
            model.addAttribute("book", newBook);
            model.addAttribute("validationErrors", bindingResult.getAllErrors());
            return "admin";
        }
        bookService.delete(oldBook.get());
        bookService.save(newBook);
        redirectAttributes
                .addAttribute("id",newBook.getIsbn())
                .addFlashAttribute("success",true);
        return "redirect:/admin";
    }

    @PutMapping("/{id}")
    public String deleteBook(@PathVariable String id, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){

        Optional<Book> b = bookService.findById(id);

        if(!b.isPresent()){
            logger.info("Book not found");
            model.addAttribute("validationErrors", bindingResult.getAllErrors());
            return "admin";
        }

        bookService.delete(b.get());
        redirectAttributes.addFlashAttribute("success",true);
        return "redirect:/admin";
    }




}
