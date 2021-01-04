package com.View;

import javax.swing.*;
import java.awt.*;

public class Kiosk extends JFrame {
    private JPanel main;


    public Kiosk(String title) {
        super(title);
        setContentPane(main);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(500, 500));
        this.setVisible(true);
        pack();

    };

}
