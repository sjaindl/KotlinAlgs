public class NextPerm {
    public void nextPermutation(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }
        
        int exchIndex = outOfOrderIndex(nums);
        if (exchIndex == -1) {
            reverse(nums, 0, nums.length - 1);
        } else {
            int nextLarger = nextLargerIndex(nums, exchIndex);
            System.out.println(nextLarger + "," + exchIndex);
            swap(nums, nextLarger, exchIndex);
            reverse(nums, exchIndex + 1, nums.length - 1);
        }
    }
    
    private int outOfOrderIndex(int[] nums) {
        for (int index = nums.length - 2; index >= 0; index--) {
            if (nums[index] < nums[index + 1]) {
                return index;
            }
        }
        
        return -1;
    }
    
    private int nextLargerIndex(int[] nums, int from) {
        int value = nums[from];
        int min = Integer.MAX_VALUE;
        int minIndex = from;
        for (int index = from + 1; index < nums.length; index++) {
            if (nums[index] > value && nums[index] <= min) {
                min = nums[index];
                minIndex = index;
            }
        }
        
        return minIndex;
    }
    
    private void reverse(int[] nums, int from, int to) {
        while (from < to) {
            swap(nums, from, to); 
            from++;
            to--;
        }
    }
    
    private void swap(int[] nums, int first, int second) {
        int temp = nums[first];
        nums[first] = nums[second];
        nums[second] = temp;
    }

    public static void main(String[] args) {
        NextPerm combi = new NextPerm();
        int[] nums = { 2,3,1,3,3 };
        //2,3,1,3,3 -> 2,3,3,1,3 -> 2,3,3,3,1
        combi.nextPermutation(nums);
        System.out.println(nums);
    }

}


//[1,1,5]
/*

[1,5,8,4,7,6,5,3,1] -> [1,5,8,5,7,6,4,3,1] -> [1,5,8,5,1,3,4,6,7]
//1. find first out of order von hinten
//2. swap with next larger
//3. reverse i + 1


[1,1,5] -> [1,5,1]
[1,1,5,2,3] -> [1,1,5,3,2]
[1,3,2,4,5] -> [1,3,2, 5, 4] 
[1,5,3,4,2] -> [1,5,4,2,3] 
[1,5,2,3,4] -> [1,5,3,2,4] 
[1,2,3] -> [1,3,2] 
[3,2,1] -> [3,2,1]
[3,4,2,1] -> exch. i/i-1 = [3,1,2,4] -> [4,1,2,3]
//remember last min & second min out of order (desc.), exch.
//else sort
erster falscher von hinten = index 1
wenn danach höherer -> mit dem
*/
//1234,1243,1324,1342,1423,1432,2134...