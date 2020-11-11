/* security.java written: by Russell Abernethy.*/
/* Outputs: 73162890 */

import edu.uci.ics.jung.graph.*;
import edu.uci.ics.jung.graph.util.EdgeType;
import java.util.*;
import java.io.*;

public class Security {
    public static void main(String[] args) {
        DirectedSparseMultigraph<Integer, String> graph = new DirectedSparseMultigraph<>();
        /* Read in the numbers from keylog.txt.
        Note: duplicate entries have been removed from the file. */
        File text = new File("keylog.txt");
        try {
            Scanner scan = new Scanner(text);
            while(scan.hasNext()) {
                int n = scan.nextInt();
                int B = n / 100 % 10;
                int M = n / 10 % 10;
                int E = n % 10;

                if(!graph.containsVertex(B))
                    graph.addVertex(B);

                if(!graph.containsVertex(M))
                    graph.addVertex(M);

                if(!graph.containsVertex(E))
                    graph.addVertex(E);

                if(!graph.containsEdge(B + " --> " + M))
                    graph.addEdge(B + " --> " + M, B, M, EdgeType.DIRECTED);

                if(!graph.containsEdge(M + " --> " + E))
                    graph.addEdge(M + " --> " + E, M, E, EdgeType.DIRECTED);
            }
            scan.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occured while trying to open the file. Exiting.");
        }
        
        /* Topological Sorting of the graph using Khan's Algorithm. */

        List<Integer> L = new LinkedList<>(); // List of sorted items
        List<Integer> S = new LinkedList<>(); // List of all nodes with no incoming edges.

        /* Add all nodes with no incoming edges to S. */
        for (Integer node : graph.getVertices()) {
            if (graph.inDegree(node) == 0)
                S.add(node);
        }

        /* While S is not empty */
        while (!S.isEmpty()) {
            /* Remove a node from the list of nodes with no incoming edges and add it to the returned list. */
            Integer vertex = S.remove(S.size() - 1);
            L.add(vertex);
            /* Get all of the edges going out from the node and remove them. */
            Collection<String> outEdge = graph.getIncidentEdges(vertex);
            for (String outedge : outEdge) {
                Integer dest = graph.getDest(outedge);
                graph.removeEdge(outedge);
                    
                if(graph.inDegree(dest) == 0) {
                    S.add(dest);
                }
            }
        }
        if(graph.getEdgeCount() != 0) {
            System.out.println("Rrror, cycles in graph.");
        }
        else
            System.out.println(L);
        
    }
}
