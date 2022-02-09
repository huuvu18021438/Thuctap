package com.example.vendor;

import com.example.ConnectPostgresql;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HistoryChat {
    public HistoryChat(int shopID, int userID) {
        Frame f = new Frame("store");

        Label l = new Label("History chat");
        l.setBounds(170,30,70,30);

        TextArea tx = new TextArea();
        tx.setBounds(30,70,340,300);

        try {
            Connection con = ConnectPostgresql.getConnection();
            PreparedStatement pst = con.prepareStatement("SELECT body_ms FROM Messages WHERE shop_id = '"+shopID+"' AND user_id = '"+userID+"'");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                tx.append(rs.getString("body_ms"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Button back = new Button("Back");
        back.setBounds(30,30,40, 30);
        back.addActionListener(event -> {
            f.setVisible(false);
        });

        f.add(tx); f.add(l); f.add(back);
        f.setSize(400,400);
        f.setBackground(Color.YELLOW);
        f.setLayout(null);
        f.setVisible(true);
    }
}
