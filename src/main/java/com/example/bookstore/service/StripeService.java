package com.example.bookstore.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.param.ChargeCreateParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeService {

    private Logger logger = LoggerFactory.getLogger(StripeService.class);

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    public void chargeCustomer(double amount) throws StripeException {
        Stripe.apiKey = stripeApiKey;
        long amountInCents = (long) (amount * 100);

//        Map<String, Object> cardParams = new HashMap<>();
//        cardParams.put("number", cardNumber);
//        cardParams.put("exp_month", expMonth);
//        cardParams.put("exp_year", expYear);
//        cardParams.put("cvc", cvc);
//
//        Map<String, Object> tokenParams = new HashMap<>();
//        tokenParams.put("card", cardParams);
//
//        Token token = Token.create(tokenParams);

        ChargeCreateParams chargeParams = ChargeCreateParams.builder()
                .setAmount(amountInCents)
                .setCurrency("usd")
                .setSource("tok_visa")
                .build();

        Charge.create(chargeParams);

    }
}

