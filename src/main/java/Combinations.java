import java.util.*;

public class Combinations {
    public List<List<Integer>> combine(int n, int k) {
        //HashSet<Pair<Int, Int>> combined = new HashSet<>();
        List<List<Integer>> combined = new ArrayList<>();
        List<Integer> curCombi = new ArrayList<>();
        
        combineRec(combined, curCombi, k, 1, n);
        
        return combined;
    }
    
    private void combineRec(List<List<Integer>> combined, 
                                     List<Integer> curCombi, int remaining, int min, int max) {
        if (remaining == 0) {
            combined.add(new ArrayList<>(curCombi));
            return;
        }
        
        for (int curValue = min; curValue <= max; curValue++) {
            curCombi.add(curValue);
            combineRec(combined, curCombi, remaining - 1, curValue + 1, max);
            curCombi.remove(curCombi.size() - 1);
        }
    }

    public static void main(String[] args) {
        Combinations combi = new Combinations();
        System.out.println(combi.combine(4, 2));
    }
}
