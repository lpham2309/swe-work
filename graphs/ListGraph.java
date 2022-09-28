import java.util.*;

public class ListGraph implements Graph {
    private HashMap<String, LinkedList<String>> nodes = new HashMap<>();

    public boolean addNode(String n) {
        if (nodes.containsKey(n)){
            return false;
        } else {
            nodes.put(n, new LinkedList<String>());
            return true;
        }
    }

    public boolean addEdge(String n1, String n2) {
        if (!nodes.containsKey(n1) || !nodes.containsKey(n2)){
            throw new NoSuchElementException();
        }

        LinkedList curr_node = nodes.get(n1);
        if (curr_node.isEmpty()){
            curr_node.add(n2);
            return true;
        }
        else{
            for (String n : nodes.get(n1)){
                if (n.equals(n2)){
                    return false;
                }
                else{
                    curr_node.add(n2);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasNode(String n) {
        if (nodes.containsKey(n)){
            return true;
        } else{
            return false;
        }
    }

    public boolean hasEdge(String n1, String n2) {
        if(n1 == null || n2 == null){
            return false;
        }

        if(nodes.containsKey(n1)) {
            for (String n : nodes.get(n1)) {
                if (n.equals(n2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean removeNode(String n) {
        if (nodes.containsKey(n)){
            nodes.remove(n);
            for (Map.Entry<String, LinkedList<String>> entry: nodes.entrySet()){
                LinkedList<String> curr_value = entry.getValue();
                for(String node: curr_value){
                    if (node.equals(n)){
                        curr_value.remove(n);
                    }
                }
            }
            return true;
        } else{
            return false;
        }
    }

    public boolean removeEdge(String n1, String n2) {
        if(!nodes.containsKey(n1) || !nodes.containsKey(n2)){
            throw new NoSuchElementException();
        }

        if(nodes.get(n1) != null){
            LinkedList<String> curr_edges;
            curr_edges = nodes.get(n1);
            if(curr_edges.contains(n2)) {
                curr_edges.remove(n2);
                return true;
            }
        }
        return false;
    }

    public List<String> nodes() {
        List<String> all_nodes = new ArrayList<>(nodes.keySet());
        return all_nodes;
    }

    public List<String> succ(String n) {
        if(!nodes.containsKey(n)){
            throw new NoSuchElementException();
        }

        List<String> all_successors = new ArrayList<>();
        for(String node: nodes.get(n)){
            all_successors.add(node);
        }
        return all_successors;
    }

    public List<String> pred(String n) {
        if(!nodes.containsKey(n)){
            throw new NoSuchElementException();
        }

        List<String> all_predecessors = new ArrayList<>();

        for (Map.Entry<String, LinkedList<String>> entry: nodes.entrySet()){
            String curr_key = entry.getKey();
            LinkedList<String> curr_value = entry.getValue();
            for(String node: curr_value){
                if (node == n){
                    all_predecessors.add(curr_key);
                }
            }
        }
        return all_predecessors;
    }

    public Graph union(Graph g) {
        Graph n_graph = new ListGraph();

        for(String node: nodes()){
            n_graph.addNode(node);
            for(String curr_node: nodes.get(node)) {
                if(!n_graph.nodes().contains(curr_node)){
                    n_graph.addNode(curr_node);
                }
                n_graph.addEdge(node, curr_node);
            }
        }

        List<String> g_nodes = g.nodes();

        for(String g_node: g_nodes){
            if (!n_graph.nodes().contains(g_node)) {
                n_graph.addNode(g_node);
            }
            if(g.succ(g_node).size() > 0){
                for(String edge: g.succ(g_node)){
                    n_graph.addEdge(g_node, edge);
                }
            }
        }

        return n_graph;
    }

    public Graph subGraph(Set<String> nodes) {
        Graph n_graph = new ListGraph();
        nodes.forEach((s) -> {
           List<String> all_nodes = this.nodes();
           if(all_nodes.contains(s)) {
               n_graph.addNode(s);
           }
        });
        for(String node: n_graph.nodes()){
            List<String> n_graph_edges = this.succ(node);
            if(!n_graph_edges.isEmpty() && nodes.contains(node) && this.nodes.containsKey(node)){
                for(String edge: n_graph_edges){
                    if(n_graph.nodes().contains(edge)) {
                        n_graph.addEdge(node, edge);
                    }
                }
            }
        }
        return n_graph;
    }

    public boolean connected(String n1, String n2) {
        if(!nodes.containsKey(n1) || !nodes.containsKey(n2)){
            throw new NoSuchElementException();
        }

        Queue<String> queue = new LinkedList<>();
        queue.add(n1);
        HashSet<String> explored = new HashSet<>();

        while (!queue.isEmpty()){
            String v = queue.poll();
            if(v.equals(n2)) {
                return true;
            }
            for(String edge: nodes.get(v)){
                if(!explored.contains(edge)){
                    explored.add(edge);
                } else {
                    return false;
                }
                queue.add(edge);
            }
        }
        return false;
    }
}
