import java.util.*;

public class Kdistinctlen {
    
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        if (s == null || s.length() == 0 || k <= 0) return 0;
        
        int startPtr = 0; //0
        int endPtr = 0; //1
        int best = 0; //0
        Map<Character, Integer> charCounts = new HashMap<>();
        // {  a:1 }
        
        while (endPtr < s.length()) {
            Character charAtIndex = s.charAt(endPtr); //c
            if (charCounts.containsKey(charAtIndex)) {
                int count = charCounts.get(charAtIndex); //
                charCounts.put(charAtIndex, count + 1);
                endPtr++;
            } else {
                charCounts.put(charAtIndex, 1);
                endPtr++;
            }
            
            while (charCounts.keySet().size() > k) {
                Character startChar = s.charAt(startPtr); //e
                int count = charCounts.get(startChar); //1
                if (count == 1) {
                    charCounts.remove(startChar);
                } else {
                    charCounts.put(startChar, count - 1);
                }  
                startPtr++;
            }
            
            best = Math.max(best, endPtr - startPtr);
        }
        
        return best;
    }

    public static void main(String[] args) {
        Kdistinctlen kdistinctlen = new Kdistinctlen();
        System.out.println(kdistinctlen.lengthOfLongestSubstringKDistinct("aba", 1));
    }
}