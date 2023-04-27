import java.util.*;

public class CC {

	// the nested class used to define a connected component
	public static class cc {
		int id; // the id of the component
		int size; // the size of the component
		

		cc(int i, int s) {
			id = i;
			size = s;
		}

		int getId() {
			return id;
		}

		int getSize() {
			return size;
		}
	}

	private boolean[] marked;
	private int[] id;  
	private int count;
	

	public CC(Graph G) {
		marked = new boolean[G.getV()*2]; 
		id = new int[G.getV()*2];
		for (int v : G.getVertices()) {
			if (!marked[v]) {
				dfs(G, v);  
				count++;
			}
		}
	}
	public int count() { 
		return count; 
	}
	public int id(int v) { 
		return id[v]; 
	}

	
	private void dfs(Graph G, int v) {
	    marked[v] = true;
	    id[v] = count+1;
		for (int w : G.adj(v)) {
			if (!marked[w])
		    dfs(G, w);
		}
	}

	public List<cc> top(int k) {
		  // Create a list to store the communities
		  List<cc> communities = new ArrayList<>();
		  
		  // Loop through each vertex in the graph
		  for (int v = 0; v < marked.length; v++) {
		    if (marked[v]) {
		      int communityId = id[v];

		      // Find the community with the current id in the list of communities
		      cc community = null;
		      for (cc c : communities) {
		    	  if (c.getId() == communityId) {
		    		  community = c;
					  break;
		    	  }
		      }
		      
		 // If the community with the current id is not in the list, add it
		      if (community == null){ 
		    	  community = new cc(communityId, 0);
		    	  communities.add(community);
		      }
		      community.size++;
		    }
		  }

		  // Sort the list of communities by size in descending order
		  Collections.sort(communities, (a, b) -> b.size - a.size);
		  
		  // Return the first k communities from the sorted list
		  if (k > communities.size()) {
			  return communities.subList(0, communities.size());
		  }
		  return communities.subList(0, k);
		}
}

