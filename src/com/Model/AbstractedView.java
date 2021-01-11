package com.Model;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractedView extends JFrame {

    protected void formDisplay() {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(500, 500));
        this.pack();
        this.setVisible(true);
    }
}

