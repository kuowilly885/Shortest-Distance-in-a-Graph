public class Edge {
	public Node[] endpoints = new Node[2];
	public int length = 0;
	public Edge (Node end1, Node end2, int length) {
		endpoints[0] = end1;
		endpoints[1] = end2;
		this.length = length;
	}
}
