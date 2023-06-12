import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteSemaphore extends Remote {
    void increment(int value) throws RemoteException;
    void decrement(int value) throws RemoteException;
    int actualValue() throws RemoteException;
}
