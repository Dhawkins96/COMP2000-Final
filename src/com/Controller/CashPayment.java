package com.Controller;

import com.Model.AbstractedView;
import com.Model.Kiosk;

import javax.swing.*;
import java.awt.*;
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

public class CashPayment extends AbstractedView {
    private JPanel CashMain;
    private JButton btnBack;
    private JList lstBasket;
    private JTextField txtTotal;
    private JTextField txtAmount;
    private JButton btnReceipt;
    private JButton btnPay;
    private JTextField txtLeftPay;
    private JLabel lblChange;
    private JTextArea txtRec;

    public static float total = Kiosk.total;

    private File file = new File("Resources\\Pay");

    private String[] stockData = new String[0];
    DefaultListModel<String> basketModel = new DefaultListModel<>();

    public CashPayment() {
        setContentPane(CashMain);
        formDisplay();
        btnReceipt.setEnabled(false);
        txtRec.setVisible(false);
        loadCashBasket();


        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnClearTxtBasket();
                total = 0;
                AbstractedView kiosk = new Kiosk();
                CashPayment.this.setVisible(false);

            }
        });
        btnPay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnPay();
            }
        });
        btnReceipt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtRec.setVisible(true);
                JOptionPane.showMessageDialog(null,"12/01/2021" + System.lineSeparator() + "Items Purchased:" + basketModel + System.lineSeparator() +"Total Price:" + total + "Change Given:" + txtLeftPay.getText(), "Card Payment",JOptionPane.DEFAULT_OPTION);
                txtRec.append("12/01/2021" + System.lineSeparator() + "Items Purchased:" + basketModel + System.lineSeparator() +"Total Price:" + total + System.lineSeparator() + "Change Given:" + txtLeftPay.getText());
            }
        });
    }


    public void loadCashBasket() {
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

    public void btnPay(){
        float given = Float.parseFloat(txtAmount.getText());

        String amountLeft;
        if (given < total){
            txtLeftPay.setText(String.valueOf(total - given));
            txtTotal.setText(String.valueOf(total - given));
            lblChange.setText("More MONEY");
            lblChange.setForeground(Color.RED);
            txtAmount.setText("");
            total = Float.valueOf(txtTotal.getText());

        }else {
            lblChange.setText("Take MONEY");
            txtTotal.setText("0.00");
            txtLeftPay.setText(String.valueOf(given - total));
            txtAmount.setText("");
            txtAmount.setEnabled(false);
            btnReceipt.setEnabled(true);
            btnPay.setEnabled(false);
            btnClearTxtBasket();
        }

    }

}
