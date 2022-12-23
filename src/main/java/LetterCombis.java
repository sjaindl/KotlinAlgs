import java.util.*;

public class LetterCombis {
    
    Map<Character, List<Character>> charMapping = Map.of(
        '2', List.of('a', 'b', 'c'),
        '3', List.of('d', 'e', 'f'),
        '4', List.of('g', 'h', 'i'),
        '5', List.of('j', 'k', 'l'),
        '6', List.of('m', 'n', 'o'),
        '7', List.of('p', 'q', 'r', 's'),
        '8', List.of('t', 'u', 'v'),
        '9', List.of('w', 'x', 'y', 'z')
    );
    
    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        if (digits == null || digits.length() == 0) return result;
        
        // "23"
        List<StringBuilder> partial = new ArrayList<>(); //[d,e,f]
        for (Integer index = digits.length() - 1; index >= 0; index--) { // 1
            List<StringBuilder> newPartial = new ArrayList<>(); //[da,ea,fa,db,eb,ec,fa,fb,fc]
            Character number = digits.charAt(index);
            for (Character character : charMapping.get(number)) { //2 (abc)
                if (index == digits.length() - 1) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(character);
                    newPartial.add(sb);
                } else {
                    for (StringBuilder sb : partial) {
                        StringBuilder newSb = new StringBuilder(sb);
                        newSb.append(character);
                        newPartial.add(newSb);
                    }
                }
            }
            partial = newPartial;
        }
        
        for (StringBuilder sb : partial) {
            result.add(sb.reverse().toString());
        }
        
        return result;
    }
    
    /*
        check edge cases: digits empty, null.. 
        call rec. function
        guaranteed only 2-9 digits.
        rec. function: int index, StringBuilder cur, String digits -> return String[]
            BC: last index - build String, return as 1-element array
            for each char at digits[index]: append to SB, recurse, remove from SB (backtrack)
                append to result String[]
            return result
            
        1. mapping from char to digits. HashMap<Character, List<Character>>
        O(D*4^D) runtime BF - with backtracking ~O(4^D) 
        
        2. top-down dynamic programming: cache String[] after index
        
        3. bottom-up dyn. programming: start at last index. build String[] upwards.
        
        4. iteratively: start at last index, for index n-1 to 0 .. prepend char at every possible pos of result array (SB array?). .. double for loop: for index( .. for stringB in result { prepend })
        for sb in result (reversed): create string, append to return array.
    */
    
}