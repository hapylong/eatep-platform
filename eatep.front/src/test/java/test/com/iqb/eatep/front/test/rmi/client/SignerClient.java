package test.com.iqb.eatep.front.test.rmi.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;


public class SignerClient {
    
    public static void main(String[] args) {
        try {
            ISignerFactor signerFactor = (ISignerFactor) Naming.lookup("rmi://localhost:8888/RHello");
            System.out.println(signerFactor.getSignerName("线上签署人"));
        } catch (MalformedURLException | RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }

}
