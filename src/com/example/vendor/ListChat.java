package com.example.vendor;

import com.example.ConnectPostgresql;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ListChat {
    public ListChat(int shopID) {
        Frame f = new Frame("Store");

        Label l = new Label("Chat list");
        l.setBounds(170,30,70,30);

        Panel panel = new Panel();
        panel.setBounds(30, 70,340,300);
        panel.setBackground(Color.WHITE);

        Button back = new Button("Back");
        back.setBounds(30,30,40, 30);
        back.addActionListener(event -> {
            f.setVisible(false);
        });

        try {
            ArrayList<String> list_chats = new ArrayList<>();
            ArrayList<Integer> list_user_id = new ArrayList<Integer>();
            Connection con = ConnectPostgresql.getConnection();
            PreparedStatement pst = con.prepareStatement("SELECT user_id FROM Messages WHERE shop_id = '"+shopID+"' " +
                            "GROUP BY user_id ORDER BY MAX(message_id) DESC",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list_user_id.add(rs.getInt("user_id"));
                PreparedStatement pst1 = con.prepareStatement("SELECT Max(date_sent) FROM Messages " +
                        "WHERE user_id = '"+rs.getInt("user_id")+"'");
                ResultSet rs1 = pst1.executeQuery();
                rs1.next();
                DateFormat df = new SimpleDateFormat("MMMMMMM d");
                String date = df.format(rs1.getTimestamp(1));

                PreparedStatement pst2 = con.prepareStatement("SELECT userName FROM \"User\" " +
                        "WHERE userID = '"+rs.getInt("user_id")+"'");
                ResultSet resultSet = pst2.executeQuery();
                resultSet.next();
                String row_chat = resultSet.getString("userName") + ", " + date;
                list_chats.add(row_chat);
            }

            int i = 0;
            switch (list_chats.size()) {
                case 1:
                    Button b1 = new Button();
                    b1.setBounds(90,85,200,40);
                    b1.setLabel(list_chats.get(i));
                    int user_id1 = list_user_id.get(i);
                    b1.addActionListener(e -> {
                        HistoryChat historyChat = new HistoryChat(shopID, user_id1);
                    });
                    f.add(b1);
                    break;
                case 2:
                    for (int y = 85; y < 150; y+=55) {
                        Button b2 = new Button();
                        b2.setBounds(90, y, 200, 40);
                        b2.setLabel(list_chats.get(i));
                        int user_id2 = list_user_id.get(i);
                        b2.addActionListener(e -> {
                            HistoryChat historyChat = new HistoryChat(shopID, user_id2);
                        });
                        i++;
                        f.add(b2);
                    }
                    break;
                case 3:
                    for (int y = 85; y < 200; y+=55) {
                        Button b3 = new Button();
                        b3.setBounds(90,y, 200, 40);
                        b3.setLabel(list_chats.get(i));
                        int user_id3 = list_user_id.get(i);
                        b3.addActionListener(e -> {
                            HistoryChat historyChat = new HistoryChat(shopID, user_id3);
                        });
                        i++;
                        f.add(b3);
                    }
                    break;
                case 4:
                    for (int y = 85; y < 290; y+=55) {
                        Button b4 = new Button();
                        b4.setBounds(90, y, 200, 40);
                        b4.setLabel(list_chats.get(i));
                        int user_id4 = list_user_id.get(i);
                        b4.addActionListener(e -> {
                            HistoryChat historyChat = new HistoryChat(shopID, user_id4);
                        });
                        i++;
                        f.add(b4);
                    }
                    break;
                default:
                    for (int y = 85; y < 370; y+=55) {
                        Button b5 = new Button();
                        b5.setBounds(90, y, 200, 40);
                        b5.setLabel(list_chats.get(i));
                        int user_id5 = list_user_id.get(i);
                        b5.addActionListener(e -> {
                            HistoryChat historyChat = new HistoryChat(shopID, user_id5);
                        });
                        i++;
                        f.add(b5);
                    }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        f.add(panel); f.add(l); f.add(back);
        f.setSize(400,400);
        f.setBackground(Color.YELLOW);
        f.setLayout(null);
        f.setVisible(true);
    }
}
