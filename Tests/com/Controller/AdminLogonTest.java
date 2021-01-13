package com.Controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdminLogonTest {
    AdminLogon adminLogon = new AdminLogon();
    @Test
    void login() {
        adminLogon.login();
    }
}