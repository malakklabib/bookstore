package com.example.bookstore.service;

import com.example.bookstore.domain.Order;
import com.example.bookstore.domain.OrderItem;
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

    private final OrderService orderService;

    private final Logger log = LoggerFactory.getLogger(MailService.class);
    private final SpringTemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;
    private final String BASE_URL = "http://localhost:8080";


    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultiPart, boolean isHtml) {
        log.debug("Sending Email");

        MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
            message.setTo(to);
            message.setFrom("noreply@springit.com");
            message.setSubject(subject);
            message.setText(content,isHtml);
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
        context.setVariable("baseURL",BASE_URL);
        String content = templateEngine.process(templateName,context);
        sendEmail(users.getEmail(),subject,content,false,true);
    }

    @Async
    public void sendWelcomeEmail(Users users) {
        log.debug("Sending activation email to '{}'", users.getEmail());
        sendEmailFromTemplate(users, "email/welcome", "Welcome, "+users.getName()+"!");
    }

    @Async
    public void sendConfirmationEmail(Users user, Order order) {
        log.debug("Sending activation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "email/welcome", "Order #"+order.getId()+" is confirmed!");
    }

    @Async
    public void sendShipmentEmail(Users user, Order order) {
        log.debug("Sending activation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "email/welcome", "Order #"+order.getId()+" is shipped!");
    }

    @Scheduled(fixedRate = 2 * 60 * 100000)
    public void sendMostCommonOrderItemEmail() {
        List<OrderItem> mostCommonOrderItem = orderService.findMostCommonOrderItem();

        if (!mostCommonOrderItem.isEmpty()) {
            String userEmail = "admin@gmail.com";
            String subject = "Your Most Common Order Item";
            String text = "Your most common order item is: " + mostCommonOrderItem.get(0).toString();

            sendEmail(userEmail, subject, text,false,true);
        }
    }

}
