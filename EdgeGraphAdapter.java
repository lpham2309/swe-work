import java.util.*;

public class EdgeGraphAdapter implements EdgeGraph {

    private Graph g;

    public EdgeGraphAdapter(Graph g) { this.g = g; }

    public boolean addEdge(Edge e) {
        if(!this.g.hasNode(e.getSrc())){
            this.g.addNode(e.getSrc());
        }

        if(!this.g.hasNode(e.getDst())){
            this.g.addNode(e.getDst());
        }

        if(!this.g.hasEdge(e.getSrc(), e.getDst())){
            this.g.addEdge(e.getSrc(), e.getDst());
            return true;
        }
        return false;
    }

    public boolean hasNode(String n) {
        List<String> curr_nodes = this.g.nodes();

        if(n == null) {
            return false;
        }

        if(curr_nodes.contains(n)) {
            return true;
        }

        if(curr_nodes.size() > 0) {
            for (String node : curr_nodes) {
                if (this.g.hasEdge(node, n)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasEdge(Edge e) {
	     if(this.g.hasEdge(e.getSrc(), e.getDst())){
             return true;
        }
        return false;
    }

    public boolean removeEdge(Edge e) {
	    if(this.g.succ(e.getSrc()).size() > 1 || this.g.succ(e.getDst()).size() > 1) {
            this.g.removeEdge(e.getSrc(), e.getDst());
        }
        if(this.g.succ(e.getSrc()).size() == 1) {
            this.g.removeEdge(e.getSrc(), e.getDst());
            return true;
        } else if(this.g.succ(e.getDst()).size() == 1) {
            this.g.removeEdge(e.getDst(), e.getSrc());
            return true;
        }
        return false;
    }

    public List<Edge> outEdges(String n) {
        List<Edge> out_edges = new ArrayList<>();
        if(n != null){
            List<String> edges = this.g.succ(n);

            for(String edge: edges){
                Edge curr_edge = new Edge(n, edge);
                out_edges.add(curr_edge);
            }
        }
        return out_edges;
    }

    public List<Edge> inEdges(String n) {
      List<Edge> in_edges = new ArrayList<>();

      if(n != null) {
          List<String> edges = this.g.pred(n);
          for (String node : edges) {
              Edge curr_edge = new Edge(node, n);
              in_edges.add(curr_edge);
          }
      }
      return in_edges;
    }

    public List<Edge> edges() {
        List<String> all_nodes = this.g.nodes();
        List<Edge> edges = new ArrayList<>();

        if(all_nodes.size() > 0) {
            for (String node : all_nodes) {
                List<String> node_edges = this.g.succ(node);
                for (String edge : node_edges) {
                    Edge curr_edge = new Edge(node, edge);
                    edges.add(curr_edge);
                }
            }
        }
        return edges;
    }

    public EdgeGraph union(EdgeGraph g) {
        List<Edge> g_edges = g.edges();
        if(g_edges.size() > 0) {
            for (Edge edge : g_edges) {
                this.addEdge(edge);
            }
        }
        return this;
    }

    public boolean hasPath(List<Edge> e) {
        if(e.size() < 1){
            return false;
        }

        for(int i = 0; i < e.size(); i++){
            Edge edge = e.get(i);
            if(i != e.size()-1){
                if(edge.getDst() == e.get(i+1).getSrc()){
                    e.get(i+1).getDst();
                    boolean is_path = this.g.connected(edge.getSrc(), e.get(i+1).getDst());
                    return is_path;
                } else {
                    throw new BadPath();
                }
            } else {
                return this.g.connected(edge.getSrc(), edge.getDst());
            }
        }
        return false;
    }
}
