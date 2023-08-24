package com.example.bookstore.service;

import com.example.bookstore.domain.Order;
import com.example.bookstore.domain.Users;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class MailService {

    private final UserService userService;
    private final BookService bookService;

    private final Logger log = LoggerFactory.getLogger(MailService.class);
    private final SpringTemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;


    @Async
    public void sendEmail(String to, String subject, String content, boolean isHtml) {
        log.debug("Sending Email");

        MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
            message.setTo(to);
            message.setFrom("noreply@springit.com");
            message.setSubject(subject);
            message.setText(content, isHtml);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            log.warn("Email could not be sent to user '{}': {}", to, e.getMessage());
        }
    }

    @Async
    public void sendEmailFromTemplate(Users users, String templateName, String subject) {
        Locale locale = Locale.ENGLISH;
        Context context = new Context(locale);
        context.setVariable("user", users);
        String BASE_URL = "http://localhost:8080";
        context.setVariable("baseURL", BASE_URL);
        String content = templateEngine.process(templateName, context);
        sendEmail(users.getEmail(), subject, content, true);
    }

    @Async
    public void sendWelcomeEmail(Users users) {
        sendEmailFromTemplate(users, "email/welcome", "Welcome, " + users.getName() + "!");
    }

    @Async
    public void sendConfirmationEmail(Users user, Order order) {
        sendEmailFromTemplate(user, "email/welcome", "Order #" + order.getId() + " is confirmed!");
    }

    @Async
    public void sendShipmentEmail(Users user, Order order) {
        sendEmailFromTemplate(user, "email/welcome", "Order #" + order.getId() + " is shipped!");
    }

    @Scheduled(fixedRate = 1440 * 60 * 1000) // 1 day = 1440 mins (time in milliseconds)
    public void sendMostCommonCategoryEmail() {

        List<Users> users = userService.findAll();

        for (Users u : users) {

            String mostCommonCategory = bookService.findMostCommonCategory(u);
            if (mostCommonCategory.isEmpty())
                return;

            String recBooks = bookService.getRecommendedBooks(mostCommonCategory);
            if (recBooks.isEmpty())
                return;

            String userEmail = u.getEmail();
            String subject = "Your top genre is " + mostCommonCategory + "!";
            String text = "Your daily recommendations are: " + recBooks;
            sendEmail(userEmail, subject, text, true);

        }
    }

}
