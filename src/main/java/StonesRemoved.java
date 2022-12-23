import java.util.*;

public class StonesRemoved {

    public int removeStonesdsu(int[][] stones) {
        int N = stones.length;
        DSU dsu = new DSU(20000);

        for (int[] stone: stones)
            dsu.union(stone[0], stone[1] + 10000);

        Set<Integer> seen = new HashSet();
        for (int[] stone: stones)
            seen.add(dsu.find(stone[0]));

        return N - seen.size();
    }
    
    
    class DSU {
        int[] parent;
        public DSU(int N) {
            parent = new int[N];
            for (int i = 0; i < N; ++i)
                parent[i] = i;
        }
        public int find(int x) {
            if (parent[x] != x) parent[x] = find(parent[x]);
            return parent[x];
        }
        public void union(int x, int y) {
            parent[find(x)] = find(y);
        }
    }
    
    //Union Find
    public int removeStones(int[][] stones) {
        if (stones == null || stones.length == 0 || stones[0].length < 2) {
            return 0;
        }

        Map<Integer, List<Node>> rowMap = new HashMap<>();
        Map<Integer, List<Node>> colMap = new HashMap<>();
        int maxRow = 0;
        int maxCol = 0;

        for (int[] stone : stones) {
            Integer row = stone[0];
            Integer col = stone[1];
            
            Node node = new Node(row, col);
            
            List<Node> rowList = rowMap.getOrDefault(row, new ArrayList<>());
            rowList.add(node);
            rowMap.put(row, rowList);
            
            List<Node> colList = colMap.getOrDefault(col, new ArrayList<>());
            colList.add(node);
            colMap.put(col, colList);

            if (row > maxRow) {
                maxRow = row;
            } 
            if (col > maxCol) {
                maxCol = col;
            }
        }

        UnionFind unionFind = new UnionFind(maxRow + 1, maxCol + 1);

        for (int[] stone : stones) {
            Integer row = stone[0];
            Integer col = stone[1];
            
            Node node = new Node(row, col);
            unionFind.addComponent(node);
        }

        for (List<Node> rowList : rowMap.values()) {
            buildUnion(unionFind, rowList);
        }

        for (List<Node> colList : colMap.values()) {
            buildUnion(unionFind, colList);
        }

        return stones.length - unionFind.numberOfComponents;
    }

    private void buildUnion(UnionFind unionFind, List<Node> nodes) {
        for (int index1 = 0; index1 < nodes.size(); index1++) {
            for (int index2 = index1 + 1; index2 < nodes.size(); index2++) {
                unionFind.union(nodes.get(index1), nodes.get(index2));
            }
        }
    }

    class UnionFind {
        //int[] components;
        Map<Integer, Integer> components = new HashMap<>();
        //int[] counts;
        Map<Integer, Integer> counts = new HashMap<>();
        int colSize;
        int numberOfComponents;

        UnionFind(int rowSize, int colSize) {
            // int gridSize = rowSize * colSize;
            this.colSize = colSize;
            // components = new int[gridSize];
            // counts = new int[gridSize];
        }

        void addComponent(Node node) {
            Integer index = indexForNode(node, colSize);
            components.put(index, index);
            counts.put(index, 1);
            // components[index] = index;
            // counts[index] = 1;
            numberOfComponents++;
        }

        int find(Integer index) {
            // while (components[index] != index) {
            //     index = components[index];
            // }
            while (!components.get(index).equals(index)) {
                index = components.get(index);
            }

            return index;
        }

        void union(Node node1, Node node2) {
            int index1 = indexForNode(node1, colSize);
            int index2 = indexForNode(node2, colSize);

            int root1 = find(index1);
            int root2 = find(index2);

            if (root1 == root2) return; //already in same component

            // int count1 = counts[root1];
            // int count2 = counts[root2];
            int count1 = counts.get(root1);
            int count2 = counts.get(root2);

            //weighted UF
            // if (count1 < count2) {
            //     components[root1] = root2;
            //     counts[root1] += counts[root2];
            // } else {
            //     components[root2] = root1;
            //     counts[root2] += counts[root1];
            // }
            if (count1 < count2) {
                components.put(root1, root2);
                counts.put(root1, count1 + count2);
            } else {
                components.put(root2, root1);
                counts.put(root2, count1 + count2);
            }

            numberOfComponents--;
        }

        private int indexForNode(Node node, int colSize) {
            return node.row * colSize + node.col;
        }
    }

    // O(n^2)
    public int removeStonesConnectedComponents(int[][] stones) {
        if (stones == null || stones.length == 0 || stones[0].length < 2) {
            return 0;
        }

        Graph graph = buildGraph(stones);
        
        Set<Node> visited = new HashSet<>();
        
        int component = 0;
        for (Node node : graph.nodes) {
            if (!visited.contains(node)) {
                dfs(graph, node, visited, component);
                component++;
            }
        }
        
        return componentCount(graph);
    }

    private int componentCount(Graph graph) {
        Map<Integer, List<Node>> componentMap = new HashMap<>();
        for (Node node : graph.nodes) {
            if (node.component != null) {
                List<Node> nodes = componentMap.getOrDefault(node.component, new ArrayList<>()); 
                nodes.add(node);
                componentMap.put(node.component, nodes);
            }
        }

        int max = 0;
        for (List<Node> nodes : componentMap.values()) {
            //ignore first (doesn't count as last stone can't be connected to another within component)
            max += nodes.size() - 1;
        }

        return max;
    }
    
    private void dfs(Graph graph, Node node, Set<Node> visited, int component) {
        if (visited.contains(node)) return;
        visited.add(node);
        node.component = component;
        for (Node adj : graph.adjList.getOrDefault(node, new ArrayList<>())) {
            dfs(graph, adj, visited, component);
        }
    }
    
    private Graph buildGraph(int[][] stones) {
        Map<Integer, List<Node>> rowMap = new HashMap<>();
        Map<Integer, List<Node>> colMap = new HashMap<>();
        List<Node> nodes = new ArrayList<>();
        
        for (int[] stone : stones) {
            Integer row = stone[0];
            Integer col = stone[1];
            
            Node node = new Node(row, col);
            nodes.add(node);
            
            List<Node> rowList = rowMap.getOrDefault(row, new ArrayList<>());
            rowList.add(node);
            rowMap.put(row, rowList);
            
            List<Node> colList = colMap.getOrDefault(col, new ArrayList<>());
            colList.add(node);
            colMap.put(col, colList);
        }
        
        Graph graph = new Graph(nodes);
        for (List<Node> rowList : rowMap.values()) {
            buildEdges(graph, rowList);
        }
        for (List<Node> colList : colMap.values()) {
            buildEdges(graph, colList);
        }
        
        return graph;
    }
    
    private void buildEdges(Graph graph, List<Node> nodes) {
        for (int index1 = 0; index1 < nodes.size(); index1++) {
            for (int index2 = index1 + 1; index2 < nodes.size(); index2++) {
                graph.addEdge(nodes.get(index1), nodes.get(index2));
            }
        }
    }
    
    class Graph {
        List<Node> nodes;
        Map<Node, List<Node>> adjList = new HashMap<>();
        
        Graph(List<Node> nodes) {
            this.nodes = nodes;
        }
        
        void addEdge(Node from, Node to) {
            List<Node> fromList = adjList.getOrDefault(from, new ArrayList<>());
            fromList.add(to);
            adjList.put(from, fromList);
            
            List<Node> toList = adjList.getOrDefault(to, new ArrayList<>());
            toList.add(from);
            adjList.put(to, toList);
        }
    }
    
    class Node {
        int row;
        int col;
        Integer component = null;
        
        Node(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    // O(n * n!) somehow optimized with backtracking + dynamic programming. but still slow.
    public int removeStonesSlow(int[][] stones) {
        if (stones == null || stones.length == 0 || stones[0].length < 2) {
            return 0;
        }
        
        Set<Integer> currentlyRemoved = new HashSet<>();
        Map<Indice, Integer> lookup = new HashMap<>();
        
        return removeStonesRec(stones, currentlyRemoved, lookup);
    }
    
    // [[0,0],[0,2],[1,1],[2,0],[2,2]]
    private int removeStonesRec(int[][] stones, Set<Integer> currentlyRemoved,
                                Map<Indice, Integer> lookup) {
        int leftStones = stones.length - currentlyRemoved.size(); //5
        if (leftStones == 1) return stones.length - 1; //BC
        
        Indice indice = new Indice(new HashSet<>(currentlyRemoved));
        Integer maxRemoved = lookup.get(indice);
        if (maxRemoved != null) return maxRemoved;
        
        maxRemoved = 0;
        
        for (Integer index = 0; index < stones.length; index++) { //0 .. 5
            if (currentlyRemoved.contains(index)) continue;
            
            if (isValid(stones, currentlyRemoved, index)) { //[0,0]
                currentlyRemoved.add(index); //0
                
                int localCount = removeStonesRec(stones, currentlyRemoved, lookup);
                maxRemoved = Math.max(maxRemoved, localCount);
                
                currentlyRemoved.remove(index); //backtrack
            }
        }
        
        lookup.put(indice, maxRemoved);
        
        return Math.max(maxRemoved, currentlyRemoved.size());
    }
    
    private boolean isValid(int[][] stones, Set<Integer> currentlyRemoved, Integer searchedIndex) {
        int searchedRow = stones[searchedIndex][0];
        int searchedCol = stones[searchedIndex][1];
        for (Integer index = 0; index < stones.length; index++) {
            if (currentlyRemoved.contains(index) || index == searchedIndex) continue;
            int[] stone = stones[index];
            if (stone[0] == searchedRow || stone[1] == searchedCol) return true;
        }
        return false;
    }
    
    class Indice {
        Set<Integer> checked;
        
        Indice(Set<Integer> checked) {
            this.checked = checked;
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(checked);
        }
        
        @Override
        public boolean equals(Object oth) {
            if (this == oth) return true;
            if (!(oth instanceof Indice)) return false;
            
            Indice other = (Indice) oth;
            return checked.equals(other.checked);
        }
    }

    public static void main(String[] args) {
        StonesRemoved rem = new StonesRemoved();
        // int[][] stones = new int[][] { { 0,1 }, { 1,0 } };
        // int[][] stones2 = new int[][] { { 0,0 }, { 0,1 }, { 1,0 }, { 1,2 }, { 2,1 }, { 2,2 } };
        // int[][] stones3 = new int[][] { { 0,0 }, { 0,2 }, { 1,1 }, { 2,0 }, { 2,2 } };
        int[][] stones4 = new int[][] { { 1,0 }, { 2,0 }, { 2,1 }};
        int[][] stones5 = new int[][] { { 3,2 }, { 3,1 }, { 4,4 }, { 1, 1 }, { 0, 2 }, { 4, 0 } };

        // System.out.println(rem.removeStonesdsu(stones));
        // System.out.println(rem.removeStonesdsu(stones2));
        // System.out.println(rem.removeStonesdsu(stones3));
        System.out.println(rem.removeStonesdsu(stones4));
        System.out.println(rem.removeStonesdsu(stones5));
        System.out.println(rem.removeStones(stones5));
    }
}

/*
    Rec. Backtracking:
    Base case: < 2 stones left (last stone can't be removed)
    for each stone - remove (if valid) - recursive call - restore/backtrack set
    args: stones, removed set<Integer> containing removed indices. return int.
        0	1	2
        0	x	x	
        1	x		x
        2		x	x
        BF: n * n!
        Backtracking: faster 
    
    dynamic progra.: cache Map<Indices, Integer> -> checked indices to count
    
    func isValid..
    
    class Indices: List<Integer>
*/