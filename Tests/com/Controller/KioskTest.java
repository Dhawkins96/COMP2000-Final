package com.Controller;

import static org.junit.jupiter.api.Assertions.*;

class KioskTest {
    Kiosk kiosk = new Kiosk();
    @org.junit.jupiter.api.Test
    void loadStock() {
        kiosk.LoadStock();
    }
}