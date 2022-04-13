import java.util.*;

public class Edge {

    public String source;  // source node
    public String dest;    // destination node
    public int capacity;   // flow capacity c_e for this edge
    public int flow;       // the actual flow on this edge

    /**
     * Construct a Graph object
     * @param source  - a node
     * @param dest - a destination node
     * @param capacity - of this edge
     * @param flow - current flow in this edge
     *    constructs a new edge from source to dest with the given
     *    capacity and flow
     */
    public Edge (String source, String dest, int capacity, int flow){
	this.source = source;
	this.dest = dest;
	this.capacity = capacity; 
	this.flow = flow;     
    }

    /**
     *  @return a string representation of this edge as s->d (cap) flow
     */
    public String toString() {
	return this.source + "->"+ this.dest +
	    " (" + this.capacity +") " + this.flow;
    }
}

// forward edge this should only be used in residual graphs
class EdgeF extends Edge {

    public EdgeF (String source, String dest, int capacity, int flow){
	super(source, dest, capacity, flow);
    }

    public String toString() {
	return "E_F: "+ this.source + "->"+ this.dest + " " + this.flow;
    }
}

// backward edge this should only be used in residual graphs
class EdgeB extends Edge {

     public EdgeB (String source, String dest, int capacity, int flow){
	super(source, dest, capacity, flow);
    }
    
    public String toString() {
	return "E_B: "+ this.source + "->"+ this.dest + " " + this.flow;
    }
}









