import java.util.*;

class ShortestWordEditPath {

  /*
  1. Build the graph
   loop thorugh words array and add edges if char diff == 1
   if at root or target remember these nodes  .. O(V^2 * S)
2. BFSHelper: Queue<Node>, HashSet<Node> visited .. O(1)
3. init 2 BFSHelper for start + target O(1)
4. 1 level traversal from start, 1 level traversal from end    .. each node once O (V + E)
    4.1 initial size, we dequeue size elments, check neighbours not in visited
        if in other: path found
        
     time: O(V^2 * S + E + V)
     space: O (V)
       graph: O(V)
       BFSHelper: O(V^2)
        */
  
	static int shortestWordEditPath(String source, String target, String[] words) {
    List<String> wordList = new ArrayList<>(Arrays.asList(words));
    if (!wordList.contains(source)) { 
      wordList.add(source); 
    }

    if (!wordList.contains(target)) return -1;

    Graph graph = buildGraph(source, target, wordList);
    
    BFSHelper startHelper = new BFSHelper();
    BFSHelper targetHelper = new BFSHelper();
    
    startHelper.addToQueue(graph.start);
    targetHelper.addToQueue(graph.target);

    startHelper.visited.add(graph.start);
    targetHelper.visited.add(graph.target);
    
    while (!startHelper.allChecked() || !targetHelper.allChecked()) { //O(V+E)
      int len = traverseLevel(startHelper, targetHelper);
      if (len > - 1) return len;
      if (startHelper.allChecked()) return -1;

      len = traverseLevel(targetHelper, startHelper);
      if (len > - 1) return len;
      if (targetHelper.allChecked()) return -1;
    }
    
    return -1;
	}
  
  /*
  4. 1 level traversal from start, 1 level traversal from end    .. each node once O (V + E)
    4.1 initial size, we dequeue size elments, check neighbours not in visited
        if in other: path found
  */
  static int traverseLevel(BFSHelper first, BFSHelper second) {
    int lenToCheck = first.queue.size();
    
    while (lenToCheck > 0) {
      Node node = first.queue.poll(); // poll from front of queue
      lenToCheck--;
      
      for (Node neighbour : node.neighbours) {
        if (second.visited.contains(neighbour)) {
          return node.equals(neighbour) ? 0 : node.pathLen + neighbour.pathLen + 1;
        }
        
        if (first.visited.contains(neighbour)) continue;
        
        neighbour.pathLen = node.pathLen + 1;
        first.visited.add(neighbour);
        first.queue.add(neighbour); // add to the end of queue
      }
    }
    
    return -1;
  }
  
  /*
    1. Build the graph
   loop thorugh words array and add edges if char diff == 1
   if at root or target remember these nodes  .. O(V^2)
   */
  static Graph buildGraph(String source, String target, List<String> words) {
    Graph graph = new Graph();
        
    Map<String, Node> nodes = new HashMap<>();
    
    for (int index = 0; index < words.size(); index++) {
      String word = words.get(index);
      Node node = nodes.getOrDefault(word, new Node(word));
      nodes.put(word, node);
      
      if (word.equals(source)) graph.start = node;
      if (word.equals(target)) graph.target = node;
      
      for (int innerIndex = index + 1; innerIndex < words.size(); innerIndex++) {
        //add edges
        String otherWord = words.get(innerIndex);
        int charDiff = 0;
        //int charLen = Math.min(word.length(), otherWord.length());
        if (word.length() != otherWord.length()) continue;
        for (int charIndex = 0; charIndex < word.length(); charIndex++) {
          if (word.charAt(charIndex) != otherWord.charAt(charIndex)) charDiff++;
        }
        //charDiff += Math.abs(word.length() - otherWord.length());
        if (charDiff == 1) {
          Node other = nodes.getOrDefault(otherWord, new Node(otherWord));
          node.addNeighbour(other);
          other.addNeighbour(node);
          nodes.put(otherWord, other);
        }
      }
    }
    
    graph.nodes = new ArrayList<>(nodes.values());
    
    return graph;
  }

	public static void main(String[] args) {
    // String source = "bit";
    // String target = "dog";
    // String[] words = new String[]{"but", "put", "big", "pot", "pog", "dog", "lot"};
    String source = "abc";
    String target = "ab";
    String[] words = new String[]{"abc", "ab"};
	  int editDistance = shortestWordEditPath( source, target, words );
    System.out.println("EditDistance: " + editDistance);
	}
  
  static class BFSHelper {
    LinkedList<Node> queue = new LinkedList<>();
    Set<Node> visited = new HashSet<>();
    
    boolean allChecked() {
      return queue.isEmpty();
    }
    
    void addToQueue(Node node) {
      queue.add(node);
    }
  }
  
  static class Graph {
    List<Node> nodes = new ArrayList<>();
    Node start;
    Node target;
  }
  
  static class Node {
    String word;
    int pathLen = 0;
    List<Node> neighbours = new ArrayList<>();
    
    Node(String word) {
      this.word = word;
    }
    
    void addNeighbour(Node neighbour) {
      neighbours.add(neighbour);
    }
    
    @Override
    public int hashCode() {
      return Objects.hash(pathLen, word);
    }
    
    @Override
    public boolean equals(Object other) {
      if (!(other instanceof Node)) return false;
      if (other == this) return true;
      Node oth = (Node) other;
      return oth.pathLen == this.pathLen && oth.word == this.word;
    }
  }

}

/*
  traverse level:
  
  1 --> 2   --> 4 --> 8 <--  9 <-- 7 <--   10
        3   --> 5
  
*/


// input: source:  String, target: String, String[] words

/*
source = "bit", target = "dog"
words = ["but", "put", "big", "pot", "pog", "dog", "lot"]

explanation: bit -> but -> put -> pot -> pog -> dog has 5 transitions

Node "but" -> "put", "bit"
Node "bit" -> "but", "big"

bidirectional BFS
BFS, visited set

1. Build the graph
   loop thorugh words array and add edges if char diff == 1
   if at root or target remember these nodes  .. O(V^2)
2. BFSHelper: Queue<Node>, HashSet<Node> visited .. O(1)
3. init 2 BFSHelper for start + target O(1)
4. 1 level traversal from start, 1 level traversal from end    .. each node once O (V + E)
    4.1 initial size, we dequeue size elments, check neighbours not in visited
        if in other: path found

shortest path

output: 5

Node: length, List<Node> neighbours

*/