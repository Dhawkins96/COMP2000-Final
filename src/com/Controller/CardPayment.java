package com.Controller;

import com.Model.AbstractedView;
import com.Model.Kiosk;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CardPayment extends AbstractedView {
    private JPanel CardMain;
    private JButton btnBack;
    private JList lstBasket;
    private JTextField txtTotal;
    private JButton btnPay;
    private JButton btnReceipt;
    private JTextArea txtRec;

    public static float total = Kiosk.total;
    private File file = new File("Resources\\Pay");
    private String[] stockData = new String[0];
    DefaultListModel<String> basketModel = new DefaultListModel<>();

    public CardPayment() {
        setContentPane(CardMain);
        formDisplay();

        txtRec.setVisible(false);
        loadCardBasket();

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnClearTxtBasket();
                total = 0;
                AbstractedView kiosk = new Kiosk();
                CardPayment.this.setVisible(false);

            }
        });
        btnPay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnCardPay();
                btnClearTxtBasket();
                btnPay.setEnabled(false);
                btnReceipt.setEnabled(true);

            }
        });
        btnReceipt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtRec.setVisible(true);
                JOptionPane.showMessageDialog(null,"12/01/2021" + System.lineSeparator() + "Items Purchased:" + basketModel + System.lineSeparator() +"Total Price:" + total, "Card Payment",JOptionPane.DEFAULT_OPTION);

                txtRec.append("12/01/2021" + System.lineSeparator() + "Items Purchased:" + basketModel + System.lineSeparator() +"Total Price:" + total);
                editStock();
            }
        });
    }

    public void loadCardBasket() {
        try {
            List<String> lines = Files.readAllLines(Path.of(String.valueOf(file)));

            for (String line : lines) {

                stockData = line.split("\\|");
                String itemName = stockData[0];
                float itemPrice = Float.parseFloat(stockData[1]);
                basketModel.addElement(stockData[0] + " £" + stockData[1]);
                lstBasket.setModel(basketModel);

            }
            txtTotal.setText(String.valueOf(total));


        } catch (Exception ignored) {

        }
    }

    public void btnClearTxtBasket() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("Resources\\Pay"));
            for (int i = 0; i < lstBasket.getModel().getSize(); i++) {
                bw.write("");
                bw.newLine();
            }
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(Kiosk.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void btnCardPay(){
        JOptionPane.showMessageDialog(null,"Card Payment has been SUCCESSFUL \n Please take your receipt", "Card Payment",JOptionPane.DEFAULT_OPTION);
    }

    public void editStock(){

    }
}
