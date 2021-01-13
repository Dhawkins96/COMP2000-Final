package com.Controller;

import com.Model.AbstractedView;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminLogon extends AbstractedView {
    private JPanel AdminMain;
    private JButton btnLogin;
    private JButton btnBack;
    private JTextField txtUser;
    private JTextField txtPass;

    public AdminLogon() {
        setContentPane(AdminMain);
        formDisplay();

        btnBack.addActionListener(e -> {
            AbstractedView kiosk = new Kiosk();
            AdminLogon.this.setVisible(false);
        });

        btnLogin.addActionListener(e -> login());
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
                String[] data = line.split("\\|");
                //checks the username and password is valid
                if (data[0].equals(Username) && data[1].equals(Password)) {
                    isAuth = true;
                    AbstractedView adminStock = new AdminStock();
                    AdminLogon.this.setVisible(false);
                    break;
                }
            }
            if (!isAuth){
                JOptionPane.showMessageDialog(null,"Username and/or Password INCORRECT! \n Please try again!", "ERROR",JOptionPane.WARNING_MESSAGE);
            }
        } catch (IOException ex) {
            Logger.getLogger(Kiosk.class.getName()).log(Level.SEVERE, null, ex);

        }
    }
}
