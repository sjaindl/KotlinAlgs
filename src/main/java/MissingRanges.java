class Solution {
    public List<String> findMissingRanges(int[] nums, int lower, int upper) {
        List<String> ranges = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            ranges.add(buildRange(lower, upper));
            return ranges;
        }
        
        if (lower == upper) {
            return ranges;
        }
        
        if (lower < nums[0]) {
            ranges.add(buildRange(lower, nums[0] - 1));
        }
        
        int low = nums[0];
        
        for (int num : nums) {
            if (num > low + 1) {
                ranges.add(buildRange(low + 1, num - 1));
            }
            low = num;
        }
        
        if (upper > low) {
            ranges.add(buildRange(low + 1, upper));
        }
        
        return ranges;
    }
    
    private String buildRange(int from, int to) {
        if (to < from) {
            return "";
        } if (from == to) {
            return String.valueOf(from);
        } else {
            return String.valueOf(from) + "->" + String.valueOf(to);
        }
    }
}
