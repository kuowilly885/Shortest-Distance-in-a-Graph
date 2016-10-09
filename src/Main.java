import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader("file.txt"));
		Graph G = new Graph();
	    char maxPoint = 0;
	    char minPoint = 0;
	    String start, end;
	    Node startNode = null, endNode = null;
	    String line = br.readLine();
	    while (line != null) {
	    	String[] split = line.split(" ");
	    	String p1 = split[0];
	    	String p2 = split[1];
	    	String length = split[2];
	    	Node node1 = new Node(p1);
	    	Node node2 = new Node(p2);
	    	Edge edge = new Edge(node1, node2, Integer.parseInt(length));
	    	G.addNode(node1);
	    	G.addNode(node2);
	    	G.addEdge(edge);
	        line = br.readLine();
		}
	    br.close();

		for (Node vx: G.Nodes) {
			char k = vx.name.toCharArray()[0];
			if (k > maxPoint)
				maxPoint = k;
			if (minPoint == 0 || k < minPoint)
				minPoint = k;
		}

		System.out.println("Welcome to the shortest path Dijkstra Algorithm program !");
		System.out.println("Please insert source vertex to destination vertex in the vertex range of file.txt, which means " +  minPoint + " to " + maxPoint + ".");
        Scanner scanner = new Scanner(System.in);
        while(true) {
    		System.out.println("Input source vertex please.");
            start = scanner.next();
    		System.out.println("Input destination vertex please.");
            end = scanner.next();
 
            boolean findValidStartVetex = false, findValidEndVetex = false;
    	    for (Node vt: G.Nodes) {
    	    	if (vt.name.equals(start)) {
    	    		findValidStartVetex = true;
    	    		startNode = vt;
    	    	} else if (vt.name.equals(end)) {
    	    		findValidEndVetex = true;
    	    		endNode = vt;
    	    	}
    	    }

            if (findValidStartVetex && findValidEndVetex) {
            	scanner.close();
            	break;
            } else {
        		System.out.println("Please input valid vertex value...");
        		continue;
            }
            
        }

	    //Algorithm Dijkstra
	    DijkstraShortestPaths(G, startNode);

    	System.out.println("From " + startNode.name + " to " + endNode.name + " minimum distance : " + endNode.value);
    	ArrayList<String> path = new ArrayList<String>();
    	Node pre = endNode.cloudPre;
    	path.add(endNode.name);
	    while (!pre.name.equals(start)) {
	    	path.add(pre.name);
	    	pre = pre.cloudPre;
	    }
    	path.add(pre.name);
    	System.out.print("Minimum path : ");
		for (int k = path.size() - 1 ; k >= 0 ; k--) {
			if (k == path.size()-1)
				System.out.print(path.get(k));
			else
				System.out.print("->" + path.get(k));
		}
		System.out.print("\n");
	}

	public static void DijkstraShortestPaths(Graph G, Node v) {
		for (Node obj: G.Nodes) {
		    if (obj.name.equals(v.name))
		    	obj.value = 0;
		    else
		    	obj.value = -1;
		}

	    Comparator<Node> comparator = new Comparator<Node>() {
	        public int compare(Node c1, Node c2) {
	        	if (c1.value == -1 && c2.value == -1)
		            return 0;
	        	else if (c1.value == -1 && c2.value == 0)
		            return c2.value - c1.value;
	        	else if (c1.value == -1 && c2.value > 0)
		            return c2.value - c1.value;
	        	else if (c1.value == 0 && c2.value == 0)
	        		return 0;
	        	else if (c1.value == 0 && c2.value == -1)
	        		return c2.value - c1.value;
	        	else if (c1.value == 0 && c2.value > 0)
	        		return c2.value - c1.value;
	        	else if (c1.value > 0 && c2.value == -1)
	        		return c2.value - c1.value;
	        	else if (c1.value > 0 && c2.value == 0)
	        		return c1.value - c2.value;
	        	else if (c1.value > 0 && c2.value > 0)
	        		return c1.value - c2.value;
        		return 0;
	        }
	    };

		ArrayList<Node> Q = new ArrayList<Node>(G.Nodes);
	    Collections.sort(Q, comparator);

	    while (!Q.isEmpty()) {
		    Node u = Q.remove(0);
			for (Node z: G.adjacentNodes(u)) {
				if (z.value == -1 || u.value + G.lengthForNodes(u, z) < z.value) {
					z.value = u.value + G.lengthForNodes(u, z);
				    Collections.sort(Q, comparator);
				    z.cloudPre = u;
				}
			}
	    }
	}
}
