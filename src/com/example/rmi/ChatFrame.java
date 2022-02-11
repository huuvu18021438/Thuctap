package com.example.rmi;

import java.awt.*;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatFrame extends Frame {
    private IServer server;
    private IClient client;
    private String name;
    private List clientList = new List();
    private TextArea chatArea = new TextArea();
    private TextArea entryArea = new TextArea();
    private Button bt_send = new Button("Send");

    public ChatFrame(IServer server, String clientName) throws RemoteException {
        super(clientName);
        this.server = server;
        this.name = clientName;
        this.client = new ClientImp(clientName, clientList, chatArea);
        String[] clNames = server.connectClient(client);
        for (String clName : clNames ) {
            clientList.add(clName);
        }
        this.setSize(400, 400);
        this.initComponent();
        this.setupEvents();
    }

    private void initComponent() {
        this.chatArea.setBounds(30, 70,340, 260 );
        this.chatArea.setEditable(false);
        this.entryArea.setBounds(30,335,290,50);
        this.bt_send.setBounds(325,335,45,50);
        this.setLayout(null);
        this.add(chatArea);
        this.add(entryArea);
        this.add(bt_send);
    }

    private void setupEvents() {
        this.bt_send.addActionListener(event -> {
            String[] clientNames = new String[0];
            try {
                clientNames = server.list();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            for (String clientName : clientNames) {
                Date date = new Date();
                DateFormat df1 = new SimpleDateFormat("H:mm");
                String timeString = df1.format(date);

                try {
                    server.sendMessage(name, clientName, entryArea.getText() + "  (" + timeString + ")");
                } catch (RemoteException re) {
                    re.printStackTrace();
                }
            }
            entryArea.setText("");
        });
    }
}
