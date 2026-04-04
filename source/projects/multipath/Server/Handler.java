package projects.multipath.Server;

import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.ArrayList;

import projects.multipath.ILP.PuzzleSolver;
import projects.multipath.advanced.Graph;
import projects.multipath.advanced.Problem;

//generated protobuf classes
import projects.multipath.protos.Instance;
import projects.multipath.protos.Assignment;
import projects.multipath.protos.Node;

import projects.multipath.protos.Solution;
import projects.multipath.protos.Timestep;
import projects.multipath.protos.Position;

public class Handler implements Runnable{
    private final Socket s;
    Handler(Socket socket) {
        this.s = socket;
    }

    public void run(){
        //Take request and service it.
        System.out.println("Hello from runner thread!");
        try(this.s){
            InputStream istream = this.s.getInputStream();
            Instance problem_msg = Instance.parseFrom(istream);
            Problem prob = this.messageToProblem(problem_msg);

            int[][] paths = PuzzleSolver.solve(prob, -1);
            
            Solution solMsg = solutionToMessage(paths);
            OutputStream ostream = this.s.getOutputStream();
            solMsg.writeTo(ostream);

        }
        catch(Exception e){
            System.err.println("ERROR encountered in runner thread: ");
            e.printStackTrace();
        }
    }

    private Problem messageToProblem(Instance msg){
        Graph g = new Graph();
        for (Node n : msg.getNodesList()){
            Integer[] raw_neighbors = n.getNeighborsList().toArray(new Integer[0]);
            int[] neighbors = Arrays.stream(raw_neighbors).mapToInt(Integer::intValue).toArray();
            g.addVertex(n.getId(), neighbors);
        }
        int robots = msg.getAssignmentsCount();
        int[] starts = new int[robots];
        int[] goals = new int[robots];

        for (Assignment a : msg.getAssignmentsList()){
            int id = a.getRobotId();
            starts[id] = a.getStartId();
            goals[id] = a.getFinishId();
        }

        g.finishBuildingGraph();

        Problem prob = new Problem();
        prob.graph = g;
        prob.sg = new int[2][robots];
        prob.sg[0] = starts;
        prob.sg[1] = goals;

        return prob;
    }

    private Solution solutionToMessage(int[][] paths){
	//int paths[][] = new int[starts.length][timeSteps + 1];
        int robots = paths.length;
        int maxTime = paths[0].length;
        
        Solution.Builder sol = Solution.newBuilder()
            .setStatus(0);
        

        for (int t = 0; t < maxTime; t++){
            Timestep.Builder ts = Timestep.newBuilder()
                .setTime(t);
            for (int r = 0; r < robots; r++){
                ts.addPositions(
                    Position.newBuilder()
                        .setRobotId(r)
                        .setNodeId(paths[r][t])
                        .build());
            }
            sol.addTimesteps(ts.build());
        }

        return sol.build();
    }
}

