package com.Controller;

import com.Model.AbstractedView;
import com.Model.Stock;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminStock extends AbstractedView {
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
    private JList lstReorder;

    public File file = new File("Resources\\FileStock");
    private ArrayList<Stock> items = new ArrayList<>();

    DefaultListModel<String> model = new DefaultListModel<>();
    DefaultListModel<String> Reorder = new DefaultListModel<>();

    public AdminStock() {
        setContentPane(StockAdmin);
        formDisplay();
        loadFile();

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Kiosk kiosk = new Kiosk();
                AdminStock.this.setVisible(false);
            }
        });

        btnRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = lstStock.getSelectedValue();
                deleteItem("Resources\\FileStock", selected, 1);
                AdminStock.this.dispose();
                AbstractedView adminStock = new AdminStock(); //reloads the form

            }

        });

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    addItem();
                    AdminStock.this.dispose();
                    AbstractedView adminStock = new AdminStock(); //reloads the form
                } catch (IOException ex) {
                    Logger.getLogger(Kiosk.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = lstStock.getSelectedValue();
                String name = txtName.getText();
                int barcode = Integer.parseInt(String.valueOf(txtCode.getText()));
                Float price = Float.parseFloat(String.valueOf(txtPrice.getText()));
                int qual = Integer.parseInt(String.valueOf(txtQuan.getText()));

                editItem("Resources\\FileStock",selected, name, barcode, price, qual);

                AdminStock.this.dispose();
                AbstractedView adminStock = new AdminStock();
            }
        });
    }

    public void loadFile() {
        try {
            List<String> lines = Files.readAllLines(Path.of(String.valueOf(file)));
            //uses a List to save the file then the foreach to split the data into the relevant things
            for (String line : lines) {
                String[] stockData = line.split("\\|");
                String itemName = String.valueOf(stockData[0]);
                int codeInt = Integer.parseInt(stockData[1]);
                float priceFloat = Float.parseFloat(stockData[2]);
                int qualInt = Integer.parseInt(stockData[3]);
                items.add(new Stock(itemName, codeInt, priceFloat, qualInt));

                model.addElement(stockData[0]);
                lstStock.setModel(model);
                //checks if stock is less than 5 so the admin user can reorder that item
                if (qualInt < 5){
                    Reorder.addElement(itemName);
                    lstReorder.setModel(Reorder);
                }
                //inputs the users selected into the txt boxes
                lstStock.addListSelectionListener(e -> {
                    String selected = lstStock.getSelectedValue();
                    txtName.setText(selected);
                    if (selected.equals(itemName)) {
                        txtCode.setText(String.valueOf(codeInt));
                        txtPrice.setText(String.valueOf(priceFloat));
                        txtQuan.setText(String.valueOf(qualInt));


                    }
                });
            }
        } catch (IOException ex) {
            Logger.getLogger(Kiosk.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addItem() throws IOException {
        var sw = new BufferedWriter(new FileWriter(file, true));
        int i = 0;
        if (i < items.size()) {
            String data = "";
            if (i < 0) {
                data += "\n";
            }
            //adds item to file
            data += txtName.getText();
            String itemCode = txtCode.getText();
            data += "|" + itemCode;
            String itemPrice = txtPrice.getText();
            data += "|" + itemPrice;
            String itemQual = txtQuan.getText();
            data += "|" + itemQual;
            sw.append(data);

            sw.close();
        }
    }

    public void editItem(String filePath, String editTerm, String newName, int newCode, Float newPrice, int newQual) {
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
                    //edits item by looking for the name in the Stock file and deletes the line
                    while((currentLine = br.readLine()) != null) {

                        String[] data = currentLine.split("\\|");

                        if(!(data[position].equals(editTerm))){
                             pw.println(currentLine);
                        } else {
                            //inputs the new line
                            pw.println(newName + "|" + newCode + "|" + newPrice + "|" + newQual);
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

    public void deleteItem(String filePath, String removeTerm, int positionOfTerm) {
        int position = positionOfTerm - 1;
        String tempFile = "Resources\\TempStock";
        File oldFile = new File(filePath);
        File newFile = new File(tempFile);

        String currentLine;

        try {
            FileWriter fw = new FileWriter(tempFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);

            while ((currentLine = br.readLine()) != null) {
                String[] data = currentLine.split("\\|");
                //deletes full line
                if (!(data[position].equalsIgnoreCase(removeTerm))) {
                    pw.println(currentLine);
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
