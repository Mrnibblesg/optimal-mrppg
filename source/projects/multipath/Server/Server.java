package projects.multipath.Server;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.net.ServerSocket;

// This server should be started by the parameter sweep script! It runs throughout the duration of the sweep if the parameter sweep contains any centralized strategy combinations.
// The server is turned off once the parameter sweep finishes.
// The server also takes as a parameter the number of threads in its pool which defaults to 1 if none is provided.
// Running on a more powerful server? Increase the number of workers for the thread pool.
public class Server{
    public static void main(String[] args){
        int workerThreads = Integer.parseInt(args[0]);
        int port = 55555;

        ExecutorService pool = Executors.newFixedThreadPool(workerThreads);

        try(ServerSocket server = new ServerSocket(port)){
            String addr = server.getInetAddress().toString();
            System.out.println("Optimal pathfinding server started at " + addr + " on port " + port);
            while(true){
                pool.execute(new Handler(server.accept()));
            }
        }
        catch(Exception e){
            System.err.println("Error encountered. Optimal pathfinding server shutting down.");
            pool.shutdown();
        }


    }

}

