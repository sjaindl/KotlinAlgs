import java.util.*;

public class WordSquares {


    // Backtracking solution: 
    /*
        https://leetcode.com/explore/learn/card/recursion-ii/472/backtracking/2654/
        https://leetcode.com/problems/word-squares/solution/
    */
    public List<List<String>> wordSquares(String[] words) {
        if (words.length == 0) {
            return new ArrayList<>();
        }

        //squares are symmetric (form horizontal + vertical line)
        List<List<String>> results = new ArrayList<>();
        Trie trie = buildTrie(words);
        wordSquaresBacktrack(words, new LinkedList<>(), trie, results);

        return results;
    }

    private void wordSquaresBacktrack(String[] allWords, List<String> currentWords, Trie trie, List<List<String>> results) {
        if (allWords[0].length() == currentWords.size()) {
            //valid word found!
            results.add(new LinkedList<>(currentWords));
            return;
        }
        
        int step = currentWords.size();
        StringBuilder prefix = new StringBuilder();
        for (int pos = 0; pos < step; pos++) {
            prefix.append(currentWords.get(pos).charAt(step));
        }

        List<String> possibleWords = wordsWithPrefix(prefix.toString(), trie);

        for (String word: possibleWords) {
            currentWords.add(word);
            wordSquaresBacktrack(allWords, currentWords, trie, results);
            currentWords.remove(word);
        }
    }

    private List<String> wordsWithPrefix(String prefix, Trie trie) {
        TrieNode current = trie.root;

        for (char character : prefix.toCharArray()) {
            if (current == null) {
                return new ArrayList<>(); //no such words exist
            }

            current = current.children.get(character);
        }

        if (current == null) {
            return new ArrayList<>(); //no such words exist
        }

        return current.childNodesWithPrefix(prefix);
    }
    
    private Trie buildTrie(String[] words) {
        Trie trie = new Trie(new TrieNode(false, ""));
        for(String word : words) {
            trie.insert(word);
        }
        
        return trie;
    }

    public class TrieNode {
        Map<Character, TrieNode> children;
        boolean terminates;
        String value;
        
        public TrieNode(boolean terminates, String value) {
            children = new HashMap<>();
            this.terminates = terminates;
            this.value = value;
        }

        public List<String> childNodesWithPrefix(String prefix) {
            List<String> nodes = new ArrayList<>();

            if (terminates) {
                nodes.add(value);
                return nodes;
            }
            
            for (char character : children.keySet()) {
                nodes.addAll(children.get(character).childNodesWithPrefix(prefix + character));
            }

            return nodes;
        }
    }
    
    public class Trie {
        TrieNode root;
        
        public Trie(TrieNode root) {
            this.root = root;
        }
        
        public void insert(String word) {
            TrieNode current = root;
            
            for (char character : word.toCharArray()) {
                if (current.children.get(character) == null) {
                    current.children.put(character, new TrieNode(false, current.value + character));
                }
                current = current.children.get(character);
            }
            
            current.terminates = true;
        }
        
        public boolean search(String word) {
            TrieNode current = root;
            
            for (char character: word.toCharArray()) {
                if (current.children.get(character) == null) {
                    return false;
                }
                current = current.children.get(character);
            }
            
            if (current == null || !current.terminates) {
                return false;
            }
            
            return true;
        }
        
    }
    
    public static void main(String[] args) {
        WordSquares sum = new WordSquares();
        //String[] nums = {"area","lead","wall","lady","ball"};
        // String[] nums = {"ab", "ba"};

        String[] nums = { "abaa","aaab","baaa","aaba" };
        System.out.println(sum.wordSquares(nums));
    }
}

