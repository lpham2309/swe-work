import java.util.*;
public class Main {

	// Run "java -ea Main" to run with assertions enabled (If you run
	// with assertions disabled, the default, then assert statements
	// will not execute!)

	public static void test1() {
		Graph g = new ListGraph();
		assert g.addNode("a");
		assert g.hasNode("a");
	}

	public static void test2() {
		Graph g = new ListGraph();
		EdgeGraph eg = new EdgeGraphAdapter(g);
		Edge e = new Edge("a", "b");
		assert eg.addEdge(e);
		assert eg.hasEdge(e);
	}

	public static void test_3() {
		Graph graph = new ListGraph();

		graph.addNode("A");
		graph.addNode("B");
		graph.addNode("C");

		graph.addEdge("A", "B");

		assert graph.hasNode("A");
		assert graph.hasNode("B");
		assert graph.hasNode("C");
		assert !graph.hasNode("D");
		assert graph.hasEdge("A", "B");
	}

	public static void test_4() {
		Graph graph = new ListGraph();
		EdgeGraph eg = new EdgeGraphAdapter(graph);

		eg.addEdge(new Edge("B", "C"));

		graph.addNode("A");
		graph.addNode("B");

		graph.removeNode("B");

		assert !graph.hasNode("B");
		assert graph.hasNode("A");
		assert eg.hasNode("A");
		assert eg.hasNode("C");
	}

	public static void test_5() {
		Graph graph = new ListGraph();
		EdgeGraph eg = new EdgeGraphAdapter(graph);

		graph.addNode("A");
		graph.addNode("B");
		graph.addNode("C");

		eg.addEdge(new Edge("C", "B"));

		assert graph.addEdge("A", "C");
		assert graph.removeEdge("A", "C");
		assert !graph.hasEdge("A", "C");
		assert graph.nodes().size() == 3;
		assert eg.hasEdge(new Edge("C", "B"));
		assert !eg.hasEdge(new Edge("A", "B"));
	}

	public static void test_6() {
		Graph graph = new ListGraph();

		graph.addNode("A");
		graph.addNode("B");
		graph.addNode("C");
		graph.addNode("D");

		graph.addEdge("A", "B");
		graph.addEdge("A", "C");
		graph.addEdge("B", "C");
		graph.addEdge("B", "D");

		assert graph.succ("A").size() == 2;
		assert graph.succ("B").size() == 2;
		assert graph.succ("C").isEmpty();
		assert !graph.pred("C").isEmpty();
		assert graph.pred("C").size() == 2;
	}

	public static void test_7() {
		Graph graph1 = new ListGraph();

		graph1.addNode("A");
		graph1.addNode("B");
		graph1.addNode("C");

		graph1.addEdge("A", "B");
		graph1.addEdge("B", "C");

		Graph graph2 = new ListGraph();

		graph2.addNode("C");
		graph2.addNode("D");
		graph2.addNode("E");

		graph2.addEdge("D", "C");
		graph2.addEdge("E", "C");

		Graph union_graph = graph1.union(graph2);

		assert union_graph.nodes().size() == 5;
		assert union_graph.pred("C").size() == 3;
		assert union_graph.hasEdge("A", "B");
		assert union_graph.hasEdge("D", "C");
		assert union_graph.hasEdge("B", "C");
		assert union_graph.hasEdge("E", "C");
	}
	public static void test_8(){
		Graph graph = new ListGraph();

		graph.addNode("A");
		graph.addNode("B");
		graph.addNode("C");
		graph.addNode("D");

		graph.addEdge("A", "B");
		graph.addEdge("A", "C");
		graph.addEdge("C", "D");

		Set<String> nodes = new HashSet<>();
		nodes.add("A");
		nodes.add("B");
		nodes.add("C");
		nodes.add("E");

		Graph sub_graph = graph.subGraph(nodes);
		assert sub_graph.nodes().size() == 3;
		assert !sub_graph.hasNode("E");
		assert !sub_graph.hasNode("D");
		assert sub_graph.hasEdge("A", "B");
		assert sub_graph.hasEdge("A", "C");
	}

	public static void test_9(){
		Graph graph = new ListGraph();

		graph.addNode("A");
		graph.addNode("B");
		graph.addNode("C");
		graph.addNode("D");

		graph.addEdge("A", "B");
		graph.addEdge("A", "C");
		graph.addEdge("C", "D");

		assert !graph.connected("B", "D");
		assert graph.connected("A", "D");
	}

	public static void test_10(){
		Graph graph = new ListGraph();
		EdgeGraph eg = new EdgeGraphAdapter(graph);

		graph.addNode("A");
		graph.addNode("B");
		graph.addNode("C");

		eg.addEdge(new Edge("C", "B"));
		eg.removeEdge(new Edge("C", "B"));

		assert !eg.hasEdge(new Edge("C", "B"));
	}

	public static void test_11(){
		Graph graph = new ListGraph();
		EdgeGraph eg = new EdgeGraphAdapter(graph);

		eg.addEdge(new Edge("A", "B"));
		eg.addEdge(new Edge("A", "C"));
		eg.addEdge(new Edge("A", "D"));
		eg.addEdge(new Edge("B", "D"));
		eg.addEdge(new Edge("D", "C"));

		assert eg.outEdges("A").size() == 3;
		assert eg.outEdges("B").size() == 1;
		assert eg.inEdges("C").size() == 2;
		assert eg.inEdges("D").size() == 2;
		assert eg.edges().size() == 5;
		assert eg.outEdges("A") instanceof List;
		assert eg.inEdges("C") instanceof List;
		assert eg.edges() instanceof  List;
	}

	public static void test_12(){
		Graph graph1 = new ListGraph();
		EdgeGraph eg1 = new EdgeGraphAdapter(graph1);
		eg1.addEdge(new Edge("A", "B"));
		eg1.addEdge(new Edge("C", "D"));
		eg1.addEdge(new Edge("C", "B"));

		Graph graph2 = new ListGraph();
		EdgeGraph eg2 = new EdgeGraphAdapter(graph2);
		eg2.addEdge(new Edge("A", "D"));
		eg2.addEdge(new Edge("D", "C"));
		eg2.addEdge(new Edge("E", "F"));

		EdgeGraph ugraph = eg1.union(eg2);

		assert ugraph instanceof EdgeGraph;
		assert ugraph != null;
		assert ugraph.edges().size() == 6;
	}

	public static void test_13() {
		Graph graph = new ListGraph();
		EdgeGraph eg = new EdgeGraphAdapter(graph);
		eg.addEdge(new Edge("A", "B"));
		eg.addEdge(new Edge("C", "D"));
		eg.addEdge(new Edge("B", "D"));
		eg.addEdge(new Edge("D", "A"));
		eg.addEdge(new Edge("E", "F"));

		List<Edge> new_edge1 = new ArrayList<>();
		new_edge1.add(new Edge("A", "E"));
		new_edge1.add(new Edge("D", "C"));

		List<Edge> new_edge2 = new ArrayList<>();
		new_edge2.add(new Edge("A", "B"));
		new_edge2.add(new Edge("B", "D"));
		new_edge2.add(new Edge("D", "C"));

		List<Edge> new_edge3 = new ArrayList<>();
		new_edge3.add(new Edge("A", "F"));

		assert eg.hasPath(new_edge2);
		assert !eg.hasPath(new_edge3);
	}

	public static void test_14() {
		Graph graph1 = new ListGraph();

		graph1.addNode("x");
		graph1.addNode("a");
		graph1.addNode("b");
		graph1.addNode("c");

		graph1.addEdge("x", "a");
		graph1.addEdge("a", "b");
		graph1.addEdge("b", "c");

		assert graph1.removeNode("a");
		assert !graph1.removeNode(null);
	}

	public static void main(String[] args) {
		test1();
		test2();
		test_3();
		test_4();
		test_5();
		test_6();
		test_7();
		test_8();
		test_9();
		test_10();
		test_11();
		test_12();
		test_13();
		test_14();
	}
}

