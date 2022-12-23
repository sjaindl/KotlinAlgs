import java.util.*;

public class EvalDivision {
    
    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        // build Directed weighted graph (Graph + nodes classes). Adj list.
        // -> weight = value from node to other node.
        // Alg.: Path search: Start at first + second node. use set. revert calc. if 1st after 2nd.
        // if not node in list -> -1
        // if same node -> 1
        
        Graph graph = buildGraph(equations, values);
        
        double[] output = new double[queries.size()];
        
        for (int index = 0; index < queries.size(); index++) {
            List<String> query = queries.get(index);
            output[index] = evaluate(graph, query.get(0), query.get(1));
        }
        
        return output;
    }
    
    private double evaluate(Graph graph, String firstExpr, String secondExpr) {
        Node firstNode = graph.findNode(firstExpr);
        Node secondNode = graph.findNode(secondExpr);
        
        if (firstNode == null || secondNode == null) {
            return -1;
        }
        
        if (firstNode == secondNode) {
            return 1;
        }
        
        Set<Node> visited = new HashSet<>();
        visited.add(firstNode);
        return evaluateDfs(graph, 1, firstNode, secondNode, visited);
    }
    
    private double evaluateDfs(Graph graph, double division, Node curNode, 
                               Node targetNode, Set<Node> visited) {
        if (curNode.equals(targetNode)) {
            return division;
        }
        
        for (Edge edge : graph.neighbours(curNode)) {
            if (!visited.contains(edge.to)) {
                visited.add(edge.to);
                double value = evaluateDfs(graph, division * edge.weight, edge.to, 
                                           targetNode, visited);
                if (value != -1) return value;
            }
        }
        
        return -1;
    }
    
    private Graph buildGraph(List<List<String>> equations, double[] values) {
        Set<Node> nodes = new HashSet<>();
        
        for (int index = 0; index < equations.size(); index++) {
            List<String> equation = equations.get(index);
            nodes.add(new Node(equation.get(0)));
            nodes.add(new Node(equation.get(1)));
        }
        
        List<Node> nodesList = new ArrayList<>(nodes);
        Graph graph = new Graph(nodesList);
        
        for (int index = 0; index < equations.size(); index++) {
            List<String> equation = equations.get(index);
            Node from = new Node(equation.get(0));
            Node to = new Node(equation.get(1));
            double weight = values[index];
            graph.addEdge(from, to, weight);
            graph.addEdge(to, from, 1 / weight);
        }
        
        return graph;
    }
    
    
    class Graph {
        //directed weighted edges
        List<Node> nodes;
        Map<Node, List<Edge>> adjList;
        
        Graph(List<Node> nodes) {
            this.nodes = nodes;
            adjList = new HashMap<>();
            for (Node node : nodes) {
                adjList.put(node, new ArrayList<>());
            }
        }
        
        List<Edge> neighbours(Node node) {
            return adjList.get(node);
        }
        
        void addEdge(Node from, Node to, double weight) {
            adjList.get(from).add(new Edge(to, weight));
        }
        
        Node findNode(String value) {
            for (Node node : nodes) {
                if (node.value.equals(value)) return node;
            }
            
            return null;
        }
    }
    
    class Edge {
        Node to;
        double weight;
        
        Edge(Node to, double weight) {
            this.to = to;
            this.weight = weight;
        }
    }
    
    class Node {
        String value;
        
        Node(String value) {
            this.value = value;
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
        
        @Override
        public boolean equals(Object other) {
            if (other == null || !(other instanceof Node)) return false;
            if (other == this) return true;
            Node oth = (Node) other;
            
            return value.equals(oth.value);
        }
    }

    public static void main(String[] args) {
        EvalDivision evalDivision = new EvalDivision();

        List<List<String>> equations = new ArrayList<>();
        equations.add(List.of("a", "b"));
        equations.add(List.of("b", "c"));

        double[] values = new double[] { 2.0, 3.0 };
        
        List<List<String>> queries = new ArrayList<>();
        queries.add(List.of("a", "c"));
        queries.add(List.of("b", "a"));
        queries.add(List.of("a", "e"));
        queries.add(List.of("a", "a"));
        queries.add(List.of("x", "x"));
        double[] result = evalDivision.calcEquation(equations, values, queries);
        Arrays.toString(result);
    }
}

/*
    a / b = 2.0, b / c = 3.0
    b / a = 0.5, c / b = 0.33...
    
    a = 2b
    b = a/2
    b = 3c
    c = b/3
    
    a = 2 * (a/2) -> a = a
    
    a / c = 2b / (b/3) = b / 3 = 3c/3 = c -> a = 1 = 6
    b = 0.5 = 3
    c = 1/6 = 1
    
    a / c = ?, 
    b / a = ?, 
    a / e = ?, 
    a / a = ?, 
    x / x = ?

*/