public class LongestTwoSubStrDistinct {
    public int lengthOfLongestSubstringTwoDistinct(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        int max = 0;
        SlideChar first = null;
        SlideChar second = null;
        
        for (int index = 0; index < s.length(); index++) { //"abcabcabc"  a
            char character = s.charAt(index);
            if (first == null) {
                first = new SlideChar(index, index, character);
                max = Math.max(max, 1);
            } else if (first != null && first.character != character && second == null) {
                second = new SlideChar(index, index, character);
                max = Math.max(max, maxSpan(first, second));
            } else if (first != null && first.character == character) {
                first.right = index;
                max = Math.max(max, maxSpan(first, second));
            } else if (second != null && second.character == character) {
                second.right = index;
                max = Math.max(max, maxSpan(first, second));
            } else {
                //reset
                boolean firstWasLastSeen = first.right > second.right;
                if (!firstWasLastSeen) {
                    SlideChar temp = first;
                    first = second;
                    second = temp;
                }
                
                first.left = second.right + 1;
                second = new SlideChar(index, index, character);
            }
        }
        
        return max;
    }
    
    private int maxSpan(SlideChar first, SlideChar second) {
        if (first == null && second == null) {
            return 0;
        } else if (second == null) {
            return first.right - first.left + 1;
        }
        
        int minIndex = first.left < second.left ? first.left : second.left;
        int maxIndex = first.right > second.right ? first.right : second.right;
        
        return maxIndex - minIndex + 1;
    }
    
    class SlideChar {
        int left = -1;
        int right = -1;
        char character = '\0';
        
        SlideChar(int left, int right, char character) {
            this.left = left;
            this.right = right;
            this.character = character;
        }
    }
    
    
    public int lengthOfLongestSubstringTwoDistinctSlow(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        
        int cur = 0;
        int max = 1;
        char first = '\0';
        char second = '\0';
        for (int index = 0; index < s.length(); index++) {
            first = s.charAt(index);
            cur = 1;
            for (int nextIndex = index + 1; nextIndex < s.length(); nextIndex++) {
                if (first != s.charAt(nextIndex) && second == '\0') {
                    second = s.charAt(nextIndex);
                } else if (second != '\0' && s.charAt(nextIndex) != first && s.charAt(nextIndex) != second) {
                    cur = 0;
                    first = '\0';
                    second = '\0';
                    break;
                }

                cur++;
                max = Math.max(max, cur);
            }
        }
        
        return max;
    }
}
