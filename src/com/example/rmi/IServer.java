package com.example.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServer extends Remote {
    String[] connectClient(IClient client) throws RemoteException;
    String[] list() throws RemoteException;
    void sendMessage(String from, String to, String msg) throws RemoteException;
}
