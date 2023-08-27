package com.example.bookstore.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@RequiredArgsConstructor
@Getter
@Setter
public class PaymentRequestDTO {

    @NonNull
    @NotEmpty(message = "Please enter your phone number.")
    private String phoneNumber;

    @NonNull
    @NotEmpty(message = "Please enter your address.")
    private String address;

    @NonNull
    @NotEmpty(message = "Please enter your card number.")
    @Size(min = 16, max = 16, message = "Please enter a valid card number.")
    private String cardNumber;

    @NonNull
    @Size(min = 2, max = 2, message = "Please enter a valid expiry month.")
    private String expMonth;

    @NonNull
    @NotEmpty(message = "Please enter your expiry year.")
    @Size(min = 4, max = 4, message = "Please enter a valid expiry year.")
    private String expYear;

    @NonNull
    @NotEmpty(message = "Please enter your CVC.")
    @Size(min = 3, max = 3, message = "Please enter a valid CVC.")
    private String cvc;
}
