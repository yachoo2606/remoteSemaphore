import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class RemoteSemaphoreImpl extends UnicastRemoteObject implements RemoteSemaphore {

    private final Semaphore semaphore;
    int counter=0;

    public RemoteSemaphoreImpl(int startingValue) throws RemoteException{
        super();
        semaphore = new Semaphore(startingValue);
    }

    @Override
    public void increment(int value) throws RemoteException {
        try{
            Boolean acquired = semaphore.tryAcquire(5000, TimeUnit.MILLISECONDS);
            if(!acquired) {
                throw new RemoteException("Timeout occurred while acquiring semaphore");
            }
            System.out.println("Increasing value by: "+ value + "; current value: "+ counter+" post operation value:"+(counter+value));
            counter += value;

        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
            throw new RemoteException("Interrupted while acquiring semaphore.", e);
        }finally {
            semaphore.release();
        }
    }

    @Override
    public void decrement(int value) throws RemoteException {
        try{
            Boolean acquired = semaphore.tryAcquire(5000, TimeUnit.MILLISECONDS);
            if(!acquired) {
                throw new RemoteException("Timeout occurred while acquiring semaphore");
            }
            System.out.println("Decreasing value by: "+ value + "; current value: "+ counter+" post operation value:"+(counter-value));
            counter-=value;
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
            throw new RemoteException("Interrupted while acquiring semaphore.", e);
        }finally {
            semaphore.release();
        }
    }

    @Override
    public int actualValue() throws RemoteException {
        return counter;
    }
}
