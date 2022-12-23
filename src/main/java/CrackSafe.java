import java.util.*;

public class CrackSafe {
    
    public String crackSafe(int n, int k) {
        if (n < 1 || k < 1) return "";
        
        if (n == 1) {
            StringBuilder result = new StringBuilder();
            for (int pos = 0; pos < k; pos++) {
                result.append(pos);
            }
            return result.toString();
        }

        StringBuilder result = new StringBuilder();
        StringBuilder start = new StringBuilder();
        for (int pos = 0; pos < n - 1; pos++) {
            start.append(0);
        }

        if (k == 1) {
            start.append(0);
            return start.toString();
        }
        
        Set<String> visited = new HashSet<>();
        dfs(result, start.toString(), visited, k);
        result.append(start);
        
        return result.toString();
    }
    
    private void dfs(StringBuilder result, String currentPassword, Set<String> visited, int maxDigit) {
        for (int digit = 0; digit < maxDigit; digit++) {
            String newString = currentPassword + String.valueOf(digit);
            if (!visited.contains(newString)) {
                visited.add(newString);
                String lastDigits = newString.substring(1);
                dfs(result, lastDigits, visited, maxDigit);
                result.append(digit);
            }
        }
    }

    public static void main(String[] args) {
        CrackSafe cs = new CrackSafe();
        System.out.println(cs.crackSafe(2, 2));
        
    }
}