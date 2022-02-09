package com.example;

import com.example.customer.HomeActivity;
import com.example.vendor.HomeVendor;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginActivity {
    public static TextField textName = new TextField();
    public static TextField textPhoneNB = new TextField();
    public static TextField textPass = new TextField();
    public static void main(String[] args) {
        Frame f = new Frame("Login");

        Label l1 = new Label("NAME");
        l1.setBounds(20,30, 35,30);
        Label l2 = new Label("PHONE");
        l2.setBounds(20,90, 43,30);
        Label l3 = new Label("PASS");
        l3.setBounds(20,150, 35,30);


        Button b = new Button("LOGIN");
        b.setBounds(198, 220, 50,30);
        Button signUp = new Button("SignUp");
        signUp.setBounds(30,220,50,30);

        textName.setBounds(70,30, 180, 30);
        textPhoneNB.setBounds(70,90, 180, 30);
        textPass.setBounds(70,150, 180, 30);

        f.add(l1);
        f.add(textName);
        f.add(l2);
        f.add(textPhoneNB);
        f.add(l3);
        f.add(textPass);
        f.add(b);
        f.add(signUp);

        f.setLayout(null);
        f.setSize(300, 300);
        f.setVisible(true);

        b.addActionListener(event -> {
            String name = textName.getText();
            String phone = textPhoneNB.getText();
            String pass = textPass.getText();
            try {
                Connection con = ConnectPostgresql.getConnection();
                PreparedStatement pst = con.prepareStatement("SELECT userID, userName, userPhoneNB, userPassword, userRole FROM \"User\" WHERE userName=? AND userPhoneNB=? AND userPassword=?");
                pst.setString(1, name);
                pst.setString(2, phone);
                pst.setString(3, pass);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    int role_user = rs.getInt("userRole");
                    if (role_user == 1) {
                        int userID = rs.getInt("userID");
                        HomeVendor homeVendor = new HomeVendor(userID);
                    } else {
                        HomeActivity homeActivity = new HomeActivity();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public static int getUserID() {
        String name = textName.getText();
        String pass = textPass.getText();
        int userID = 0;
        try {
            Connection con = ConnectPostgresql.getConnection();
            PreparedStatement pst = con.prepareStatement("SELECT userID FROM \"User\" WHERE userName=? AND userPassword=?");
            pst.setString(1, name);
            pst.setString(2, pass);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                userID = rs.getInt("userID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userID;
    }
}
