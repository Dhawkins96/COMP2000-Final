package com.Model;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class AdminStock extends Kiosk {
    private JLabel lblAdminName;
    private JList<String> lstStock;
    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnRemove;
    private JButton btnBack;
    private JPanel StockAdmin;
    private JTextField txtName;
    private JTextField txtCode;
    private JTextField txtPrice;
    private JTextField txtQuan;

    public File file = new File("Resources\\FileStock");
    private ArrayList<Stock> stocks = new ArrayList<Stock>();

    public AdminStock(String title) {
        super(title);
        setContentPane(StockAdmin);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(500, 500));
        this.setVisible(true);
        pack();


        loadFile();

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Kiosk kiosk = new Kiosk("Home");
                AdminStock.this.setVisible(false);
            }
        });



    }

    public void loadFile(){

        try {
             Scanner scanner = new Scanner(file);
             DefaultListModel <String> model = new DefaultListModel<>();

            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                String[] stockData = data.split("\\|");

                Stock stock = new Stock();

                String itemName = String.valueOf(stockData[0]);
                stock.setItemName(itemName);

                int codeInt = Integer.parseInt(stockData[1]);
                stock.setItemCode(codeInt);

                float priceFloat = Float.parseFloat(stockData[2]);
                stock.setItemPrice(priceFloat);

                int qualInt = Integer.parseInt(stockData[3]);
                stock.setItemQuantity(qualInt);

                Stock tempItem = new Stock(itemName, codeInt, priceFloat, qualInt);
                stocks.add(tempItem);

                model.addElement(stockData[0]);
                lstStock.setModel(model);

                lstStock.addListSelectionListener(e -> {
                    String selected = lstStock.getSelectedValue();
                    txtName.setText(selected);
                    if (selected.equals(stockData[0])){
                        txtCode.setText(String.valueOf(codeInt));
                        txtPrice.setText(String.valueOf(priceFloat));
                        txtQuan.setText(String.valueOf(qualInt));

                    }
                });

        }


        } catch(FileNotFoundException fe){
            fe.getMessage();
        }

    }



}
