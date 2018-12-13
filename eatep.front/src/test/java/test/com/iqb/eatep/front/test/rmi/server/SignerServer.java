package test.com.iqb.eatep.front.test.rmi.server;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class SignerServer {
    
    public static void main(String[] args) {
        try {
            ISignerFactor isf = new SignerFactorImpl();
            
            LocateRegistry.createRegistry(8888);
            
            System.out.println("开始发布服务。");
            Naming.bind("rmi://localhost:8888/RHello", isf);
            System.out.println("发布服务完成。");
        } catch (RemoteException | MalformedURLException | AlreadyBoundException e) {
            e.printStackTrace();
        }
        
    }

}
