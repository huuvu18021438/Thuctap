package com.example.customer;

import com.example.ConnectPostgresql;
import com.example.customer.ItemsActivity;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class HomeActivity {
    public HomeActivity() {
        Frame f = new Frame("Home");

        Button b = new Button("Search");
        b.setBounds(30,30,60,30);
        TextField t = new TextField();
        t.setBounds(110, 30, 260, 30);

        Panel panel = new Panel();
        panel.setBounds(30, 70,340,300);
        panel.setBackground(Color.LIGHT_GRAY);
        f.add(panel);

        b.addActionListener(event -> {
            String textSearch = t.getText();
            panel.removeAll();
            try {
                ArrayList<String> shopName = new ArrayList<>();
                Connection connection = ConnectPostgresql.getConnection();
                PreparedStatement st = connection.prepareStatement("SELECT shopID FROM products WHERE productName LIKE '%"+textSearch+"%'",
                        ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet resultSet = st.executeQuery();
                while (resultSet.next()) {
                    int shopID = resultSet.getInt("shopID");
                    Connection con = ConnectPostgresql.getConnection();
                    PreparedStatement pst = con.prepareStatement("SELECT * FROM shop WHERE shopID='"+shopID+"'",
                            ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    ResultSet rs = pst.executeQuery();
                    rs.next();
                    shopName.add(rs.getString("shopName"));
                }
                if (shopName.size() > 0) {
                    int i = 0;
                    switch (shopName.size()) {
                        case 1: Button button = new Button();
                            button.setBounds(15, 15, 90, 90);
                            button.setLabel(shopName.get(i));
                            panel.add(button);
                            String nameShop = shopName.get(i);
                            button.addActionListener(e -> {
                                onClickShop(nameShop);
                            });
                            break;

                        case 2: for (int x = 15; x < 135; x+=110) {
                            Button button1 = new Button();
                            button1.setBounds(x, 15, 90, 90);
                            button1.setLabel(shopName.get(i));
                            String nameShop1 = shopName.get(i);
                            button1.addActionListener(e -> {
                                onClickShop(nameShop1);
                            });
                            i++;
                            panel.add(button1);
                        }
                            break;

                        case 3:
                            for (int x = 15; x < 245; x+=110) {
                                Button button2 = new Button();
                                button2.setBounds(x, 15, 80, 80);
                                button2.setLabel(shopName.get(i));
                                String nameShop2 = shopName.get(i);
                                button2.addActionListener(e -> {
                                    onClickShop(nameShop2);
                                });
                                i++;
                                panel.add(button2);
                            }
                            break;

                        case 4:
                            for (int x = 15; x < 245; x+=110) {
                                Button button3 = new Button();
                                button3.setBounds(x, 15, 80, 80);
                                button3.setLabel(shopName.get(i));
                                String nameShop3 = shopName.get(i);
                                button3.addActionListener(e -> {
                                    onClickShop(nameShop3);
                                });
                                i++;
                                panel.add(button3);
                            }
                            Button button3_3 = new Button();
                            button3_3.setBounds(15, 115, 80,80);
                            button3_3.setLabel(shopName.get(i));
                            String nameShop3_3 = shopName.get(i);
                            button3_3.addActionListener(e -> {
                                onClickShop(nameShop3_3);
                            });
                            break;

                        case 5: for (int x = 15; x < 245; x+=110) {
                            Button button4 = new Button();
                            button4.setBounds(x, 15, 80, 80);
                            button4.setLabel(shopName.get(i));
                            String nameShop4 = shopName.get(i);
                            button4.addActionListener(e -> {
                                onClickShop(nameShop4);
                            });
                            i++;
                            panel.add(button4);
                        }
                            for (int x = 15; x < 135; x+=110) {
                                Button button4_4 = new Button();
                                button4_4.setBounds(x, 115,80,80);
                                button4_4.setLabel(shopName.get(i));
                                String nameShop4_4 = shopName.get(i);
                                button4_4.addActionListener(e -> {
                                    onClickShop(nameShop4_4);
                                });
                                i++;
                                panel.add(button4_4);
                            }
                            break;

                        case 6: for (int x = 15; x < 245; x+=110) {
                            for (int y = 15; y < 120; y+=100) {
                                Button button5= new Button();
                                button5.setBounds(x, y, 80, 80);
                                button5.setLabel(shopName.get(i));
                                String nameShop5 = shopName.get(i);
                                button5.addActionListener(e -> {
                                    onClickShop(nameShop5);
                                });
                                i++;
                                panel.add(button5);
                            }
                        }
                            break;

                        case 7: for (int x = 15; x < 245; x+=110) {
                            for (int y = 15; y < 220; y+=100) {
                                Button button6= new Button();
                                button6.setBounds(x, y, 80, 80);
                                button6.setLabel(shopName.get(i));
                                String nameShop6 = shopName.get(i);
                                button6.addActionListener(e -> {
                                    onClickShop(nameShop6);
                                });
                                i++;
                                panel.add(button6);
                            }
                        }
                            Button button6_6 = new Button();
                            button6_6.setBounds(15, 215,80,80);
                            button6_6.setLabel(shopName.get(i));
                            String nameShop6_6 = shopName.get(i);
                            button6_6.addActionListener(e -> {
                                onClickShop(nameShop6_6);
                            });
                            panel.add(button6_6);
                            break;

                        case 8: for (int x = 50; x < 245; x+=110) {
                            for (int y = 15; y < 220; y+=100) {
                                Button button7= new Button();
                                button7.setBounds(x, y, 80, 80);
                                button7.setLabel(shopName.get(i));
                                String nameShop7 = shopName.get(i);
                                button7.addActionListener(e -> {
                                    onClickShop(nameShop7);
                                });
                                i++;
                                panel.add(button7);
                            }
                        }
                            for (int x = 15; x < 135; x+=110) {
                                Button button7_7 = new Button();
                                button7_7.setBounds(x,215,80,80);
                                button7_7.setLabel(shopName.get(i));
                                String nameShop7_7 = shopName.get(i);
                                button7_7.addActionListener(e -> {
                                    onClickShop(nameShop7_7);
                                });
                                i++;
                                panel.add(button7_7);
                            }
                            break;

                        default: for (int x = 15; x < 245; x+=110) {
                            for (int y = 15; y < 220; y+=100) {
                                Button button8= new Button();
                                button8.setBounds(x, y, 80, 80);
                                button8.setLabel(shopName.get(i));
                                String nameShop8 = shopName.get(i);
                                button8.addActionListener(e -> {
                                    onClickShop(nameShop8);
                                });
                                i++;
                                panel.add(button8);
                            }
                        }
                    }
                } else {
                    Button button10 = new Button("OK");
                    Dialog d = new Dialog(new Frame(), "Warning");
                    d.add(new Label("Couldn't find any stores that sell this product!"));
                    d.add(button10);
                    d.setLayout(new FlowLayout());
                    d.setSize(300, 100);
                    d.setLocation(50, 160);
                    d.setVisible(true);
                    button10.addActionListener((e) -> {
                        d.setVisible(false);
                    });
                }
                f.add(panel);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        f.add(b); f.add(t);
        f.setLayout(null);
        f.setSize(400, 400);
        f.setVisible(true);
    }

    private void onClickShop(String nameShop) {
        try {
            Connection connection1 = ConnectPostgresql.getConnection();
            PreparedStatement pst1 = connection1.prepareStatement("SELECT shopID FROM shop WHERE shopName='"+nameShop+"'",
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet1 = pst1.executeQuery();
            if (resultSet1.next()) {
                int shop_ID = resultSet1.getInt("shopID");
                ItemsActivity itemsActivity = new ItemsActivity(shop_ID);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
