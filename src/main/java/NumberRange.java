public class NumberRange {
    
    public int[] searchRange(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return new int[] { -1, -1 };
        }
        //[5,7,7,8,8,10], 8
        int low = 0; //3
        int high = nums.length - 1; //5
        
        while (low <= high) {
            int center = low + (high - low) / 2; //4
            
            if (nums[center] == target) {
                //search left + right
                int highest = highestTargetIndex(nums, target, low, high);
                int lowest = lowestTargetIndex(nums, target, low, high);
                int rangeLow = lowest == -1 ? center : lowest;
                int rangeHigh = highest == -1 ? center: highest;
                
                return new int[] { rangeLow, rangeHigh };
            } else if (nums[center] < target) {
                low = center + 1;
            } else {
                high = center - 1;
            }
        }
        
        return new int[] { -1, -1 };
    }
    
    private int highestTargetIndex(int[] nums, int target, int low, int high) {
        int highest = -1;
        
        while (low <= high) {
            int center = low + (high - low) / 2;
            
            if (nums[center] > target) {
                high = center - 1;
            } else {
                highest = center;
                low = center + 1;
            }
        }
        
        return highest;
    }
    
    private int lowestTargetIndex(int[] nums, int target, int low, int high) {
        int lowest = Integer.MAX_VALUE;
        
        while (low <= high) {
            int center = low + (high - low) / 2;
            
            if (nums[center] < target) {
                low = center + 1;
            } else {
                lowest = center;
                high = center - 1;
            }
        }
        
        return lowest;
    }
}