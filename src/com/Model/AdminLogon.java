package com.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AdminLogon extends AbstractedView {
    private JPanel AdminMain;
    private JButton btnLogin;
    private JButton btnBack;
    private JTextField txtUser;
    private JTextField txtPass;

    public AdminLogon() {

        setContentPane(AdminMain);
        formDisplay();

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AbstractedView kiosk = new Kiosk();
                AdminLogon.this.setVisible(false);
            }
        });

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
    }

    public void login(){

        boolean isAuth = false;

        String Username = txtUser.getText();
        String Password = txtPass.getText();

        File file = new File("Resources\\FileAdmin");
        try {
            Scanner inputBuffer = new Scanner(file);

            while(inputBuffer.hasNext()) {
                String line = inputBuffer.nextLine();
                String[] values = line.split("\\|");

                if (values[0].equals(Username) && values[1].equals(Password)) {
                    isAuth = true;
                    AbstractedView adminStock = new AdminStock();
                    AdminLogon.this.setVisible(false);
                    break;
                }
            }
            if (!isAuth){
                JOptionPane.showMessageDialog(null,"Username and/or Password INCORRECT! \n Please try again", "ERROR",JOptionPane.WARNING_MESSAGE);
            }

        } catch (FileNotFoundException fe){
            fe.getMessage();

        }
    }
}
