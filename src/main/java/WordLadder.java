import java.util.*;

public class WordLadder {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        UndirectedGraphNode source = buildGraph(beginWord, wordList);
        UndirectedGraphNode target = findNode(source, endWord);
        
        if (target == null) {
            return 0;
        }

        return findPathLen(source, target);
    }
    
    private int findPathLen(UndirectedGraphNode source, UndirectedGraphNode target) {
        // Bidirectional BFS
        LinkedList<UndirectedGraphNode> startQueue = new LinkedList<>();
        startQueue.add(source);
        Set<String> visitedStart = new HashSet<>();
        visitedStart.add(source.value);
        
        LinkedList<UndirectedGraphNode> endQueue = new LinkedList<>();
        endQueue.add(target);
        Set<String> visitedEnd = new HashSet<>();
        visitedEnd.add(target.value);
        
        int level = 1;
        
        while(!startQueue.isEmpty() && !endQueue.isEmpty()) {
            boolean found = traverseLevel(startQueue, visitedStart, visitedEnd);
            if (found) {
                return level;
            }
            
            level++;

            found = traverseLevel(endQueue, visitedEnd, visitedStart);
            if (found) {
                return level;
            }
            
            level++;
        }
        
        return 0;
    }

    private boolean traverseLevel(LinkedList<UndirectedGraphNode> queue, Set<String> visited, Set<String> otherVisited) {
        UndirectedGraphNode currentStart = queue.removeLast();

        System.out.println(currentStart.value);

        if (otherVisited.contains(currentStart.value)) {
            return true;
        }
        
        for (UndirectedGraphNode neighbour : currentStart.neighbours) {
            if (!visited.contains(neighbour.value)) {
                queue.add(neighbour);   
                visited.add(neighbour.value);
            }
        }

        return false;
    }
    
    private UndirectedGraphNode buildGraph(String beginWord, List<String> wordList) {
        UndirectedGraphNode root = new UndirectedGraphNode(beginWord);
        Map<String, UndirectedGraphNode> nodeMap = new HashMap<>();
        
        List<String> wordListToBuild = new ArrayList<>(wordList);
        wordListToBuild.add(beginWord);
        nodeMap.put(beginWord, root);
        
        for (int wordIndex = 0; wordIndex < wordListToBuild.size(); wordIndex++) {
            String word = wordListToBuild.get(wordIndex);
            for(String adjacent : findAdjacentWords(word, wordListToBuild, wordIndex)) {
                UndirectedGraphNode first = nodeMap.getOrDefault(word, new UndirectedGraphNode(word));
                nodeMap.put(word, first);

                UndirectedGraphNode second = nodeMap.getOrDefault(adjacent, new UndirectedGraphNode(adjacent));
                nodeMap.put(adjacent, second);
                
                first.addEdge(second);
                second.addEdge(first);
            }
        }
        
        return root;
    }

    private UndirectedGraphNode findNode(UndirectedGraphNode root, String value) {
        Set<String> marked = new HashSet<>();
        return findNode(root, value, marked);
    }
    
    private UndirectedGraphNode findNode(UndirectedGraphNode root, String value, Set<String> marked) {
        if (root.value.equals(value)) {
            return root;
        }

        for (UndirectedGraphNode neighbour : root.neighbours) {
            if (!marked.contains(neighbour.value)) {
                marked.add(neighbour.value);
                UndirectedGraphNode candidate = findNode(neighbour, value, marked);
                if (candidate != null) {
                    return candidate;
                }
            }
        }

        return null;
    }
    
    private List<String> findAdjacentWords(String word, List<String> words, int fromIndex) {
        List<String> adjacent = new ArrayList<>();
        for (int wordIndex = fromIndex; wordIndex < words.size(); wordIndex++) {
            String newWord = words.get(wordIndex);
            if (newWord.length() != word.length()) {
                continue;
            }
            int diff = 0;
            for (int charPos = 0; charPos < newWord.length(); charPos++) {
                if (newWord.charAt(charPos) != word.charAt(charPos)) {
                    diff++;
                }
                if (diff > 1) { 
                    break;
                }
            }
            
            if (diff == 1) {
                adjacent.add(newWord);
            }
        }
        
        return adjacent;
    }
    
    class UndirectedGraphNode {
        List<UndirectedGraphNode> neighbours = new ArrayList<>();
        String value;
        
        UndirectedGraphNode(String value) {
            this.value = value;
        }
        
        void addEdge(UndirectedGraphNode neighbour) {
            neighbours.add(neighbour);
        }
    }

    public static void main(String[] args) {
        String[] words = { "ted","tex","red","tax","tad","den","rex","pee" };
        List<String> wordList = new ArrayList<>(Arrays.asList(words));
        System.out.println(new WordLadder().ladderLength("red", "tax", wordList)); //should be instead of 5 (some minor bug in here..)
    }
}
