import java.util.*;

public class Ego {

    // the nested class used to define a egonet
    public static class egonet implements Comparable<egonet>{
        int center; // center of the egonet
        Graph G; // subgraph that represents the egonet

        egonet(int c, Graph g) {
            center = c;
            G = g;
        }

        int getCenter() {
            return center;
        }

        Graph getG() {
            return G;
        }
        @Override
        public int compareTo(egonet other) {
        // Compare the center of this ego network with the center of the other ego network
        return this.center - other.center;
        }
    }
    // Define the fields for the Ego class
 	Graph g;
 	Map<Integer, ArrayList<egonet>> egoNetworks = new HashMap<>();

 	
    public Ego(Graph g) {
	    this.g = g;
	    this.egoNetworks = new HashMap<>();
	    //[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
        for (int ego : g.getVertices()) {
            Set<Integer> neighbors = g.adj(ego); 				  // Get the neighbors of the ego node
            Graph egoNetwork = g.getSubgraph(ego, neighbors, g); // Create a subgraph for the ego network
            int numEdges = egoNetwork.getE(); 					// Get the number of edges in the ego network
            
            egonet e = new egonet(ego, egoNetwork);
            ArrayList<egonet> tempList = null;
            if (egoNetworks.containsKey(numEdges)) {
               tempList = egoNetworks.get(numEdges);
               if(tempList == null)
                  tempList = new ArrayList<egonet>();
               	  tempList.add(e);  
            } else {
               tempList = new ArrayList<egonet>();
               tempList.add(e);               
            }
            egoNetworks.put(numEdges,tempList);
        }

    }

    // Method to return the top k ego networks with the largest number of edges
    public List<egonet> top(int k) {
    List<egonet> topEgoNetworks = new ArrayList<>();
    
    //set keySet in descending order 
    Set<Integer> temp = egoNetworks.keySet();
    ArrayList<Integer> list = new ArrayList<>(temp);
    Collections.sort(list, Collections.reverseOrder());
    Set<Integer> resultSet = new LinkedHashSet<>(list);
    

      // Iterate over the ego networks in the map
      for (int numEdges : resultSet) {    				//keySet in descending order
    	  for (egonet e: egoNetworks.get(numEdges)) {
    	        topEgoNetworks.add(e);
    	            
    	  }
    	// If we have added k ego networks, we can stop iterating    	    
    	  if (topEgoNetworks.size() == k) {
	            break;
	      }	  
      }
      return topEgoNetworks;
    }
}

