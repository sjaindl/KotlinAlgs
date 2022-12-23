import java.io.*;
import java.util.*;
import javafx.util.Pair;

public class PairsWithSpecificDiff {

  // arr = [0, -1, -2, 2, 1], k = 1
  static int[][] findPairsWithGivenDifference(int[] arr, int k) {
      Set<Integer> values = new HashSet<>();

      List<Pair<Integer, Integer>> outputList = new ArrayList<>();
    
      // 1. pass
      for (int index = 0; index < arr.length; index++) {
        values.add(arr[index]); // values = [0, -1, -2, 2, 1]
      }
    
      // 2. pass
      for (int index = 0; index < arr.length; index++) {
        Integer searched = arr[index] + k; // 1
        if (values.contains(searched)) {
          // [[1,0],[0,-1],[-1,-2],[2,1]]
          outputList.add(new Pair<Integer, Integer>(searched, arr[index]));
        }
      }
    
      int[][] output = new int[outputList.size()][2];
      for (int index = 0; index < outputList.size(); index++) {
        output[index][0] = outputList.get(index).getKey();
        output[index][1] = outputList.get(index).getValue();
      }
    
      return output;
  }

  public static void main(String[] args) {
    
  }

}