package com.example.bookstore.service;

import com.paypal.http.HttpResponse;
import com.paypal.orders.AmountWithBreakdown;
import com.paypal.orders.ApplicationContext;
import com.paypal.orders.Order;
import com.paypal.orders.OrderRequest;
import com.paypal.orders.OrdersCreateRequest;
import com.paypal.orders.PurchaseUnitRequest;
import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PayPalService {

    @Value("${paypal.client.id}")
    private String clientId;

    @Value("${paypal.client.secret}")
    private String clientSecret;

    Logger logger = LoggerFactory.getLogger(PayPalService.class);

    public void processPayment(double amount) {
        PayPalEnvironment environment = new PayPalEnvironment.Sandbox(clientId, clientSecret);
        PayPalHttpClient client = new PayPalHttpClient(environment);

        OrderRequest orderRequest = buildOrderRequestBody(amount);

        OrdersCreateRequest request = new OrdersCreateRequest();
        request.requestBody(orderRequest);

        try {
            HttpResponse<Order> response = client.execute(request);
            if (response.statusCode() == 201) {
                Order order = response.result();
            } else
                logger.info("Payment failed: " + response.result());
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
    }

    private OrderRequest buildOrderRequestBody(double amount) {
        OrderRequest orderRequest = new OrderRequest();

        ApplicationContext applicationContext = new ApplicationContext()
                .brandName("Your Brand Name")
                .landingPage("BILLING")
                .userAction("CONTINUE");

        orderRequest.applicationContext(applicationContext);

        List<PurchaseUnitRequest> purchaseUnits = new ArrayList<>();
        purchaseUnits.add(new PurchaseUnitRequest().amountWithBreakdown(new AmountWithBreakdown().value(String.valueOf(amount))));
        orderRequest.purchaseUnits(purchaseUnits);

        return orderRequest;
    }
}
