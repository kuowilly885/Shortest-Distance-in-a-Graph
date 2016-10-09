import java.util.ArrayList;

public class Graph {
	ArrayList<Edge> Edges;
	ArrayList<Node> Nodes;
	
	public Graph () {
		Edges = new ArrayList<Edge> ();
		Nodes = new ArrayList<Node> ();
	}
	
	public boolean addNode (Node n) {
		if (Nodes.size() > 0) {
			boolean sameNodeFound = false;
			for (Node obj: Nodes) {
			    if (obj.name.equals(n.name))
			    	sameNodeFound = true;
			}
			if (!sameNodeFound) {
				Nodes.add(n);
				return true;
			}

		} else {
			Nodes.add(n);
			return true;
		}

		return false;
		
	}
	
	public boolean addEdge (Edge e) {
		boolean edgeEndPoint0Repeated = false, edgeEndPoint1Repeated = false, sameEdgeFound = false;
		Node repeatedEndpoint0 = null, repeatedEndpoint1 = null;
		if (Edges.size() > 0) {
			for (Node obj: Nodes) {
			    if (obj.name.equals(e.endpoints[0].name)) {
			    	edgeEndPoint0Repeated = true;
			    	repeatedEndpoint0 = obj;
			    } else if (obj.name.equals(e.endpoints[1].name)) {
			    	edgeEndPoint1Repeated = true;
			    	repeatedEndpoint1 = obj;
			    }
			}

			for (Edge obj: Edges) {
			    if ((obj.endpoints[0].name.equals(e.endpoints[0].name) &&
			    	obj.endpoints[1].name.equals(e.endpoints[1].name)) ||
			    	(obj.endpoints[0].name.equals(e.endpoints[1].name) &&
			    	obj.endpoints[1].name.equals(e.endpoints[0].name)))
			    	sameEdgeFound = true;
			}

			if (!sameEdgeFound && !edgeEndPoint0Repeated && !edgeEndPoint1Repeated)
				return Edges.add(e);
			else if (!sameEdgeFound && edgeEndPoint0Repeated && !edgeEndPoint1Repeated)
				return Edges.add(new Edge(repeatedEndpoint0, e.endpoints[1], e.length));
			else if (!sameEdgeFound && !edgeEndPoint0Repeated && edgeEndPoint1Repeated)
				return Edges.add(new Edge(repeatedEndpoint1, e.endpoints[0], e.length));
			else if (!sameEdgeFound && edgeEndPoint0Repeated && edgeEndPoint1Repeated)
				return Edges.add(new Edge(repeatedEndpoint1, repeatedEndpoint0, e.length));
		} else
			return Edges.add(e);

		return false;
	}

	public ArrayList<Node> adjacentNodes (Node n) {
		ArrayList<Node> adjNodes = new ArrayList<Node>();
		for (Edge obj: Edges) {
			if (obj.endpoints[0].name.equals(n.name))
				adjNodes.add(obj.endpoints[1]);
			else if (obj.endpoints[1].name.equals(n.name))
				adjNodes.add(obj.endpoints[0]);
		}
		return adjNodes;
	}

	public int lengthForNodes (Node n1, Node n2) {
		for (Edge obj: Edges) {
			if ((obj.endpoints[0].name.equals(n1.name) && obj.endpoints[1].name.equals(n2.name))
				|| (obj.endpoints[0].name.equals(n2.name) && obj.endpoints[1].name.equals(n1.name)))
				return obj.length;
		}
		return -1;
	}
}
