package com.Controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardPaymentTest {
    CardPayment cardPayment = new CardPayment();
    @Test
    void loadCardBasket() {
        cardPayment.loadCardBasket();

    }
}