import java.util.*;

class AutocompleteSystem {  
    TrieNode root;
    
    private static final char TERMINATION_CHARACTER = '#';
    
    private TrieNode curInputNode;
    private StringBuilder curInput = new StringBuilder();

    public AutocompleteSystem(String[] sentences, int[] times) {
        root = buildTrie(sentences, times);
        curInputNode = root;
    }
    
    public List<String> input(char c) {
        if (c == TERMINATION_CHARACTER) {
            // base case
            curInputNode.sentence = curInput.toString();
            curInputNode.count++;
            curInputNode.terminates = true;
            
            curInput = new StringBuilder();
            curInputNode = root;
            
            return new ArrayList<>();
        } else {
            Character key = c;
            TrieNode sub = curInputNode.children.getOrDefault(key, new TrieNode(key));
            curInputNode.children.put(key, sub);
            curInputNode = sub;
            
            curInput.append(c);
            
            return buildTopSentences(sub);
        }
    }
    
    private List<String> buildTopSentences(TrieNode node) {
        List<TrieNode> topNodes = node.subNodes();
        Collections.sort(topNodes);
        
        List<String> topSentences = new ArrayList<>();
        for (int index = 0; index < Math.min(3, topNodes.size()); index++) {
            topSentences.add(topNodes.get(index).sentence);
        }
        
        return topSentences;
    }
    
    private TrieNode buildTrie(String[] sentences, int[] times) {
        TrieNode trie = new TrieNode();
        
        for (int index = 0; index < sentences.length; index++) {
            trie.insert(sentences[index], times[index], 0);
        }

        return trie;
    }
    
    class TrieNode implements Comparable<TrieNode> {
        boolean terminates;
        String sentence;
        int count;
        char character;
        Map<Character, TrieNode> children;
        
        TrieNode(boolean terminates, String sentence, int count, char character) {
            this.terminates = terminates;
            this.sentence = sentence;
            this.count = count;
            this.character = character;
            
            children = new HashMap<>();
        }
        
        TrieNode(char character) {
            this.character = character;
            children = new HashMap<>();
        }
        
        TrieNode() {
            children = new HashMap<>();
        }
        
        List<TrieNode> subNodes() {
            List<TrieNode> sub = new ArrayList<>();
            subNodesRec(sub);
            return sub;
        }
        
        private void subNodesRec(List<TrieNode> nodes) {
            if (terminates) {
                nodes.add(this);
            }
            
            for (TrieNode node : children.values()) {
                node.subNodesRec(nodes);
            }
        }
        
        public void insert(String sentence, int count, int charIndex) {
            Character charAtIndex = sentence.charAt(charIndex);
            TrieNode node = children.getOrDefault(charAtIndex, 
                                                  new TrieNode(sentence.charAt(charIndex)));
            
            if (charIndex == sentence.length() - 1) {
                // base case
                node.terminates = true;
                node.count += count;
                node.sentence = sentence;
                
                children.put(charAtIndex, node);
            } else {
                children.put(charAtIndex, node);
                node.insert(sentence, count, charIndex + 1);
            }
        }
        
        @Override
        public int compareTo(TrieNode other) {
            if (this.count > other.count) return -1;
            else if (this.count < other.count) return 1;
            
            return this.sentence.compareTo(other.sentence);
        }
    }

    public static void main(String[] args) {
        String[] sentences = new String[] {
            //"i love you","island","iroman","i love leetcode"
            "abc", "abbc", "a"
        };

        int[] times = new int[] {
            //5,3,2,2
            3,3,3
        };

        AutocompleteSystem system = new AutocompleteSystem(sentences, times);

        System.out.println(system.input('b'));
        System.out.println(system.input('c'));
        System.out.println(system.input('#'));

        System.out.println(system.input('b'));
        System.out.println(system.input('c'));
        System.out.println(system.input('#'));
        
        System.out.println(system.input('a'));
        System.out.println(system.input('b'));
        System.out.println(system.input('c'));
        System.out.println(system.input('#'));

        System.out.println(system.input('a'));
        System.out.println(system.input('b'));
        System.out.println(system.input('c'));
        System.out.println(system.input('#'));

        // System.out.println(system.input('i'));
        // System.out.println(system.input(' '));
        // System.out.println(system.input('a'));
        // System.out.println(system.input('#'));
    }
}

/**
 * Your AutocompleteSystem object will be instantiated and called as such:
 * AutocompleteSystem obj = new AutocompleteSystem(sentences, times);
 * List<String> param_1 = obj.input(c);
 */

/*
    String[] sentences  -> existing sentences to autocomplete
    int[] times         -> occ. of each sentence
    
    .. can add new sentences or incr. count via input terminated by '#'
    chars: 'a' - 'z', ' ' & '#'
    
    sort by:
        1. priority desc.
        2. string asc. (alphabetically)
    
    "i love" : 1 time
    "i love you" : 5 times
    "island" : 3 times
    "ironman" : 2 times
    "i love leetcode" : 2 times
    
    Trie
        root
        i
    ' ', 's', 'r'
    
    TrieNode: boolean terminates, String sentence, int count, children: HashMap<Character, TrieNode>
    
    TODO:
        private TrieNode root in AutocompleteSystem
        class TrieNode + TrieNode Comparable (count + sentence)
    constructor: 
        -> buildTrie(), root .. pass down chars of sentence as SB .. O(N * C), where C = max chars of string, N = # sentences
    
    private SB + TrieNode curInput
    input: if new char -> progress SB + curInput
           if '#' -> terminate TN
           always: return top <= 3 using comparable
    O(N*C) 
*/