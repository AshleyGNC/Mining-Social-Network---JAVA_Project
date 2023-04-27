import java.util.*;

public class Influence {
	// the nested class used to define a influencer
	public static class influencer {
		int source; // the influencer
		double power; // the impact of this influencer

		influencer(int i, double p) {
			source = i;
			power = p;
		}

		int getSource() {
			return source;
		}

		double getPower() {
			return power;
		}
	}
	
	//initialize priorityQueue to store influencers
	private Queue<influencer> influencerQueue = new PriorityQueue<influencer>(); 
    private	int[]	distTo;	
	
    
	public Influence(Graph g) {
		// Create a Comparator to compare influencers by their influence
		Comparator<influencer> influencerComparator = new Comparator<influencer>() {
		    public int compare(influencer i1, influencer i2) {
		        return Double.compare(i2.power, i1.power);
		    }
		};
		// Initialize the priority queue with the Comparator
		influencerQueue = new PriorityQueue<influencer>(influencerComparator);	
		distTo = new int[g.getV()*2];
		
		for (int v: g.getVertices()) {		
			influencer i = new influencer(v, bfs(g, v));  //(v, POWER OF SUCH V)
		    influencerQueue.add(i);
		}	    
	}

	    private double bfs(Graph G, int s) { 
	    	double power = 0.0;
	    	Queue<Integer> q = new LinkedList<Integer>();
	    	Set<Integer> visited = new HashSet<Integer>();
	    	q.add(s);   	
	    	visited.add(s);
	    	distTo[s] = 0;
	    	
	    	while (!q.isEmpty()) {
	    		int v = q.poll();
	    		for (int w : G.adj(v)) {
	    			if (!visited.contains(w)) {
	    				q.add(w);
	    				visited.add(w);		
	        			distTo[w] = distTo[v] + 1;
	        			power += 1/(Math.pow(2, distTo[w]-1)); 
		    		}
		    	}	    		 
	    	}
	    	return power;
	    }
		  
	    
	    // Method to return the top k influencers
		  public List<influencer> top(int k) {
			  ArrayList<influencer> topInfluencers = new ArrayList<>();
			  for (int i = 0; i < k; i++) {
			      topInfluencers.add(influencerQueue.poll());
			    }
			    return topInfluencers;
		  }
}

