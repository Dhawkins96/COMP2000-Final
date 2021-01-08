package com.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Kiosk extends JFrame {
    private JPanel KioskMain;
    private JButton btnLogin;


    public Kiosk(String title) {
        super(title);
        setContentPane(KioskMain);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(500, 500));
        this.setVisible(true);
        pack();

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminLogon adminLogon = new AdminLogon("Login");
                Kiosk.this.setVisible(false);
            }
        });
    };

}
