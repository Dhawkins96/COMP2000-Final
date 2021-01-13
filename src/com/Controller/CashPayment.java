package com.Controller;

import com.Model.AbstractedView;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Timestamp;
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
    //gets total from Kiosk
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
                deleteCashFile();
                total = 0;
                AbstractedView kiosk = new Kiosk();
                CashPayment.this.setVisible(false);

            }
        });
        btnPay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CashPay();

            }
        });
        btnReceipt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnReceipt.setEnabled(false);
                txtRec.setVisible(true);
                // gets up receipt in both test area and JOptionPane
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                JOptionPane.showMessageDialog(null, timestamp + System.lineSeparator() +
                        "Thank you for using Quarantine Shopping \n Where your only job is to stay home \n Stay safe out there"
                        + System.lineSeparator() +
                        "Items Purchased:" + basketModel + System.lineSeparator() +"Total Price:" + total, "Cash Payment"
                        + System.lineSeparator() + "Change Given:" + txtLeftPay.getText(),JOptionPane.DEFAULT_OPTION);

                txtRec.append(timestamp + System.lineSeparator() +
                        "Thank you for using Quarantine Shopping \n Where your only job is to stay home \n Stay safe out there" +
                        System.lineSeparator() +
                        "Items Purchased:" + basketModel + System.lineSeparator() +"Total Price:" + total
                        + System.lineSeparator() + "Change Given:" + txtLeftPay.getText());
            }
        });
    }


    public void loadCashBasket() {
        try {
            List<String> lines = Files.readAllLines(Path.of(String.valueOf(file)));
            // loads the basket in from the Pay File
            for (String line : lines) {
                stockData = line.split("\\|");
                String itemName = stockData[0];
                float itemPrice = Float.parseFloat(stockData[1]);
                basketModel.addElement(stockData[0] + " £" + stockData[2]);
                lstBasket.setModel(basketModel);
            }
             txtTotal.setText(String.valueOf(total));

        } catch (Exception ignored) {

        }
    }

    public void deleteCashFile() {
        File pay = new File("Resources\\Pay");
        pay.delete();
    }

    public void CashPay(){
        float given = Float.parseFloat(txtAmount.getText());
        //first if is to check the amount is less than the total
        if (given < total){
            txtLeftPay.setText(String.valueOf(total - given));
            txtTotal.setText(String.valueOf(total - given));
            lblChange.setText("More MONEY");
            lblChange.setForeground(Color.RED);
            txtAmount.setText("");
            total = Float.valueOf(txtTotal.getText());

            //once total has been reached it prints the change and disables the buttons so you can pay more times
        }else {
            lblChange.setText("Take MONEY");
            txtTotal.setText("0.00");
            txtLeftPay.setText(String.valueOf(given - total));
            txtAmount.setText("");
            txtAmount.setEnabled(false);
            btnReceipt.setEnabled(true);
            btnPay.setEnabled(false);

            try {
                //decreases the Stock file from the items bought
                List<String> lines = Files.readAllLines(Path.of(String.valueOf(file)));
                for (String line : lines) {
                    stockData = line.split("\\|");
                    editCashStock("Resources\\FileStock");
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            deleteCashFile();

        }

    }

    public void editCashStock(String filePath){
        int position = 0;

        String tempFile = "Resources\\TempStock";
        File oldFile = new File(filePath);
        File newFile = new File(tempFile);

        String currentLine;

        try{
            FileWriter fw = new FileWriter(tempFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);


            while ((currentLine = br.readLine()) != null) {

                String[] data = currentLine.split("\\|");

                int qualInt = Integer.parseInt(stockData[3]);
                qualInt = qualInt - 1;
                if (!(data[position].equals(stockData[0]))) {
                    pw.println(currentLine);
                } else {
                    pw.println(stockData[0] + "|" + stockData[1] + "|" + stockData[2] + "|" + qualInt);
                }
            }

            pw.flush();
            pw.close();
            fr.close();
            br.close();
            bw.close();
            fw.close();
            oldFile.delete();
            File dump = new File(filePath);
            newFile.renameTo(dump);

        } catch (IOException ex) {
            Logger.getLogger(Kiosk.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
