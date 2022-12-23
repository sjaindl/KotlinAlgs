import java.util.*;

public class DecodeString {
    public String decodeString(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        //"3[a2[c]]"
        Stack<Integer> numStack = new Stack<>(); //
        Stack<Character> exprStack = new Stack<>(); //
        
        StringBuilder prevDigit = new StringBuilder(); //
        for (char character : s.toCharArray()) {
            if (Character.isDigit(character)) {
                prevDigit.append(character);
                continue;
            }
            
            if (prevDigit.length() > 0) {
                numStack.push(Integer.valueOf(prevDigit.toString()));
                prevDigit = new StringBuilder();
            }
            
            if (character != ']') {
                exprStack.push(character);
            } else {
                StringBuilder exprBuilder = new StringBuilder(); //a,c,c
                while (!exprStack.isEmpty() && exprStack.peek() != '[') {
                    exprBuilder.append(exprStack.pop());
                }
                if (exprStack.peek() == '[') {
                    exprStack.pop();
                }
                String expr = exprBuilder.reverse().toString(); //3
                Integer multiplier = numStack.pop(); //
                for (int run = 0; run < multiplier; run++) {
                    for (char exprCharacter : expr.toCharArray()) {
                        exprStack.push(exprCharacter); //a,c,c,a,c,c,a,c,c
                    }
                }
            }
        }
        
        return convertToString(exprStack);
    }
    
    private String convertToString(Stack<Character> exprStack) {
        char[] chars = new char[exprStack.size()];
        int index = exprStack.size() - 1;
        while (!exprStack.isEmpty()) {
            chars[index] = exprStack.pop();
            index--;
        }
        
        return new String(chars); //a,c,c,a,c,c,a,c,c
    }
}


//"3[a2[c]]" -> accaccacc
/*
put nums on num stack
put expr after [ on expr stack
on ] calc expr from num/expr & put on expr stack

    num stack 3,2
    expr stack [a,[,c
    
    ] ->
    num stack 3
    expr stack [a,cc
    ] ->
    num stack 
    expr stack accaccacc

*/

//"a3[a2[c]]" -> accaccacc
/*
put nums on num stack
put expr after [ on expr stack
on ] calc expr from num/expr & put on expr stack

    num stack 3,2
    expr stack a[a,[,c
    
    ] ->
    num stack 3
    expr stack a[a,cc
    ] ->
    num stack 
    expr stack aaccaccacc

output: char[expr stack size] -> insert from len-1 to 0

*/
