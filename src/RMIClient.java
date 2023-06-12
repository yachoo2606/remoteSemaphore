import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/*
*
* Usage: java RMIClient inc 3
*
* */

public class RMIClient {
    public static void main(String[] args) throws Exception{

        if(args.length < 2){
            System.err.println("Too few call arguments provided.");
            System.err.println("Example: java RMIClient inc 3");
            return;
        }

        try{
            Registry registry = LocateRegistry.getRegistry("localhost",1099);
            RemoteSemaphore semaphore = (RemoteSemaphore) registry.lookup("RemoteSemaphore");

            switch (args[0]) {
                case "inc" -> {
                    semaphore.increment(Integer.parseInt(args[1]));
                    System.out.println("Value of semaphore on the end of operations: " + semaphore.actualValue());
                }
                case "dec" -> {
                    semaphore.decrement(Integer.parseInt(args[1]));
                    System.out.println("Value of semaphore on the end of operations: " + semaphore.actualValue());
                }
                default -> {
                    System.out.println("Provided operation is not supported. Supported operations: [inc, dec]");
                }
            }

        }catch (Exception e){
            System.err.println("RMIClient exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
