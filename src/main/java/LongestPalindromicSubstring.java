public class LongestPalindromicSubstring {
    public String longestPalindrome(String s) {
        if (s == null) {
            return null;
        }
        if (s.length() <= 1) {
            return s;
        }
        
        boolean checked[][] = new boolean[s.length()][s.length()];

        return longestPalindrome(s, checked);
    }
    
    private String longestPalindrome(String string, boolean[][] checked) {
        int lastValidLow = -1;
        int lastValidHigh = -1;

        for (int size = 1; size <= string.length(); size++) {
            for (int low = 0; low + size <= string.length(); low++) {
                int high = low + size - 1;
                if (size <= 2) {
                    boolean isPalindrome = isPalindrome(string, low, high);
                    checked[low][high] = isPalindrome;

                    if (isPalindrome) {
                        lastValidLow = low;
                        lastValidHigh = high;
                    }
                } else {
                    boolean innerIsPalindrome = checked[low + 1][high - 1];
                    
                    boolean outerIsPalindrome = innerIsPalindrome && string.charAt(low) == string.charAt(high);
                    checked[low][high] = outerIsPalindrome;

                    if (outerIsPalindrome) {
                        lastValidLow = low;
                        lastValidHigh = high;
                    }
                }
            }
        }

        if (lastValidLow != -1 && lastValidHigh != -1) {
            return string.substring(lastValidLow, lastValidHigh + 1);
        }

        return null;
    }
    
    private boolean isPalindrome(String s, int low, int high) {
        while (high > low) {
            if (s.charAt(low) != s.charAt(high)) {
                return false;
            }
            
            low++;
            high--;
        }
        
        return true;
    }

    public static void main(String[] args) {
        LongestPalindromicSubstring sum = new LongestPalindromicSubstring();

        System.out.println(sum.longestPalindrome("xxabbac"));
        System.out.println(sum.longestPalindrome("jhgtrclvzumufurdemsogfkpzcwgyepdwucnxrsubrxadnenhvjyglxnhowncsubvdtftccomjufwhjupcuuvelblcdnuchuppqpcujernplvmombpdttfjowcujvxknzbwmdedjydxvwykbbamfnsyzcozlixdgoliddoejurusnrcdbqkfdxsoxxzlhgyiprujvvwgqlzredkwahexewlnvqcwfyahjpeiucnhsdhnxtgizgpqphunlgikogmsffexaeftzhblpdxrxgsmeascmqngmwbotycbjmwrngemxpfakrwcdndanouyhnnrygvntrhcuxgvpgjafijlrewfhqrguwhdepwlxvrakyqgstoyruyzohlvvdhvqmzdsnbtlwctetwyrhhktkhhobsojiyuydknvtxmjewvssegrtmshxuvzcbrabntjqulxkjazrsgbpqnrsxqflvbvzywzetrmoydodrrhnhdzlajzvnkrcylkfmsdode"));
    }
}