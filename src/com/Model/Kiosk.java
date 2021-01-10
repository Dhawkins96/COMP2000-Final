package com.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Kiosk extends JFrame {
    private JPanel KioskMain;
    private JButton btnLogin;
    private JButton btnAdd;
    private JButton btnCard;
    private JList lstBasket;
    private JButton btnCash;
    private JList<String> lstItems;


    public File file = new File("Resources\\FileStock");
    private String[] loadFile = new String[0];

    private ArrayList<Stock> stocks = new ArrayList<Stock>();

    DefaultListModel <String> itemModel = new DefaultListModel<>();
    DefaultListModel<String> basketModel = new DefaultListModel<>();

    public Kiosk(String title)  {
        super(title);
        setContentPane(KioskMain);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(500, 500));
        this.setVisible(true);
        pack();

        LoadStock();

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminLogon adminLogon = new AdminLogon("Login");
                Kiosk.this.setVisible(false);
                Kiosk.this.dispose();
            }
        });
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = lstItems.getSelectedIndex();
                String selected = lstItems.getSelectedValue();
                if(index != -1){
                    itemModel.get(index);
                    basketModel.addElement(selected);
                    lstBasket.setModel(basketModel);
                }
            }
        });
    }

    public void LoadStock(){

        try {
            List<String> lines = Files.readAllLines(Path.of(String.valueOf(file)));

            for (String line: lines){

                loadFile = line.split("\\|");
                String itemName = String.valueOf(loadFile[0]);

                int codeInt;
                codeInt = Integer.parseInt(loadFile[1]);
                float priceFloat = Float.parseFloat(loadFile[2]);
                int qualInt = Integer.parseInt(loadFile[3]);
                stocks.add(new Stock(itemName, codeInt, priceFloat, qualInt));

                itemModel.addElement(loadFile[0]);
                lstItems.setModel(itemModel);

            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

}