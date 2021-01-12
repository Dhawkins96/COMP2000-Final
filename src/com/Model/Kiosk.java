package com.Model;

import com.Controller.CardPayment;
import com.Controller.CashPayment;

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

public class Kiosk extends AbstractedView{
    private JPanel KioskMain;
    private JButton btnLogin;
    private JButton btnAdd;
    private JButton btnCard;
    private JList<String> lstBasket;
    private JButton btnCash;
    private JList<String> lstItems;
    private JTextField txtTotal;

    public static float total = 0;


    public File file = new File("Resources\\FileStock");
    private String[] loadFile = new String[0];

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
                    TransTotal();



                }

            }
        });
        btnCash.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnTxtBasket();
                AbstractedView CashPayment = new CashPayment();

                Kiosk.this.setVisible(false);
                Kiosk.this.dispose();
            }
        });


        btnCard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnTxtBasket();
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

                loadFile = line.split("\\|");
                String itemName = String.valueOf(loadFile[0]);

                int codeInt = Integer.parseInt(loadFile[1]);
                float priceFloat = Float.parseFloat(loadFile[2]);
                int qualInt = Integer.parseInt(loadFile[3]);
                stocks.add(new Stock(itemName, codeInt, priceFloat, qualInt));

                itemModel.addElement(itemName);
                lstItems.setModel(itemModel);

            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

    public void TransTotal() {
        String getItem = lstItems.getSelectedValue();
        Stock item = new Stock();
        Iterator<Stock> iterator = stocks.iterator();

        float itemPrice;

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
                basketModel.addElement(item.itemName + "|" + itemPrice);

            }
        }


    }
    public void btnTxtBasket() {
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
