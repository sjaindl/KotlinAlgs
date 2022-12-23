import java.util.*;

public class ValidAnagram {
    public boolean isAnagram(String s, String t) {
        if (s == null || t == null || s.length() != t.length()) {
            return false;
        }
        
        int[] counts = buildArray(s);
        
        for (char character : t.toCharArray()) {
            counts[character - 'a']--;
            if (counts[character - 'a'] < 0) return false;
        }
        
        return true;
    }
    
    private int[] buildArray(String s) {
        int[] counts = new int[26];
        
        for (char character : s.toCharArray()) {
            counts[character - 'a']++;
        }
        
        return counts;
    }
    
    public boolean isAnagramMap(String s, String t) {
        if (s == null || t == null || s.length() != t.length()) {
            return false;
        }
        
        Map<Character, Integer> counts = buildMap(s);
        
        for (char character : t.toCharArray()) {
            Integer charCount = counts.get(character);
            if (charCount == null) return false;
            
            if (charCount == 1) counts.remove(character);
            else counts.put(character, charCount - 1);
        }
        
        return counts.isEmpty();
    }
    
    private Map<Character, Integer> buildMap(String s) {
        Map<Character, Integer> counts = new HashMap<>();
        
        for (char character : s.toCharArray()) {
            Integer charCount = counts.getOrDefault(character, 0);
            counts.put(character, charCount + 1);
        }
        
        return counts;
    }
    
    public boolean isAnagramSorting(String s, String t) {
        if (s == null || t == null || s.length() != t.length()) {
            return false;
        }
        
        char[] first = s.toCharArray();
        char[] second = t.toCharArray();
        
        Arrays.sort(first);
        Arrays.sort(second);
        
        return Arrays.equals(first, second);
    }
}
