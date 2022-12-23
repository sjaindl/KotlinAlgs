import java.util.*;

public class CourseOrder {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        Vertice<Integer>[] vertices = new Vertice[numCourses];
        
        for (int course = 0; course < numCourses; course++) {
            vertices[course] = new Vertice<>(course, course);
        }
        
        DirectedGraph<Integer> graph = new DirectedGraph<>(vertices);
        
        for (int[] prereq : prerequisites) {
            graph.addEdge(vertices[prereq[1]], vertices[prereq[0]]);
        }
                                    
        LinkedList<Vertice<Integer>> processing = new LinkedList<>();
        LinkedList<Vertice<Integer>> output = new LinkedList<>();
        
        for (Vertice<Integer> vertice : vertices) {
            if (vertice.inboundCount == 0) {
                processing.addFirst(vertice);
            }
        }
                                    
        while (!processing.isEmpty()) {
            Vertice<Integer> vertice = processing.removeLast();
            output.add(vertice);
            
            for (Vertice<Integer> next : graph.adjacents(vertice)) {
                next.inboundCount--;
                if (next.inboundCount == 0) {
                    processing.addFirst(next);
                }
            }
        }
                                    
        if (output.size() < numCourses) {
            //cycle!
            return new int[0];
        }
        
        int[] outputArray = new int[numCourses];
        
        int index = 0;
        for (Vertice<Integer> course : output) {
            outputArray[index] = course.id;
            index++;
        }
                                    
        return outputArray;
    }
    
    class DirectedGraph<T> {
        Vertice<T>[] vertices;
        Map<Vertice<T>, List<Vertice<T>>> adjList = new HashMap<>();
        
        DirectedGraph(Vertice<T>[] vertices) {
            this.vertices = vertices;
        }
        
        void addEdge(Vertice<T> from, Vertice<T> to) {
            List<Vertice<T>> edges = adjList.getOrDefault(from, new ArrayList<>());
            edges.add(to);
            adjList.put(from, edges);
            to.inboundCount++;
        }
        
        List<Vertice<T>> adjacents(Vertice<T> vertice) {
            return adjList.getOrDefault(vertice, new ArrayList<>());
        }
    }
    
    class Vertice<T> {
        T value;
        int id;
        int inboundCount = 0;
        
        Vertice(T value, int id) {
            this.value = value;
            this.id = id;
        }
    }
    
    public static void main(String[] args) {
        CourseOrder order = new CourseOrder();
        int[] o = order.findOrder(2, new int[][] { new int[] { 1, 0 } });
        System.out.println(o);
    }
}
