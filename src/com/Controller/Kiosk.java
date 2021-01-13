package com.Controller;

import com.Model.AbstractedView;
import com.Model.Stock;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Kiosk extends AbstractedView {
    private JPanel KioskMain;
    private JButton btnLogin;
    private JButton btnAdd;
    private JButton btnCard;
    private JList<String> lstBasket;
    private JButton btnCash;
    private JList<String> lstItems;
    private JTextField txtTotal;
    //Total has been made static so it can be read from other classes
    public static float total = 0;

    public File file = new File("Resources\\FileStock");

    private ArrayList<Stock> stocks = new ArrayList<Stock>();

    DefaultListModel <String> itemModel = new DefaultListModel<>();
    DefaultListModel<String> basketModel = new DefaultListModel<>();

    public Kiosk()  {
        setContentPane(KioskMain);
        formDisplay();
        LoadStock();

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AbstractedView adminLogon = new AdminLogon();
                Kiosk.this.setVisible(false);
                Kiosk.this.dispose();
            }
        });

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = lstItems.getSelectedIndex();
                if(index != -1){
                    itemModel.get(index);
                    lstBasket.setModel(basketModel);
                    Total();
                }
            }
        });

        btnCash.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CheckoutBasket();
                AbstractedView CashPayment = new CashPayment();

                Kiosk.this.setVisible(false);
                Kiosk.this.dispose();
            }
        });

        btnCard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CheckoutBasket();
                AbstractedView CardPayment = new CardPayment();

                Kiosk.this.setVisible(false);
                Kiosk.this.dispose();
            }
        });
    }

    public void LoadStock(){

        try {
            List<String> lines = Files.readAllLines(Path.of(String.valueOf(file)));

            for (String line: lines){

                String[] loadFile = line.split("\\|");
                String itemName = String.valueOf(loadFile[0]);

                int codeInt = Integer.parseInt(loadFile[1]);
                float priceFloat = Float.parseFloat(loadFile[2]);
                int qualInt = Integer.parseInt(loadFile[3]);
                stocks.add(new Stock(itemName, codeInt, priceFloat, qualInt));
                //adds the file to the lst box
                itemModel.addElement(itemName);
                lstItems.setModel(itemModel);
            }

        } catch (IOException ex) {
            Logger.getLogger(Kiosk.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Total() {
        String getItem = lstItems.getSelectedValue();
        Stock item = new Stock();
        Iterator<Stock> iterator = stocks.iterator();

        float itemPrice;
        //adds up the total of the selected items and uses the get price from Stock class
        for(basketModel.elements().asIterator(); iterator.hasNext();){
            item =  iterator.next();
            if(getItem.equals(item.itemName)){
                itemPrice = item.getItemPrice();
                total = itemPrice;
                if(!txtTotal.getText().isEmpty()){
                    total = total + Float.parseFloat(txtTotal.getText()) ;
                    txtTotal.setText(String.valueOf(total));
                } else {
                    txtTotal.setText(String.valueOf(total));

                }
                //includes all the elements to the line in the basket
                basketModel.addElement(item.itemName + "|" + item.itemCode + "|" + itemPrice + "|" + item.itemQuantity);
            }
        }


    }
    public void CheckoutBasket() {
        //saves the lstBasket to a file for printing in the payment classes
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("Resources\\Pay"));
            for (int i=0; i<lstBasket.getModel().getSize(); i++){
                bw.write(lstBasket.getModel().getElementAt(i));
                bw.newLine();
            }
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(Kiosk.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
