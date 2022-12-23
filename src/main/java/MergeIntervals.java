import java.util.*;

public class MergeIntervals {
    public int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return intervals;
        }
        
        Arrays.sort(intervals, new Comparator<int[]>(){
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] < o2[0]) {
                    return - 1;
                } else if (o1[0] == o2[0]) {
                    return 0;
                }
                return 1;
            }
        });

        List<int[]> combined = new ArrayList<>();

        Integer curMin = null;
        Integer curMax = null;
        for (int[] interval : intervals) {
            if (curMin == null) {
                curMin = interval[0];
                curMax = interval[1];
            } else if (interval[0] <= curMax) {
                curMax = Math.max(curMax, interval[1]); //merge
            } else {
                combined.add(new int[] { curMin, curMax });
                curMin = interval[0];
                curMax = Math.max(curMax, interval[1]);
            }
        }
        
        combined.add(new int[] { curMin, curMax });
        
        int[][] myArray = new int[combined.size()][];
        for (int index = 0; index < combined.size(); index++) {
            myArray[index] = combined.get(index);
        }
        
        return myArray;
    }
}
