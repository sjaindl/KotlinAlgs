import java.util.*;

public class SplitArrayLargest {
    
    int min = Integer.MAX_VALUE;

    // Bruteforce
    public int splitArrayBF(int[] nums, int m) {
        if (nums == null || nums.length < 1 || m > nums.length || m == 0) return -1;
        if (nums.length == 0) return 0;
        
        min = Integer.MAX_VALUE;
        int minLen = 1;
        int maxLen = nums.length - m + 1; // 4
        
        splitArrayRecBF(nums, m, 0, minLen, maxLen, 0, 0);

        return min;
    }

    public void splitArrayRecBF(int[] nums, int m, int curM, int minLen, int maxLen, 
                            int startIndex, int maxSum) {
        if (startIndex > nums.length) return;
        if (curM == m && startIndex < nums.length) return;
        if (startIndex == nums.length && curM == m) {
            min = Math.min(min, maxSum);
        } 
        
        for (int len = minLen; len <= maxLen; len++) {
            int endIndex = len + startIndex - 1;
            if (endIndex >= nums.length) break;
            
            int sum = 0;
            
            for (int i = startIndex; i <= endIndex; i++) {
                sum += nums[i];
            }
            
            splitArrayRecBF(nums, m, curM + 1, minLen, maxLen, endIndex + 1, Math.max(maxSum, sum));
        }
    }

    // Top-down dynamic programming
    public int splitArrayDynProgTopDown(int[] nums, int m) {
        if (nums == null || nums.length < 1 || m > nums.length || m == 0) return -1;
        if (nums.length == 0) return 0;
        
        int minLen = 1;
        int maxLen = nums.length - m + 1; // 4
        
        //memo for startIndex & cur m:
        //Map<MemoPair, Integer> memo = new HashMap<>(); // MemoPair : count
        int[][] memo = new int[nums.length + 1][m];
        int[] cumulativeCounts = cumulate(nums);

        splitArrayRecDynProgTopDown(nums, m, 0, minLen, maxLen, 0, 0, memo, cumulativeCounts);

        return min;
    }

    private int[] cumulate(int[] nums) {
        int[] cumulative = new int[nums.length];
        cumulative[0] = nums[0];

        for (int index = 1; index < nums.length; index++) {
            cumulative[index] = cumulative[index - 1] + nums[index];
        }

        return cumulative;
    }

    public int splitArrayRecDynProgTopDown(int[] nums, int m, int curM, int minLen, int maxLen, int startIndex, 
                                           int maxSum, /* Map<MemoPair, Integer> */ int[][] memo, int[] cumulativeCounts) {
        if (startIndex > nums.length) return 0;
        if (curM == m && startIndex < nums.length) return 0;
        if (startIndex == nums.length && curM == m) {
            System.out.println("m: " + curM + ", index: " + startIndex + ", sum: " + maxSum);
            min = Math.min(min, maxSum);
            return 0;
        } 
        
        // lookup 
        // if exist: update min
        //MemoPair pair = new MemoPair(startIndex, curM);

        //if (memo.containsKey(pair)) {
        if( memo[startIndex][curM] != 0) {
            //Integer maxSubSequenceCount = memo.get(pair);
            Integer maxSubSequenceCount = memo[startIndex][curM];
            int maxSumOfAll = Math.max(maxSum, maxSubSequenceCount);
            min = Math.min(min, maxSumOfAll);
            return maxSubSequenceCount;
        }

        int maxSubCount = 0;
        for (int len = minLen; len <= maxLen; len++) {
            int endIndex = len + startIndex - 1;
            if (endIndex >= nums.length) break;
            
            int sum = cumulativeCounts[endIndex];
            if (startIndex > 0) {
                sum -= cumulativeCounts[startIndex - 1];
            }
            
            int subcount = splitArrayRecDynProgTopDown(nums, m, curM + 1, minLen, maxLen, endIndex + 1, Math.max(maxSum, sum), memo, cumulativeCounts);
            maxSubCount = Math.max(subcount, maxSubCount);
        }

        // save to memo
        //memo.put(pair, maxSubCount);
        memo[startIndex][curM] = maxSubCount;

        return maxSubCount;
    }

    class MemoPair {
        int startIndex;
        int numSequences; //curM

        MemoPair(int startIndex, int numSequences) {
            this.startIndex = startIndex;
            this.numSequences = numSequences;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof MemoPair)) return false;
            if (obj == this) return true;
            MemoPair other = (MemoPair) obj;

            return startIndex == other.startIndex && numSequences == other.numSequences;
        }

        @Override
        public int hashCode() {
            return Objects.hash(startIndex, numSequences);
        }
    }

    public int splitArrayIterative(int[] nums, int numSequences) {
        int numLen = nums.length;
        int[][] memo = new int[numLen + 1][numSequences + 1];
        int[] cumulativeCounts = new int[numLen + 1];
        
        for (int index = 0; index <= numLen; index++) {
            for (int sequence = 0; sequence <= numSequences; sequence++) {
                memo[index][sequence] = Integer.MAX_VALUE;
            }
        }
        
        for (int index = 0; index < numLen; index++) {
            cumulativeCounts[index + 1] = cumulativeCounts[index] + nums[index];
        }

        memo[0][0] = 0;
        for (int toIndex = 1; toIndex <= numLen; toIndex++) {
            for (int sequence = 1; sequence <= numSequences; sequence++) {
                for (int fromIndex = 0; fromIndex < toIndex; fromIndex++) { //split
                    int valueBeforeSplit = memo[fromIndex][sequence - 1];
                    int sumAfterSplit = cumulativeCounts[toIndex] - cumulativeCounts[fromIndex];

                    memo[toIndex][sequence] = Math.min(
                                                    memo[toIndex][sequence], 
                                                    Math.max(
                                                        valueBeforeSplit, 
                                                        sumAfterSplit
                                                    )
                                                );
                }
            }
        }

        // for (int i = 1; i <= numLen; i++) {
        //     System.out.println("");
        //     for (int j = 1; j <= numSequences; j++)
        //         System.out.print(memo[i][j] + ",");
        // }
        
        
        return memo[numLen][numSequences];        
    }

    public static void main(String[] args) {
        SplitArrayLargest sal = new SplitArrayLargest();
        // System.out.println(sal.splitArrayBF(new int[] { 7,2,5,10,8 }, 2));
        // System.out.println(sal.splitArrayDynProgTopDown(new int[] { 7,2,5,10,8 }, 2));
        System.out.println(sal.splitArrayIterative(new int[] { 7,2,5,10,8 }, 2));

        // System.out.println(sal.splitArrayBF(new int[] { 2,3,1,2,4,3 }, 5));
        //System.out.println(sal.splitArrayDynProgTopDown(new int[] { 2,3,1,2,4,3 }, 5));
    }
}
