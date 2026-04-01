package projects.multipath.Server;

import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;

import projects.multipath.ILP.MultiagentGraphSolverGurobiTime;
//generated protobuf classes
import projects.multipath.protos.Problem;
import projects.multipath.protos.Assignment;
import projects.multipath.protos.Node;

public class Handler implements Runnable{
    private final Socket s;
    Handler(Socket socket) {
        this.s = socket;
    }

    public void run(){
        //Take request and service it.
        System.out.println("Hello from runner thread!");
        try{
            InputStream istream = this.s.getInputStream();
            Assignment assignment = Assignment.parseFrom(istream);

            System.out.println("Java: " + assignment.getRobotId());
            System.out.println("Java: " + assignment.getStartId());
            System.out.println("Java: " + assignment.getFinishId());

            Assignment testResponse = Assignment.newBuilder()
                .setRobotId(30)
                .setStartId(40)
                .setFinishId(50)
                .build();

            OutputStream ostream = this.s.getOutputStream();
            testResponse.writeTo(ostream);

            this.s.close();
        }
        catch(Exception e){
            System.err.println("Error encountered in using thread.");
        }
    }
}

