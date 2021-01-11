package com.Model;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


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

    public File file = new File("Resources\\FileStock");
    private static Scanner x;
    private String[] stockData = new String[0];
    private ArrayList<Stock> items = new ArrayList<>();

    DefaultListModel<String> model = new DefaultListModel<>();

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
                } catch (IOException ioException) {
                    ioException.printStackTrace();
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

                editItem("Resources\\FileStock",selected, 1, name, barcode, price, qual);

                AdminStock.this.dispose();
                AbstractedView adminStock = new AdminStock(); //reloads the form
            }
        });
    }

    public void loadFile() {

        try {

            List<String> lines = Files.readAllLines(Path.of(String.valueOf(file)));

            for (String line : lines) {
                Stock stock = new Stock();
                stockData = line.split("\\|");
                String itemName = String.valueOf(stockData[0]);
                stock.setItemName(itemName);
                int codeInt = Integer.parseInt(stockData[1]);
                float priceFloat = Float.parseFloat(stockData[2]);
                int qualInt = Integer.parseInt(stockData[3]);
                items.add(new Stock(itemName, codeInt, priceFloat, qualInt));

                model.addElement(stockData[0]);
                lstStock.setModel(model);

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

        } catch (IOException ioException) {
            ioException.printStackTrace();
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

    public void editItem(String filePath, String editTerm, int positionOfTerm, String newName, int newCode, Float newPrice, int newQual) {
            int position = positionOfTerm - 1;
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


                    while((currentLine = br.readLine()) != null) {


                        String[] data = currentLine.split("\\|");

                        if(!(data[position].equals(editTerm))){
                             pw.println(currentLine);
                        } else {
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

            }
            catch (Exception ignored){ }

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
        } catch (Exception e) { }
    }
}
