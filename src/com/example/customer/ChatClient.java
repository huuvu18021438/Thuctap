package com.example.customer;

import com.example.ConnectPostgresql;
import com.example.LoginActivity;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatClient implements Runnable {
    ChatClient thisCustomer;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    private TextArea tx = new TextArea();
    private Frame f = new Frame("Chat");
    private Label l = new Label("IP");
    private Label l1 = new Label("Port");
    private TextField t = new TextField();
    private TextField t1 = new TextField();
    private TextArea tx1 = new TextArea();
    private Button bt_connect = new Button("Connect");
    private Button bt_send = new Button("Send");
    private Button bt_disconnect = new Button("Disconnect");


    public ChatClient(int shopID) {
        initComponent(shopID);
        thisCustomer = this;
    }


    private void initComponent(int shopId) {
        l.setBounds(30,30,15,30);
        l1.setBounds(140,30,25,30);

        t.setBounds(50,30,80,30);
        t1.setBounds(170, 30,60,30);

        tx.setBounds(30, 70,340, 260 );
        tx1.setBounds(30,335,290,50);

        bt_connect.setBounds(235,30,60,30);
        bt_connect.addActionListener(e -> {
            try {
                String ip = t.getText();
                int port = Integer.parseInt(t1.getText());
                clientSocket = new Socket(ip, port);
                Thread thread = new Thread(thisCustomer);
                thread.start();
                token("Successfully connected");
            } catch (Exception e1) {
                token("Error connected");
            }
        });

        bt_send.setBounds(325,335,45,50);

        int userID = LoginActivity.getUserID();
        bt_send.addActionListener(event -> {
            if (tx1.getText().isEmpty()) return;
            try {
                Connection con = ConnectPostgresql.getConnection();
                PreparedStatement pst = con.prepareStatement("SELECT userName FROM \"User\" WHERE userID = '"+userID+"'");
                ResultSet rs = pst.executeQuery();
                rs.next();
                String customerName = rs.getString("userName");

                Date date = new Date();
                DateFormat df = new SimpleDateFormat("MMMMMMM d");
                DateFormat df1 = new SimpleDateFormat("H:mm");
                String dateString = df.format(date);
                String timeString = df1.format(date);

                if (tx.getText().isEmpty()) {
                    out.write(dateString + "\n");
                    tx.append(dateString + "\n");
                }

                out.write(customerName + ": " + tx1.getText() + "  (" + timeString + ")" + "\n");
                out.flush();
                tx.append(customerName + ": " + tx1.getText() + "  (" + timeString + ")" + "\n");
                tx1.setText("");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        bt_disconnect.setBounds(300,30,70,30);
        bt_disconnect.addActionListener(e -> {
            if (!tx.getText().isEmpty()) {
                try {
                    String body_message = tx.getText();
                    Connection con = ConnectPostgresql.getConnection();
                    PreparedStatement pst1 = con.prepareStatement("INSERT INTO Messages (body_ms, user_id, shop_id) " +
                            "VALUES ('"+body_message+"', '"+userID+"', '"+shopId+"')");
                    pst1.executeUpdate();
                } catch (SQLException sql) {
                    sql.printStackTrace();
                }
            }
            f.setVisible(false);
            stop();
        });

        f.add(l); f.add(l1); f.add(t); f.add(t1); f.add(bt_disconnect);
        f.add(bt_connect); f.add(tx1); f.add(bt_send); f.add(tx);
        f.setLayout(null);
        f.setSize(400,400);
        f.setVisible(true);
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (clientSocket != null) {
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    out = new PrintWriter(clientSocket.getOutputStream());
                    String msg = "";
                    while ((msg = in.readLine()) != null) {
                        tx.append(msg + "\n");
                    }
                }
            }
        } catch (IOException io) {}
    }

    public void stop() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void token(String message) {
        Button button = new Button("OK");
        Dialog d = new Dialog(new Frame(), "Connected");
        d.add(new Label(message));
        d.add(button);
        d.setLayout(new FlowLayout());
        d.setSize(180, 100);
        d.setLocation(100, 160);
        d.setVisible(true);
        button.addActionListener((event) -> {
            d.setVisible(false);
        });
    }
}
