package com.example.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class ServerImp extends UnicastRemoteObject implements IServer {
    private Map<String, IClient> clientMap = new HashMap<>();

    public ServerImp() throws RemoteException {
    }


    @Override
    public String[] connectClient(IClient client) throws RemoteException {
        String name = client.getName();
        String[] clientNames = list();
        clientMap.put(name, client);
        for (String clientName : clientNames) {
            clientMap.get(clientName).joined(name);
        }
        return clientNames;
    }

    @Override
    public String[] list() throws RemoteException {
        return clientMap.keySet().toArray(new String[clientMap.size()]);
    }

    @Override
    public void sendMessage(String from, String to, String msg) throws RemoteException {
        clientMap.get(to).showMessage(from, msg);
    }
}
