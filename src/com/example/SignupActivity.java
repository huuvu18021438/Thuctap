package com.example;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignupActivity {
    public static TextField textName = new TextField();
    public static TextField textPhoneNB = new TextField();
    public static TextField textPass = new TextField();

    public SignupActivity() {
        Frame f = new Frame("Signup");

        Label l1 = new Label("NAME");
        l1.setBounds(20, 70, 35, 30);
        Label l2 = new Label("PHONE");
        l2.setBounds(20, 130, 43, 30);
        Label l3 = new Label("PASS");
        l3.setBounds(20, 190, 35, 30);
        Label l4 = new Label("USERTYPE");
        l4.setBounds(20,240,68,30);

        CheckboxGroup cbg = new CheckboxGroup();
        Checkbox cb1 = new Checkbox("Customer", cbg, false);
        cb1.setBounds(90, 230, 80,50);
        Checkbox cb2 = new Checkbox("Vendor", cbg, false);
        cb2.setBounds(170, 230,50,50);

        Label shopName = new Label("SHOPNAME");
        shopName.setBounds(20,280,68,30);
        TextField nameShop = new TextField();
        nameShop.setBounds(90,280,180,30);
        cb1.addItemListener(e -> {
            f.remove(shopName);
            f.remove(nameShop);
        });
        cb2.addItemListener(e -> {
            f.add(shopName);
            f.add(nameShop);
        });


        Button b = new Button("Signup");
        b.setBounds(218, 320, 50, 30);

        Button back = new Button("Back");
        back.setBounds(20,30,40, 30);
        back.addActionListener(event -> {
            f.setVisible(false);
        });

        textName.setBounds(90, 70, 180, 30);
        textPhoneNB.setBounds(90, 130, 180, 30);
        textPass.setBounds(90, 190, 180, 30);

        f.add(l1);
        f.add(textName);
        f.add(l2);
        f.add(textPhoneNB);
        f.add(l3);
        f.add(textPass);
        f.add(b);
        f.add(back);
        f.add(l4);
        f.add(cb1);
        f.add(cb2);

        f.setLayout(null);
        f.setSize(300, 380);
        f.setVisible(true);

        b.addActionListener(event -> {
            String name = textName.getText();
            String phone = textPhoneNB.getText();
            String pass = textPass.getText();
            try {
                if (name.equals("") || phone.equals("") || pass.equals("")) {
                    String msg = "Signup failed, " +
                            "Please complete all information";
                    token(msg);
                } else {
                    if (cb1.getState()) {
                        Connection con = ConnectPostgresql.getConnection();
                        PreparedStatement pst = con.prepareStatement("INSERT INTO \"User\" (userName, userPhoneNB, userPassword, userRole) " +
                                "VALUES (?, ?, ?, 2) ");
                        pst.setString(1, name);
                        pst.setString(2, phone);
                        pst.setString(3, pass);
                        pst.executeUpdate();
                        token("Successfully signed up");
                        textName.setText("");
                        textPhoneNB.setText("");
                        textPass.setText("");
                    } else if (cb2.getState()) {
                        Connection con = ConnectPostgresql.getConnection();
                        PreparedStatement pst = con.prepareStatement("INSERT INTO \"User\" (userName, userPhoneNB, userPassword, userRole) " +
                                "VALUES (?, ?, ?, 1) ");
                        pst.setString(1, name);
                        pst.setString(2, phone);
                        pst.setString(3, pass);
                        pst.executeUpdate();

                        PreparedStatement pst1 = con.prepareStatement("SELECT userID FROM \"User\" WHERE userName=? AND userPhoneNB=? AND userPassword=?");
                        pst1.setString(1, name);
                        pst1.setString(2, phone);
                        pst1.setString(3, pass);
                        ResultSet rs = pst1.executeQuery();
                        rs.next();

                        String name_shop = nameShop.getText();
                        PreparedStatement pst2 = con.prepareStatement("INSERT INTO Shop(shopName, userID) VALUES (?, ?)");
                        pst2.setString(1, name_shop);
                        pst2.setInt(2, rs.getInt(1));
                        pst2.executeUpdate();
                        token("Successfully signed up");
                        textName.setText("");
                        textPhoneNB.setText("");
                        textPass.setText("");
                        nameShop.setText("");
                    } else {
                        String msg = "Signup failed, " +
                                "Please complete all information";
                        token(msg);
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void token(String message) {
        Button button = new Button("OK");
        Dialog d = new Dialog(new Frame(), "Warning");
        d.add(new Label(message));
        d.add(button);
        d.setLayout(new FlowLayout());
        d.setSize(260, 100);
        d.setLocation(20, 160);
        d.setVisible(true);
        button.addActionListener((event) -> {
            d.setVisible(false);
        });
    }
}
