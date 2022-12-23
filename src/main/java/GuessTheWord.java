import java.util.*;

public class GuessTheWord {
    
    class Master {
        private String pw;

        Master(String pw) {
            this.pw = pw;
        }

        public int guess(String word) {
            return similarityCount(pw, word);
        }

        private Integer similarityCount(String first, String second) {
            Integer simCount = 0;
            for (int index = 0; index < first.length(); index++) {
                if (first.charAt(index) == second.charAt(index)) simCount++;
            }
            return simCount;
        }
    }

/**
 * // This is the Master's API interface.
 * // You should not implement it, or speculate about its implementation
 * interface Master {
 *     public int guess(String word) {}
 * }
 */

/*
"acckzz"
["acckzz","ccbazz","eiowzz","abcczz"]
10
*/
    public void findSecretWord(String[] wordlist, Master master) {
        /*
        1. build similaryCounts (2 nested for loops with input strings), func simCount based on chars..
            O(N^2 / 2) runtime
        */
        
        // 
        Map<String, List<Target>> similarityCounts = buildSimilarityCounts(wordlist);
        
        /* 2. init possiblePasswords with all strings. O(N) */
        
        // { "acckzz","ccbazz","eiowzz","abcczz" }
        Set<String> possiblePasswords = initPasswords(wordlist);

        /*
        3. while (!possiblePasswords.isEmpty())
            3.1 Build maxSameCount PQ. O(N log N)
            3.2 retrieve word with max same count. O(log N)
            3.3 guess this word O(1)
            3.4 based on return value & similaryCounts: 
                    eliminate possible PW's OR return if match. O(N)
        */
        
        // { "acckzz","ccbazz","eiowzz","abcczz" }
        while (!possiblePasswords.isEmpty()) {
            PriorityQueue<Target> maxSameCounts 
                = buildMaxSameCounts(similarityCounts, possiblePasswords);
            
            Target candidate = maxSameCounts.poll();
            System.out.println(candidate.word);
            int result = master.guess(candidate.word);
            if (result == wordlist[0].length()) return; // pw cracked!
            
            for (Target target : similarityCounts.get(candidate.word)) {
                if (target.count != result) {
                    possiblePasswords.remove(target.word);
                }
            }
            possiblePasswords.remove(candidate.word);
        }
        
    }
    
    // ["acckzz","ccbazz","eiowzz","abcczz"]
    /*
     {
        { "acckzz" : [{ "ccbazz": 3 }] }, 
        { "ccbazz" : [ { "acckzz": 3 }] }, 
        { "eiowzz" : [] }, 
        { "abcczz" : [] }, 
        ...
     }
    
    */
    private Map<String, List<Target>> buildSimilarityCounts(String[] wordlist) {
        Map<String, List<Target>> similarityCounts = new HashMap<>();
        for (int indexOne = 0; indexOne < wordlist.length; indexOne++) { // 0
            for (int indexTwo = indexOne + 1; indexTwo < wordlist.length; indexTwo++) { // 1
                String first = wordlist[indexOne];  // "acckzz"
                String second = wordlist[indexTwo]; // "ccbazz"
                Integer simCount = similarityCount(first, second); // 3
                
                List<Target> firstTargets = similarityCounts.getOrDefault(first, new ArrayList<>());
                List<Target> secondTargets = similarityCounts.getOrDefault(second, new ArrayList<>());
                
                firstTargets.add(new Target(second, simCount)); // 
                secondTargets.add(new Target(first, simCount)); // 
                
                similarityCounts.put(first, firstTargets);
                similarityCounts.put(second, secondTargets);
            }
        }
        
        return similarityCounts;
    }
    
    private Integer similarityCount(String first, String second) {
        Integer simCount = 0;
        for (int index = 0; index < first.length(); index++) {
            if (first.charAt(index) == second.charAt(index)) simCount++;
        }
        return simCount;
    }
    
    private Set<String> initPasswords(String[] wordlist) {
        Set<String> passwords = new HashSet<>();
        for (String word : wordlist) {
            passwords.add(word);
        }
        return passwords;
    }
    
    private PriorityQueue<Target> buildMaxSameCounts(
        Map<String, List<Target>> similarityCounts, Set<String> possiblePasswords) {
        //PriorityQueue<Target> maxSameCounts = new PriorityQueue<>((t1, t2) -> t2.count - t1.count);
        PriorityQueue<Target> maxSameCounts = new PriorityQueue<>((t1, t2) -> t1.count - t2.count);
        
        for (String password : possiblePasswords) {
            Integer maxSame = 0;
            Map<Integer, Integer> counts = new HashMap<>();
            for (Target target : similarityCounts.get(password)) {
                Integer count = counts.getOrDefault(target.count, 0);
                counts.put(target.count, count + 1);
                maxSame = Math.max(maxSame, count + 1);
            }
            maxSameCounts.add(new Target(password, maxSame));
        }
        
        return maxSameCounts;
    }
    
    class Target {
        String word;
        Integer count;
        
        Target(String word, Integer count) {
            this.word = word;
            this.count = count;
        }
    }

    public static void main(String[] args) {
        GuessTheWord guessTheWord = new GuessTheWord();
        String pw =  "ccbazz";
        Master master = new GuessTheWord().new Master(pw);
        guessTheWord.findSecretWord(new String[] { "acckzz","ccbazz","eiowzz","abcczz" }, master);
    }
}

/*
    secret = "acckzz", wordlist = ["acckzz","ccbazz","eiowzz","abcczz"]
    
    similarity counts:
    "acckzz" to "ccbazz" = 3
    "acckzz" to "eiowzz" = 2
    "acckzz" to "abcczz" = 4
    max same: 1
    
    "ccbazz" to "acckzz" = 3
    "ccbazz" to "eiowzz" = 2
    "ccbazz" to "abcczz" = 2
    max same: 2
    
    "eiowzz" to "acckzz" = 2
    "eiowzz" to "ccbazz" = 2
    "eiowzz" to "abcczz" = 2
    max same: 3
    
    "abcczz" to "eiowzz" = 2
    "abcczz" to "ccbazz" = 2
    "abcczz" to "acckzz" = 4
    max same: 2
    
    secret = "eiowzz"
    start with string with lowest max same count to guess.
    1. guess "acckzz" -> 2
        -> lookup strings with 2-count -> ["eiowzz"]
    2. guess "eiowzz" -> 6
        -> true. done.
    
    - What data structures to use?
        -> set of remaining possibilities.. Set<String> possiblePasswords; 
            intially all strings.
            remove invalid ones after each guess, based on count.
        -> similaryCounts: HashMap<String, Target>
           class Target: String (word) + Integer (count)
        -> maxSameCount: MaxPriorityQueue<Target>

    - Recursive/Iterative relationship
        
    - Implementation    
        1. build similaryCounts (2 nested for loops with input strings), func simCount based on chars..
            O(N^2 / 2) runtime
        2. init possiblePasswords with all strings. O(N)
        3. while (!possiblePasswords.isEmpty())
            3.1 Build maxSameCount PQ. O(N log N)
            3.2 retrieve word with max same count. O(log N)
            3.3 guess this word O(1)
            3.4 based on return value & similaryCounts: 
                    eliminate possiblePW's OR return if match. O(N)
*/