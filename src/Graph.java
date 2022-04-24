import java.util.*;

/**
 * File Name: Graph.java
 * Author: RIT_CS, Daniel Kee Kim
 * Description: Graph class used in finding maximum flow utilizing Ford-Fulkerson Algorithm.
 * Full implementation of all functions. Uses DFS to search out optimal path.
 */

public class Graph {
    
    public Map<String, ArrayList<Edge>> map;   // keys are nodes
                                               // each node maps to an
                                               // list of Edges where the
                                               // node is the source
    public int flow;       // the flow from s -> t in this graph right now

     /**
     * Construct a Graph object
     * @param
     */
    public Graph() {
	this.map = new HashMap<String, ArrayList<Edge>>();
    }

    /**
     * add a new node to this Graph
     * @param node - is just a string label
     */ 
    public void addNode(String node) {
 	this.map.put(node, new ArrayList<Edge>());
    }

    /**
     * add a new edge to this graph
     * @param node - String - add this edge to nodes adjacency list
     * @param e - Edge to add
     */ 
    public void addEdge(String node, Edge e) {
	ArrayList<Edge> list;
        list = map.get(node);
	list.add(e);
        map.put(node,list);
    }

    /**
     * Get set of nodes (all string labels) for this graph
	@return Set of Strings (nodes) in this graph
     */ 
    public Set<String> getNodes (){
	return map.keySet();
    }

    /**
     * Get set of nodes (all string labels) for this graph
	@return Set of Strings (nodes) in this graph
     */     
    public ArrayList<Edge> getEdges (String n) {
	return map.get(n);
    }


    /**
     *	@return String representation of this Graph, includes the current flow
     */         
    public String toString() {
	String result = "G_" + this.flow + "\n";
	Set<String> nodes = this.map.keySet();
	for (String n : nodes) {
	    result += n + "=" + this.map.get(n) + "\n";
	}
	return result;
    }

    /**
     *	@return a residual graph corresponding to this Graph
     */             
    public ResGraph getResidual() {

	ResGraph graphR = new ResGraph();
	Set<String> nodes = this.getNodes();
	for (String n : nodes)
	    graphR.addNode(n);

        for (String n : nodes) {
            ArrayList<Edge> edgeList = this.getEdges(n);
	    for (Edge e : edgeList) {
		if (e.capacity == e.flow) 
		    graphR.addEdge(e.dest, new EdgeB(e.dest, e.source,
						   e.capacity, e.capacity));
		else if (e.flow == 0){
		    graphR.addEdge(n, new EdgeF(e.source, e.dest,
						e.capacity,  e.capacity));
		}
		else {
		    graphR.addEdge(n,
				   new EdgeF(e.source, e.dest, e.capacity,
					     e.capacity-e.flow));
		    graphR.addEdge(e.dest,
				   new EdgeB(e.dest, e.source, e.capacity, e.flow));
		}
	    }
	}

	graphR.flow = this.flow;
	return graphR;	    
    }


    /**
     *	@return augment(update) this graph with the new path
     */             
    public void augment(ArrayList<Edge> path) {
		// if the path is empty, there is no update to this graph
		if (path.size() <=0) return;

		int b = bottleNeck(path);
		for(Edge edge : path){
			if(edge instanceof EdgeF){
				for(Edge e : this.map.get(edge.source)){
					if(e.dest.equals(edge.dest)){
						e.flow += b;
					}
				}
			}
			else if (edge instanceof EdgeB){
				edge = new EdgeF(edge.dest, edge.source, edge.flow, edge.capacity);
				for(Edge e : this.map.get(edge.source)){
					if(e.dest.equals(edge.dest)){
						e.flow -= b;
					}
				}
			}
		}
		// update the flow of this graph with the bottleneck in the path
		this.flow +=b;
    }

    /**
     *	@return pick out and return an edge from this adjacency
     *      list given the destination node
     */             
    private Edge pickOut(ArrayList<Edge> edgeList, String dest) {
	for (Edge e : edgeList)
	    if (dest.equals(e.dest)) 
		return e;
	return null;

    }

    /**
     *	@return given a path (a listed of Edges), return
     *     the bottleneck
     */                 
    private int bottleNeck(ArrayList<Edge> path) {
	int n = path.size();
	int minFlow = path.get(0).flow;
        for (int i =1; i < n; i++) {
	    int flow = path.get(i).flow;
	    if (flow < minFlow)
		minFlow = flow;
	}
	return minFlow;
    }
}

// There is a residual graph for every graph

class ResGraph extends Graph{

    /*
     *	@return String representation of this Graph, includes the current flow
     */         
    public String toString() {
	String result = "G_" + this.flow + "^R\n";	
	Set<String> nodes = this.map.keySet();
	for (String n : nodes) {
	    result += n + "=" + this.map.get(n) + "\n";
	}
	return result;
    }

    // FINISH ONE OF THE FOLLOWING METHODS, either DFS or BFS
    /**
     *  @param source node for search
     *  @param target node for search
     *	@return a path from the source node to the target node
     *          which is a list of Edges
     *          returns an empty path[] if there is no path
     *          uses DFS
     */             
    public ArrayList <Edge> DFS(String source, String target){
		ArrayList<Edge> result;
    	HashMap<String, Boolean> visited = new HashMap<>();
		for(String node : this.getNodes()){
			visited.put(node, false);
		}

		result = DFSUtil(source, target, visited);
		if(result == null){
			return new ArrayList<Edge>();
		}

		return result;
    }

	/**
	 * DFS utility function that is used recursively
	 * @param source node for search
	 * @param target node for search
	 * @param visited boolean map of visited nodes
	 * @return list of all edges along the path, returns empty if none available
	 */
    private ArrayList<Edge> DFSUtil(String source, String target, HashMap<String, Boolean> visited){
    	ArrayList<Edge> result = new ArrayList<>();
    	visited.put(source, true);
    	for(Edge e : this.map.get(source)){
    		if(e.dest.equals(target)){
    			result.add(e);
    			return result;
			}
    		if(!visited.get(e.dest)){
    			ArrayList<Edge> temp = DFSUtil(e.dest, target, visited);
    			if(temp != null){
    				result.addAll(temp);
    				result.add(e);
    				return result;
				}
			}
		}

    	return null;
	}


    /**
     *  @param source node for search
     *  @param target node for search
     *	@return a path from the source node to the target node
     *          which is a list of Edges
     *          returns an empty path[] if there is no path
     *          uses BFS
     */             
    public ArrayList <Edge> BFS(String source, String target) {
    	ArrayList<Edge> result = new ArrayList<>();


		return result;
    }
    



}
