package com.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminStock extends AdminLogon{
    private JLabel lblAdminName;
    private JList lstStock;
    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnRemove;
    private JButton btnBack;
    private JPanel StockAdmin;

    public AdminStock(String title) {
        super(title);
        setContentPane(StockAdmin);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(500, 500));
        this.setVisible(true);
        pack();

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Kiosk kiosk = new Kiosk("Home");
                AdminStock.this.setVisible(false);
            }
        });


    }
}
