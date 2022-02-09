package com.example.vendor;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatServer implements Runnable {
    ChatServer thisShop;
    private ServerSocket serverSocket;
    private PrintWriter out;
    private BufferedReader in;

    private Frame f = new Frame("Store");
    private Label l = new Label("Port:");
    private TextField t = new TextField();
    private Button bt_send = new Button("Send");
    private Button bt_start = new Button("Start server");
    private Button bt_list_chat = new Button("Chat list");
    private Button back = new Button("Back");
    private TextArea tx = new TextArea();
    private TextArea tx1 = new TextArea();

    public ChatServer(int shopId) {
        initComponents(shopId);
        this.thisShop = this;
    }

    private void initComponents(int shopId) {
        l.setBounds(80, 30,30,30);

        t.setBounds(110,30,60,30);

        bt_send.setBounds(325,335,45,50);

        bt_start.setBounds(175, 30, 70,30);
        bt_start.addActionListener(e -> {
            try {
                int port = Integer.parseInt(t.getText());
                serverSocket = new ServerSocket(port);
                Thread t = new Thread(thisShop);
                t.start();
                token("Server is running");
            } catch (Exception ex) {
                token("Start server error");
            }
        });

        bt_list_chat.setBounds(250,30,70,30);
        bt_list_chat.addActionListener(e -> {
            ListChat listChat = new ListChat(shopId);
        });

        tx.setBounds(30, 70,340, 260 );
        tx1.setBounds(30,335,290,50);

        bt_send.addActionListener(event -> {
            if (tx1.getText().isEmpty()) return;
            try {
                Date date = new Date();
                DateFormat df = new SimpleDateFormat("MMMMMMM d");
                DateFormat df1 = new SimpleDateFormat("H:mm");
                String dateString = df.format(date);
                String timeString = df1.format(date);

                if (tx.getText().isEmpty()) {
                    out.write(dateString + "\n");
                    tx.append(dateString + "\n");
                }

                out.write("Shop: " + tx1.getText() + "  (" + timeString + ")" + "\n");
                out.flush();
                tx.append("Shop: " + tx1.getText() + "  (" + timeString + ")" + "\n");
                tx1.setText("");

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        back.setBounds(30,30,40, 30);
        back.addActionListener(event -> {
            f.setVisible(false);
        });

        f.add(l); f.add(t); f.add(bt_start); f.add(back);
        f.add(tx); f.add(tx1); f.add(bt_send); f.add(bt_list_chat);
        f.setLayout(null);
        f.setSize(400,400);
        f.setBackground(Color.YELLOW);
        f.setVisible(true);
    }


    @Override
    public void run() {
        try {
            while (true) {
                Socket customerSocket = serverSocket.accept();
                if (customerSocket != null) {
                    in = new BufferedReader(new InputStreamReader(customerSocket.getInputStream()));
                    out = new PrintWriter(customerSocket.getOutputStream());
                    String msg = "";
                    while ((msg = in.readLine()) != null) {
                        tx.append(msg + "\n");
                    }
                }
            }
        } catch (IOException io) {}
    }

    private void token(String message) {
        Button button = new Button("OK");
        Dialog d = new Dialog(new Frame(), "Started sever");
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
