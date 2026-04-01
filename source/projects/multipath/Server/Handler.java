package projects.multipath.Server;

import projects.multipath.ILP.MultiagentGraphSolverGurobiTime;
//generated protobuf classes
import projects.multipath.protos.Problem;
import projects.multipath.protos.Assignment;
import projects.multipath.protos.Node;
import java.net.Socket;

public class Handler implements Runnable{
    private final Socket s;
    Handler(Socket socket) {
        this.s = socket;
    }

    public void run(){
        //Take request and service it.
        System.out.println("Hello from runner thread!");
        try{
            this.s.close();
        }
        catch(Exception e){
            System.err.println("Error when closing a thread.");
        }
    }
}

