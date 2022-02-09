package com.example.customer;

import com.example.ConnectPostgresql;
import com.example.LoginActivity;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemsActivity {
    public ItemsActivity(int shopID) {
        Frame f = new Frame("List items");

        Label label = new Label("Choose product:");
        label.setBounds(25,95,80,30);
        Choice c = new Choice();
        c.setBounds(120, 100, 200, 300);

        Button back = new Button("Back");
        back.setBounds(30,30,40, 30);

        Button chat = new Button("Contact store");
        chat.setBounds(300,30,80, 30);
        chat.addActionListener(e -> {
            EventQueue.invokeLater(() -> {
                try {
                    ChatClient frame = new ChatClient(shopID);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        });

        Button b = new Button("Buy");
        b.setBounds(190, 300, 60, 30);
        b.setBackground(Color.yellow);

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

        b.addActionListener(e -> {
            int customerID = LoginActivity.getUserID();
            try {
                Connection connection = ConnectPostgresql.getConnection();
                PreparedStatement pt= connection.prepareStatement("INSERT INTO Orders (customerID, shopID) VALUES('"+customerID+"', '"+shopID+"')");
                pt.executeUpdate();

                Button button = new Button("OK");
                Dialog d = new Dialog(new Frame(), "Warning");
                d.add(new Label("Successfully purchased!"));
                d.add(button);
                d.setLayout(new FlowLayout());
                d.setSize(150, 100);
                d.setLocation(100, 160);
                d.setVisible(true);
                button.addActionListener((event) -> {
                    d.setVisible(false);
                });
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        });

        back.addActionListener(event -> {
            f.setVisible(false);
        });

        f.add(back); f.add(label); f.add(c); f.add(b); f.add(chat);
        f.setSize(400, 400);
        f.setLayout(null);
        f.setVisible(true);
    }
}
