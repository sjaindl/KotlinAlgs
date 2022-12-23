import java.util.*;

class LongestSubstring {
    public int lengthOfLongestSubstring(String s) {
        Set<Character> set = new HashSet<Character>();
        int bestCount = 0;
        int curCount = 0;
        int startIndex = 0;
        
        char[] chars = s.toCharArray();
        
        for(int index = 0; index < chars.length; index++) {
            if (!set.contains(chars[index])) {
                curCount += 1;
                set.add(chars[index]);
                if (curCount > bestCount) {
                    bestCount = curCount;
                }
            } else {
                while (chars[startIndex] != chars[index]) {
                    set.remove(chars[startIndex]);
                    startIndex++;
                    curCount--;
                }
                
                startIndex++;
            }
        }
        
        return bestCount;
    }
}
