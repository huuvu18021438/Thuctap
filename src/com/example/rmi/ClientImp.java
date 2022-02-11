package com.example.rmi;

import java.awt.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientImp extends UnicastRemoteObject implements IClient {
    private String name;
    private List clientList;
    private TextArea chatArea;

    protected ClientImp(String name, List l, TextArea ta) throws RemoteException {
        this.name = name;
        this.clientList = l;
        this.chatArea = ta;
    }

    @Override
    public String getName() throws RemoteException {
        return this.name;
    }

    @Override
    public void joined(String name) throws RemoteException {
        this.clientList.add(name);
    }

    @Override
    public void showMessage(String from, String msg) throws RemoteException {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("MMMMMMM d");
        String dateString = df.format(date);
        if (chatArea.getText().isEmpty()) {
            chatArea.append(dateString + "\n");
        }
        this.chatArea.append(from + ":" + msg + "\n");
    }
}
