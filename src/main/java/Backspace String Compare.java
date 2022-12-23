class BackSpaceStringCompare {
    private static final char BACKSPACE = '#';

    public boolean backspaceCompareOptimized(String S, String T) {
        BackwardsResult first = new BackwardsResult(S.toCharArray());
        BackwardsResult second = new BackwardsResult(T.toCharArray());
        //"ab#c"
        //"ad#c"
        while (first.index >= 0 || second.index >= 0) {
            first.proceedToFront();
            second.proceedToFront();
            
            System.out.println("first: " + first.character);
            System.out.println("second: " + second.character);
            
            if (first.character != second.character) {
                return false;
            }
        }
        
        return true;
    }
    
    class BackwardsResult {
        char[] chars;
        int index;
        int delCount;
        char character;
        
        BackwardsResult(char[] chars) {
            this.chars = chars;
            this.index = chars.length - 1;
            this.delCount = 0;
            this.character = '\0';
        }
        
        void proceedToFront() {
            character = '\0';
            
            while (character == '\0' && index >= 0) {
                if (chars[index] == BACKSPACE) {
                    delCount++;
                } else {
                    if (delCount > 0) {
                        delCount--;
                    } else {
                        character = chars[index];
                    }
                }
                index--;
            }
        }
    }

    
    public boolean backspaceCompare(String S, String T) {
        String first = buildString(S);
        String second = buildString(T);
        return first.equals(second);
    }
    
    private String buildString(String input) {
        StringBuilder builder = new StringBuilder();
        for (char character : input.toCharArray()) {
            if (character == BACKSPACE) {
                int len = builder.length();
                if (len > 0) {
                    builder.deleteCharAt(len - 1);
                }
            } else {
                builder.append(character);
            }
        }
        
        return builder.toString();
    }
}