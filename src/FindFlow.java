import java.util.*;
import java.io.*;

public class FindFlow {

    public static void main (String [] args) throws FileNotFoundException {
	Scanner sc = new Scanner(new File(args[0]));
	// get the number of nodes in graph (first line of file)
	int n = Integer.parseInt(sc.nextLine());

	// construct new empty graph
        Graph graph = new Graph();

	// add nodes to this graph
	String source = sc.nextLine();
	graph.addNode(source);
	for (int i = 1; i < n-1; i++) {
	    String node = sc.nextLine();
	    graph.addNode(node);
	}
	String dest = sc.nextLine();
	graph.addNode(dest);
	
	// add edges to this graph, each edge has source, dest, capacity
	// and starts off with a flow of 0
	while (sc.hasNextLine()) {
	    String [] tokens = sc.nextLine().split(" ");
      	    String s = tokens[0]; //get edge source
      	    String d = tokens[1]; // get edge dest
	    int capacity = Integer.parseInt(tokens[2]);
	    graph.addEdge(s, new Edge(s, d, capacity,0));
	}
	// right now the graph is G_0

	ArrayList <Edge> path = new ArrayList<Edge>();
        do {
	    // when printed, each graph prints its current flow
            System.out.println(graph);
	    // get the residual graph
	    // the first residual graph will be G_0^R
            ResGraph resGraph = graph.getResidual();
	    // when printed, each graph prints what graph it is the
	    // residual for
            System.out.println(resGraph);
	    // find a path in the residual graph, use either DFS or BFS
	    // comment out the one you do not want
            // path = resGraph.BFS(source,dest);
            path = resGraph.DFS(source, dest);
            //print the path
            System.out.println("path = " + path + "\n");
	    // update (that is augment) the graph with the new path
	    // if the new path is empty, no augmentation happens
            graph.augment(path);
	} while (path.size() > 0);  

    }
	
}
