package com.example.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClient extends Remote {
    String getName() throws RemoteException;
    void joined(String name) throws RemoteException;
    void showMessage(String from, String msg) throws RemoteException;
}
