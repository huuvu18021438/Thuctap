package com.example.vendor;

import com.example.ConnectPostgresql;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HomeVendor {
    public HomeVendor(int userID) {
        Frame f = new Frame("Store");

        String name_shop = "";
        int shopID = 0;
        try {
            Connection connection = ConnectPostgresql.getConnection();
            PreparedStatement pst = connection.prepareStatement("SELECT shopID, shopName FROM shop WHERE userID='"+userID+"'");
            ResultSet resultSet = pst.executeQuery();
            if(resultSet.next()) {
                name_shop = resultSet.getString("shopName");
                shopID = resultSet.getInt("shopID");
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        Label l = new Label(name_shop + " Store");
        l.setBounds(160,30,100,30);

        Label l3 = new Label("Add item");
        l3.setBounds(165,230,60,30);

        Label l2 = new Label("List items");
        l2.setBounds(30,70,60,30);
        Choice c = new Choice();
        c.setBounds(100, 80,200,300);

        Label l4 = new Label("Name");
        l4.setBounds(95,265, 35, 30);
        Label l5 = new Label("Price");
        l5.setBounds(95,305, 30, 30);
        TextField t1 = new TextField();
        t1.setBounds(130,270,120,30);
        TextField t2 = new TextField();
        t2.setBounds(130,310,120,30);

        Button add = new Button("Add");
        add.setBounds(220, 350, 60, 30);
        add.setBackground(Color.blue);

        Button order = new Button("List orders");
        order.setBounds(30,350,80,30);
        order.setBackground(Color.blue);

        int finalShopID1 = shopID;
        order.addActionListener(event -> {
            ListOrders listOrders = new ListOrders(finalShopID1);
        });

        try {
            Connection con = ConnectPostgresql.getConnection();
            PreparedStatement pst = con.prepareStatement("SELECT productName, productPrice FROM products WHERE shopID='"+shopID+"'",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                c.add("Product: " + rs.getString("productName") + ", Price: "  + rs.getFloat("productPrice"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int finalShopID = shopID;
        add.addActionListener(e -> {
            String product_name = t1.getText();
            try {
                Float product_Price = Float.parseFloat(t2.getText());
                if (product_name.length() == 0) {
                    token("Add failed!");
                } else {
                    try {
                        Connection con = ConnectPostgresql.getConnection();
                        PreparedStatement pst = con.prepareStatement("INSERT INTO products (productName, productPrice, shopID) VALUES (?, ?, ?)");
                        pst.setString(1, product_name);
                        pst.setFloat(2, product_Price);
                        pst.setInt(3, finalShopID);
                        pst.executeUpdate();

                        Button button = new Button("OK");
                        Dialog d = new Dialog(new Frame(), "Warning");
                        d.add(new Label("Successfully added!"));
                        d.add(button);
                        d.setLayout(new FlowLayout());
                        d.setSize(150, 100);
                        d.setLocation(100, 160);
                        d.setVisible(true);
                        button.addActionListener((event) -> {
                            d.setVisible(false);
                            f.setVisible(false);
                            new HomeVendor(userID);
                        });
                    } catch (SQLException sqlException) {
                        sqlException.printStackTrace();
                    }
                }
            } catch (Exception exception) {
                token("Add failed!");
            }
        });

        f.add(l); f.add(l2); f.add(l3);
        f.add(l4); f.add(l5); f.add(t1); f.add(t2);
        f.add(c); f.add(add); f.add(order);
        f.setSize(400, 400);
        f.setBackground(Color.yellow);
        f.setLayout(null);
        f.setVisible(true);
    }

    private void token(String message) {
        Button button = new Button("OK");
        Dialog d = new Dialog(new Frame(), "Warning");
        d.add(new Label(message));
        d.add(button);
        d.setLayout(new FlowLayout());
        d.setSize(150, 100);
        d.setLocation(100, 160);
        d.setVisible(true);
        button.addActionListener((event) -> {
            d.setVisible(false);
        });
    }
}
