import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RemoteSemaphoreImpl extends UnicastRemoteObject implements RemoteSemaphore {

    private Semaphore semaphore;
    private Lock lock;
    private Condition condition;

    public RemoteSemaphoreImpl(int startingValue) throws RemoteException{
        super();
        semaphore = new Semaphore(startingValue);
        lock = new ReentrantLock();
        condition = lock.newCondition();
    }

    @Override
    public void increment(int value) throws RemoteException {
        lock.lock();
        System.out.println("Increasing value by: " + value + " | Current value: " + (semaphore.availablePermits()+value));
        try{
            semaphore.release(value);
            condition.signal();
        }finally {
            lock.unlock();
        }
    }

    @Override
    public void decrement(int value) throws RemoteException {
        try{
            Boolean acquired = semaphore.tryAcquire(value,5000, TimeUnit.MILLISECONDS);
            if(!acquired) {
                throw new RemoteException("Timeout occurred while acquiring semaphore");
            }
            System.out.println("Decreasing value by: "+ value + "; current value: "+ semaphore.availablePermits());
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
            throw new RemoteException("Interrupted while acquiring semaphore.", e);
        }
    }

    @Override
    public int actualValue() throws RemoteException {
        return semaphore.availablePermits();
    }
}
