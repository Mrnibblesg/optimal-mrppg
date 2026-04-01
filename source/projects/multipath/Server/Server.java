package projects.multipath.Server;

import projects.multipath.ILP.MultiagentGraphSolverGurobiTime;

// import generated protobuf classes
import projects.multipath.protos.Problem;
import projects.multipath.protos.Assignment;
import projects.multipath.protos.Node;

// Start server with a thread pool executor.
// accept() connections and submit each to the pool.
// Each worker handles one problem at a time.
// Response goes back on the same connection the worker holds.


// This server should be started by the parameter sweep script! It runs throughout the duration of the sweep if the parameter sweep contains any centralized strategy combinations.
// The server is turned off once the parameter sweep finishes.
// The server also takes as a parameter the number of threads in its pool which defaults to 1 if none is provided.
// Running on a more powerful server? Increase the number of workers for the thread pool.
public class Server{
    public static void main(String[] args){
        System.out.println("Supplied arguments:");
        for (int i = 0; i < args.length; i++){
            System.out.println(i + ": " + args[i]);
        }

        //Read from protobuf queue and start jobs
        System.out.println("Hello World!");
    }
}
