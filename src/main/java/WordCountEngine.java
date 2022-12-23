import java.io.*;
import java.util.*;

public class WordCountEngine {
    
    static String[][] wordCountEngine(String document) {
    if (document == null || document.length() == 0) return new String[0][];
    
    Map<String, Integer> firstIndices = new HashMap<>();
    
    String[] words = document.split(" ");
    String[] cleanedWords = clean(words);
    
    Map<String, Integer> counts = new HashMap<>();
    for(int index = 0; index < cleanedWords.length; index++) {
        String word = cleanedWords[index];
        addOrIncrement(counts, word);
        if (!firstIndices.containsKey(word)) {
        firstIndices.put(word, index);
        }
    }
    
    List<Wrapper> wrappers = new ArrayList();
    for (Map.Entry<String, Integer> entry : counts.entrySet()) {
        String word = entry.getKey();
        int count = entry.getValue();
        int index = firstIndices.get(word);
        
        Wrapper wrapper = new WordCountEngine().new Wrapper(index, count, word);
        wrappers.add(wrapper);
    }
    Collections.sort(wrappers, Collections.reverseOrder());
    
    // "3" -> "practice",...
    
    return convertToOutput(wrappers);
    }
    
    
    // sortedMap: "3" -> "practice",2 -> perfect, 1 -> "makes",  1 -> "youll" ...
    // firstIndices: "practice -> 0", "perfect" -> 2, "makes" -> 1, "youll" -> 4
    private static String[][] convertToOutput(List<Wrapper> wrappers) {
    String[][] output = new String[wrappers.size()][];
    
    int index = 0;
    for (Wrapper wrapper : wrappers) {
        String[] partial = new String[2];
        partial[0] = wrapper.word;
        partial[1] = String.valueOf(wrapper.count);
        output[index] = partial;
        index++;
    }
    
    return output;
    }
    
    private static void addOrIncrement(Map<String, Integer> counts, String word) {
    Integer count = counts.getOrDefault(word, 0);
    counts.put(word, count + 1);
    }
    
    private static String[] clean(String[] words) {
    String[] cleanedWords = new String[words.length];
    for (int index = 0; index < words.length; index++) {
        String word = words[index];
        String lowercased = word.toLowerCase().trim();
        StringBuilder sb = new StringBuilder();
        for (char letter : lowercased.toCharArray()) {
        if (letter >= 'a' && letter <= 'z') {
            sb.append(letter);
        }
        }
        
        if (sb.length() > 0) {
        //System.out.println(index + ": " + sb.toString());
        cleanedWords[index] = sb.toString();
        }
    }
    
    return cleanedWords;
    }
    
    class Wrapper implements Comparable<Wrapper> {
        int index;
        int count;
        String word;
        
        Wrapper(int index, int count, String word) {
            this.index = index;
            this.count = count;
            this.word = word;
        }
        
        @Override
        public int compareTo(Wrapper other) {
            if (this.count < other.count) return -1;
            if (this.count > other.count) return 1;
            if (this.index == other.index) return 0;
            return this.index < other.index ? 1 : -1;
        }
    }

    public static void main(String[] args) {
        WordCountEngine solution = new WordCountEngine();
    System.out.println(solution.wordCountEngine(
"Every book is a quotation; and every house is a quotation out of all forests, and mines, and stone quarries; and every man is a quotation from all his ancestors. "));
    }

}
    
    //just lowercase
    /*
      sort output first by nr. of occcurences, then by first index/occ.
      26 characters (EN alphabet)
      (hint: double spaces maybe..)
      LinkedHashMap<String, Integer>  -> { "practice" : 3 } .. unordered
      
      1. edge case checks O(1)
      2. Split document into String[] (words) by blanks   O(N)
      3. Iterate over each char and lowercase + check if >= 'a' && <= 'z', if so include  O(N)
      "[practice, makes, perfect, youll, only,..
        get, perfect, by, practice, just, practice]"
      4. Iterate over new array and put words as key into LinkedHashMap 
      5. convert LinkedHashMap to String[[]]
      O(N) time + space
      
    */