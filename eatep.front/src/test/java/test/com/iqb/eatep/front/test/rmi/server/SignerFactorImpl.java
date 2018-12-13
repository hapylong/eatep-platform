package test.com.iqb.eatep.front.test.rmi.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class SignerFactorImpl extends UnicastRemoteObject implements ISignerFactor {

    private static final long serialVersionUID = 476154274282694187L;

    protected SignerFactorImpl() throws RemoteException {
        super();
    }

    @Override
    public String getSignerName(String name) throws RemoteException {
        return "签署方姓名：" + name;
    }

}
