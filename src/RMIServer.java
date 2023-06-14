import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServer {
    public static void main(String[] args){
        try{
            RemoteSemaphore semaphore = new RemoteSemaphoreImpl(5);
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("RemoteSemaphore", semaphore);
            System.out.println("RemoteSemaphore is waits for connect...");
        }catch (RemoteException e){
            System.err.println("RemoteSemaphore server exception: " + e.getMessage());
            e.printStackTrace();
        }catch (Exception e){
            System.err.println("Server error: "+e.getMessage());
        }
    }
}
