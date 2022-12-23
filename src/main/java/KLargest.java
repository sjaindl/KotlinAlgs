import java.util.*;

public class KLargest {
    
    public int findKthLargest(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k < 1 || nums.length < k) {
            return Integer.MIN_VALUE;
        }
        
        PriorityQueue<Integer> largest = new PriorityQueue<>();
        for (Integer num : nums) {
            if (largest.size() < k) {
                largest.add(num);
            } else if (num > largest.peek()) {
                largest.poll();
                largest.add(num);
            }
        }
        
        return largest.peek();
    }
}
