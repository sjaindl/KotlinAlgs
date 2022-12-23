import java.util.*;

public class ValidParentheses {
    public boolean isValid(String s) {
        if (s == null || s.length() % 2 != 0) {
            return false;
        }
        
        if (s.length() == 0) {
            return true;
        }
        
        Map<Character, Character> braces = Map.of(
            '[', ']',
            '{', '}',
            '(', ')'
        );
        
        Stack<Character> openBracesStack = new Stack<>();
        for (Character character : s.toCharArray()) {
            if (braces.keySet().contains(character)) {
                openBracesStack.push(character);
            } else {
                if (openBracesStack.isEmpty()) {
                    return false;
                }
                Character openingBrace = openBracesStack.pop();
                if (!braces.get(openingBrace).equals(character)) {
                    return false;
                }
            }
        }
        
        return openBracesStack.isEmpty();
    }
}
