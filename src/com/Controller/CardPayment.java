package com.Controller;

import com.Model.AbstractedView;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Timestamp;
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
                deleteCardFile();
                total = 0;
                AbstractedView kiosk = new Kiosk();
                CardPayment.this.setVisible(false);

            }
        });
        btnPay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardPay();

                btnPay.setEnabled(false);
                btnReceipt.setEnabled(true);

            }
        });
        btnReceipt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtRec.setVisible(true);
                // gets up receipt in both test area and JOptionPane

                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                JOptionPane.showMessageDialog(null, timestamp + System.lineSeparator() +
                        "Thank you for using Quarantine Shopping \n Where your only job is to stay home \n Stay safe out there" +
                        "Items Purchased:" + basketModel + System.lineSeparator() +"Total Price:" + total, "Card Payment",JOptionPane.DEFAULT_OPTION);

                txtRec.append(timestamp + System.lineSeparator() +
                        "Thank you for using Quarantine Shopping \n Where your only job is to stay home \n Stay safe out there" +
                        "Items Purchased:" + basketModel + System.lineSeparator() +"Total Price:" + total);
                //deletes the Pay file as items in basket have been paid for
                deleteCardFile();
            }
        });
    }

    public void loadCardBasket() {
        try {
            List<String> lines = Files.readAllLines(Path.of(String.valueOf(file)));
            // loads the basket in from the Pay File
            for (String line : lines) {

                stockData = line.split("\\|");
                String itemName = stockData[0];
                int codeInt = Integer.parseInt(stockData[1]);
                float priceFloat = Float.parseFloat(stockData[2]);
                int qualInt = Integer.parseInt(stockData[3]);
                basketModel.addElement( stockData[0] + " £ " + stockData[2]);
                lstBasket.setModel(basketModel);

            }
            txtTotal.setText(String.valueOf(total));

        } catch (IOException ex) {
            Logger.getLogger(Kiosk.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void CardPay(){
        //Bank approved message
        JOptionPane.showMessageDialog(null,"Bank has Approved \n Card Payment was SUCCESSFUL \n Please take your receipt", "Card Payment",JOptionPane.DEFAULT_OPTION);
        try {
            //edits the Stock file
            List<String> lines = Files.readAllLines(Path.of(String.valueOf(file)));
            for (String line : lines) {
                stockData = line.split("\\|");
                editCardStock("Resources\\FileStock");
            }
        } catch (IOException ex) {
            Logger.getLogger(Kiosk.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void editCardStock(String filePath){
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
    public void deleteCardFile() {
        File pay = new File("Resources\\Pay");
        pay.delete();
    }
}
