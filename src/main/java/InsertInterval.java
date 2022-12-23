import java.util.Arrays;

public class InsertInterval {
    enum Overlap {
        INSIDE,
        AROUND,
        LEFT_OVERLAP,
        RIGHT_OVERLAP,
        LEFT,
        RIGHT
    }
    
    public int[][] insert(int[][] intervals, int[] newInterval) {
        if (newInterval == null || newInterval.length == 0) {
            return intervals;
        }
        
        if (intervals == null || intervals.length == 0) {
            int[][] newIntervals = { newInterval };
            return newIntervals;
        }
        
        int low = 0;
        int high = intervals.length - 1;
        
        int left = low;
        int right = high;
        boolean overlaps = false;
        
        FindIndices:
        while (low <= high) {
            int center = low + (high - low) / 2;
            Overlap overlap = overlaps(intervals, newInterval, center);
            switch (overlap) {
                case INSIDE:
                    return intervals.clone(); //already included
                case LEFT:
                    high = center - 1;
                    right = Math.max(low, high);
                    break;
                case RIGHT:
                    low = center + 1;
                    left = Math.min(low, high);
                    break;
                case LEFT_OVERLAP:
                    overlaps = true;
                    right = center;
                    left = findLeftEnd(intervals, newInterval, low, center);
                    break FindIndices;
                case RIGHT_OVERLAP:
                overlaps = true;
                    left = center;
                    right = findRightEnd(intervals, newInterval, center, high);
                    break FindIndices;
                case AROUND:
                    overlaps = true;
                    left = findLeftEnd(intervals, newInterval, low, center - 1);
                    right = findRightEnd(intervals, newInterval, center + 1, high);
                    break FindIndices;
            }
        }
        
        return merge(intervals, newInterval, left, right, overlaps); 
    }
    
    private int[][] merge(int[][] intervals, int[] newInterval, int left, int right, boolean overlaps) {
        int mergedLen = overlaps ? intervals.length - (right - left) : intervals.length + 1;
        
        int[][] merged = new int[mergedLen][];

        int index = 0;
        while (index < mergedLen) {
            if (index < left) {
                merged[index] = intervals[index];
                index++;
            } else if (index == left && overlaps) {
                int[] innerMerged = new int[2];
                innerMerged[0] = Math.min(intervals[left][0], newInterval[0]);
                innerMerged[1] = Math.max(intervals[right][1], newInterval[1]);
                merged[index] = innerMerged;
                index++;
            } else if (index == left && !overlaps) {
                int[] lower = intervals[left][0] < newInterval[0] ? intervals[left] : newInterval;
                int[] higher = intervals[left][0] > newInterval[0] ? intervals[left] : newInterval;
                merged[index++] = lower;
                merged[index++] = higher;
            } else {
                int afterIndex = overlaps ? index + (right - left) : index - 1;
                merged[index] = intervals[afterIndex];
                index++;
            }
        }
        
        return merged;
    }
    
    private int findLeftEnd(int[][] intervals, int[] newInterval, int low, int high) {
        while (low <= high) {
            int center = low + (high - low) / 2;
            Overlap overlap = overlaps(intervals, newInterval, center);
            
            if (overlap == Overlap.RIGHT) {
                low = center + 1;
            } else if (overlap == Overlap.RIGHT_OVERLAP || overlap == Overlap.INSIDE) {
                return center;
            } else if (overlap == Overlap.LEFT_OVERLAP) {
                if (low == center) { return center; }
                Overlap leftOver =  overlaps(intervals, newInterval, center - 1);
                if (leftOver == Overlap.RIGHT) { return center; }
                high = center - 1;
            } else {
                high = center - 1;
            }
        }
        
        return low;
    }
    
    private int findRightEnd(int[][] intervals, int[] newInterval, int low, int high) {
        while (low <= high) {
            int center = low + (high - low) / 2;
            Overlap overlap = overlaps(intervals, newInterval, center);
            
            if (overlap == Overlap.LEFT) {
                high = center - 1;
            } else if (overlap == Overlap.LEFT_OVERLAP || overlap == Overlap.INSIDE) {
                return center;
            } else if (overlap == Overlap.RIGHT_OVERLAP) {
                if (high == center) { return center; }
                Overlap rightOver =  overlaps(intervals, newInterval, center + 1);
                if (rightOver == Overlap.LEFT) { return center; }
                low = center + 1;
            } else {
                low = center + 1;
            }
        }
        
        return high;
    }
    
    private Overlap overlaps(int[][] intervals, int[] newInterval, int index) {
        int[] intervalToCheck = intervals[index];
        if (newInterval[0] >= intervalToCheck[0] && newInterval[1] <= intervalToCheck[1]) {
            return Overlap.INSIDE;
        } else if (newInterval[0] < intervalToCheck[0] && newInterval[1] > intervalToCheck[1]) {
            return Overlap.AROUND;
        } else if (newInterval[1] >= intervalToCheck[0] && newInterval[1] <= intervalToCheck[1]) {
            return Overlap.LEFT_OVERLAP;
        } else if (newInterval[0] <= intervalToCheck[1] && newInterval[0] >= intervalToCheck[0]) {
            return Overlap.RIGHT_OVERLAP;
        } else if (newInterval[1] < intervalToCheck[0]) {
            return Overlap.LEFT;
        } else {
            return Overlap.RIGHT;
        }
    }

    public static void main(String[] args) {
        InsertInterval inter = new InsertInterval();
        // int[][] intervals = {  { 1, 3 }, { 6, 9 } };
        // int[] interval = { 2, 5 };

        // int[][] intervals = { {1, 5} };
        // int[] interval = {6, 8};

        // int[][] intervals = { {1, 5} };
        // int[] interval = {1, 7};

        // int[][] intervals = { {1, 5} };
        // int[] interval = {0, 6};

        // int[][] intervals = { {2, 5}, { 6, 7 }, { 8, 9 } };
        // int[] interval = {0, 1};
        // int[][] intervals = { {1, 2}, { 3, 5 }, { 6, 7 }, { 8, 10 }, { 12, 16 } };
        // int[] interval = { 4, 8 };
        int[][] intervals = { {2,6}, { 7,9 } };
        int[] interval = { 15, 18 };

        int[][] newInt = inter.insert(intervals, interval);
        for (int[] newI : newInt) {
            System.out.println(Arrays.toString(newI));
        }
    }
}
