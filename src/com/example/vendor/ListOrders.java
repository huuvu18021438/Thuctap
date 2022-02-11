package com.example.vendor;

import com.example.ConnectPostgresql;
import com.example.rmi.ChatFrame;
import com.example.rmi.IServer;

import java.awt.*;
import java.rmi.Naming;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ListOrders {
    public ListOrders(int shopID){
        Frame f = new Frame("Store");

        Label l = new Label("List orders");
        l.setBounds(165,30,80,30);

        Button back = new Button("Back");
        back.setBounds(30,30,40, 30);
        back.addActionListener(event -> {
            f.setVisible(false);
        });

        Button chat = new Button("Contact customer");

        // RMI
        /*chat.setBounds(290,30,95, 30);
        chat.addActionListener(e -> {
            EventQueue.invokeLater(() -> {
                try {
                    IServer server = (IServer) Naming.lookup("rmi://localhost"+"/"+"chat");
                    ChatFrame frame = new ChatFrame(server, "Shop");
                    frame.setVisible(true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        });*/

        // socket
        chat.setBounds(290,30,95, 30);
        chat.addActionListener(e -> {
            EventQueue.invokeLater(() -> {
                try {
                    ChatServer frame = new ChatServer(shopID);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        });

        Panel panel = new Panel();
        panel.setBounds(30, 70,340,300);
        panel.setBackground(Color.LIGHT_GRAY);

        try {
            ArrayList<String> list_orders = new ArrayList<>();
            Connection con = ConnectPostgresql.getConnection();
            PreparedStatement pst = con.prepareStatement("SELECT OrdersOfShop.orderID, \"User\".userName FROM " +
                            "(SELECT orderID, customerID FROM Orders  WHERE shopID='"+shopID+"') AS OrdersOfShop INNER JOIN \"User\"" +
                            "ON OrdersOfShop.customerID = \"User\".userID ORDER BY orderID DESC;",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list_orders.add("Code: #" + rs.getInt("orderID") + "-" + "customer: " + rs.getString("userName"));
            }
            int i = 0;
            switch (list_orders.size()) {
                case 1:
                    Button b1 = new Button();
                    b1.setBounds(80,85,200,40);
                    b1.setLabel(list_orders.get(i));
                    b1.setBackground(Color.CYAN);
                    f.add(b1);
                    break;
                case 2:
                    for (int y = 85; y < 150; y+=55) {
                        Button b2 = new Button();
                        b2.setBounds(80, y, 200, 40);
                        b2.setLabel(list_orders.get(i));
                        b2.setBackground(Color.CYAN);
                        i++;
                        f.add(b2);
                    }
                    break;
                case 3:
                    for (int y = 85; y < 200; y+=55) {
                        Button b3 = new Button();
                        b3.setBounds(80,y, 200, 40);
                        b3.setLabel(list_orders.get(i));
                        b3.setBackground(Color.CYAN);
                        i++;
                        f.add(b3);
                    }
                    break;
                case 4:
                    for (int y = 85; y < 290; y+=55) {
                        Button b4 = new Button();
                        b4.setBounds(80, y, 200, 40);
                        b4.setLabel(list_orders.get(i));
                        b4.setBackground(Color.CYAN);
                        i++;
                        f.add(b4);
                    }
                    break;
                default:
                    for (int y = 85; y < 370; y+=55) {
                        Button b5 = new Button();
                        b5.setBounds(80, y, 200, 40);
                        b5.setLabel(list_orders.get(i));
                        b5.setBackground(Color.CYAN);
                        i++;
                        f.add(b5);
                    }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        f.add(back); f.add(l); f.add(panel); f.add(chat);
        f.setSize(400,400);
        f.setBackground(Color.yellow);
        f.setLayout(null);
        f.setVisible(true);
    }
}
