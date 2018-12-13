package test.com.iqb.eatep.front.test.rmi.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ISignerFactor extends Remote {

    public String getSignerName(String name) throws RemoteException;
    
}
