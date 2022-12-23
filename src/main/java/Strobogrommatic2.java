import java.util.*;

class Strobogrammatic2 {    
    
    private Map<Character, Character> flippedEquivalents = 
        Map.of('6', '9', '9', '6', '0', '0', '1', '1', '8', '8');
    
    public boolean isStrobogrammatic(String num) {
        if (num == null || num.length() == 0) return false;
        
        int front = 0;
        int back = num.length() - 1;
        
        while (front <= back) {
            Character frontChar = num.charAt(front);
            Character backChar = num.charAt(back);
            Character expectedFlipped = flippedEquivalents.get(frontChar);
            if (expectedFlipped == null || !expectedFlipped.equals(backChar)) {
                return false;
            }

            front++;
            back--;
        }
        
        return true;
    }
    

    public static void main(String[] args) {
        System.out.println(new Strobogrammatic2().isStrobogrammatic("9696"));
    }
}
