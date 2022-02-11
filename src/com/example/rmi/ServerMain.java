package com.example.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerMain {
    public static void main(String[] args) throws RemoteException {
        Registry r = LocateRegistry.createRegistry(1099);
        IServer server = new ServerImp();
        r.rebind("chat", server);
    }
}
