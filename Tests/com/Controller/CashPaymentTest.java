package com.Controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CashPaymentTest {
    CashPayment cashPayment = new CashPayment();
    @Test
    void loadCashBasket() {
        cashPayment.loadCashBasket();
    }
}