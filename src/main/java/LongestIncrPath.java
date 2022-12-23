import java.util.*;

public class LongestIncrPath {
    public int longestIncreasingPath(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;    
        }
        
        int longest = 0;
        HashMap<Integer, Integer> computed = new HashMap<>();
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                HashSet<Integer> marked = new HashSet<>();
                int longestAt = dfs(matrix, row, col, Integer.MIN_VALUE, 0, computed, marked);
                System.out.println("[" + row + "][" + col + "] = " + longestAt );
                longest = Math.max(longest, longestAt);
            }
        }
        
        return longest;
    }
    
    private int dfs(int[][] matrix, int row, int col, int max,
                    int curCount, HashMap<Integer, Integer> computed, HashSet<Integer> marked) {
        Integer flat = flatIndex(matrix, row, col);

        if (!isInBounds(matrix, row, col) || marked.contains(flat) || matrix[row][col] <= max) {
            return curCount;
        }
        
        Integer comp = computed.get(flat);
        if (comp != null) {
            return curCount + comp;
        }
        
        int[][] neighbours = {
            { -1, 0 }, { 1, 0 }, { 0, 1 }, { 0, -1 }
        };
        
        marked.add(flat);
        int subCount = 0;
        for (int[] neighbour : neighbours) {
            subCount = Math.max(subCount, dfs(matrix, row + neighbour[0], col + neighbour[1],
                                              matrix[row][col], 1, computed, marked));
        }
        marked.remove(flat);
        
        computed.put(flat, subCount);
        //System.out.println(flat + ": " + (curCount + subCount));
                              
        return curCount + subCount;
    }
    
    private boolean isInBounds(int[][] matrix, int row, int col) {
        return row >= 0 && col >= 0 && row < matrix.length && col < matrix[0].length;
    }
    
    private int flatIndex(int[][] matrix, int row, int col) {
        int cols = matrix[0].length;
        return row * cols + col;
    }

    public static void main(String[] args) {
        LongestIncrPath path = new LongestIncrPath();
        int[][] matrix = new int[][] { {9,9,4}, {6,6,8}, {2,1,1} };
        //int[][] matrix = new int[][] {{ 3,4,5}, {3,2,6},{2,2,1}};
        int p = path.longestIncreasingPath(matrix);
        System.out.println(p);
    }
}