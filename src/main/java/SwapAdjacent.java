import java.util.*;

public class SwapAdjacent {

    public boolean canTransform(String start, String end) {
        if (start == null || end == null || start.length() != end.length()) {
            return false;
        }
        
        if (!start.replace("X", "").equals(end.replace("X", ""))) {
            return false;
        }
        
        int endIndex = 0;
        for (int index = 0; index < start.length(); index++) {
            if (start.charAt(index) == 'L') {
                while (endIndex < end.length() && end.charAt(endIndex) != 'L') {
                    endIndex++;
                }
                if (endIndex > index) return false;
                endIndex++;
            }
        }
        
        endIndex = 0;
        for (int index = 0; index < start.length(); index++) {
            if (start.charAt(index) == 'R') {
                while (endIndex < end.length() && end.charAt(endIndex) != 'R') {
                    endIndex++;
                }
                if (endIndex < index) return false;
                endIndex++;
            }
        }
        
        return true;
    }

    private Map<String, String> replaceMap = Map.of("XL", "LX", "RX", "XR");
    
    public boolean canTransform2(String start, String end) {
        if (start == null || end == null || start.length() != end.length()) {
            return false;
        }
        
        char[] startChars = start.toCharArray();
        char[] endChars = end.toCharArray();
        int index = 0;
        int len = startChars.length;
        
        while (index < len) {
            Character startChar = startChars[index];
            Character endChar = endChars[index];
            
            // if (startChar.equals(endChar)) {
            //     index++;
            // } else {
                if (index == len - 1) {
                    checkLeft(startChars, endChars, index);
                    break;
                }
                
                /*
                "XXXXXLXXXX"
                "LXXXXXXXXX"
                
                "XXXXLXXXXX"
                "LXXXXXXXXX"
                */
                
                String key = String.valueOf(startChar) + String.valueOf(startChars[index + 1]);
                
                String mapped = replaceMap.get(key);
                if (mapped != null && !(startChars[index] == mapped.charAt(0) && startChars[index + 1] == mapped.charAt(1))) {
                    startChars[index] = mapped.charAt(0);
                    startChars[index + 1] = mapped.charAt(1);
                    
                    checkLeft(startChars, endChars, index);
                }
                
                index += 1;
            // }
        }
        
        return Arrays.equals(startChars, endChars);
    }
    
    private void checkLeft(char[] startChars, char[] endChars, int from) {
        for (int index = from; index > 0; index--) {
            String key = String.valueOf(startChars[index - 1]) + String.valueOf(startChars[index]);

            //"LXXXXXXXXX"
            //"LXXXXXXXXX"
            String mapped = replaceMap.get(key);
            if (mapped != null && !(startChars[index - 1] == mapped.charAt(0) && startChars[index] == mapped.charAt(1))) {
                startChars[index - 1] = mapped.charAt(0);
                startChars[index] = mapped.charAt(1);
            } else {
                break;
            }
        }
    }
        
    // start = "RXXLRXRXL", end = "XRLXXRRLX"
    public boolean canTransformOld(String start, String end) {
        if (start == null || end == null || start.length() != end.length()) {
            return false;
        }
        
        char[] startChars = start.toCharArray();
        char[] endChars = end.toCharArray();
        int index = 0;
        int len = startChars.length;
        while (index < len) {
            Character startChar = startChars[index]; // R
            Character endChar = endChars[index]; // X
            
            if (startChar.equals(endChar)) {
                index++;
            } else {
                if (index == len - 1) {
                    return false; // only 1 (wrong) char left
                }
                
                String key = String.valueOf(startChar) 
                    + String.valueOf(startChars[index + 1]); //RX
                
                String value = String.valueOf(endChar)
                    + String.valueOf(endChars[index + 1]); //XR
                
                String mapped = replaceMap.get(key); //XR
                if (mapped == null || !mapped.equals(value)) {
                    return false;
                }
                
                startChars[index] = mapped.charAt(0);
                startChars[index + 1] = mapped.charAt(1);
                
                index += 1;
            }
        }
        
        return true;
    }

    public static void main(String[] args) {
        SwapAdjacent sa = new SwapAdjacent();

        System.out.println(sa.canTransform("XXXXXLXXXX", "LXXXXXXXXX")); 
       System.out.println(sa.canTransform("LXXXXXXXXX", "XXXXXLXXXX")); 
       System.out.println( sa.canTransform("XXXXXLXXXX", "LXXXXXXXXX"));
       System.out.println(sa.canTransform("RXXLRXRXL", "XRLXXRRLX"));
    }
}
