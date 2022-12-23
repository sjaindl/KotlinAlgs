public class TrappingRain {
    public int trap(int[] height) {
        // 1. compute tallestFromLeft
        // 2. compute tallestFromRight
        // 3. It. through arr., find 2 tallest each, add to sum if > 0: min(tallest) - height[index]
        // 4. return sum
        if (height == null || height.length < 3) return 0;
        
        int[] tallestLeft = tallestFromLeft(height); // [0,1,1,2,2,2,2,3,3,3,3,3]
        int[] tallestRight = tallestFromRight(height); // [3,3,3,3,3,3,3,3,2,2,2,1]
        
        int waterSum = 0; // 0
        
        //[0,1,0,2,1,0,1,3,2,1,2,1]
        for (int index = 1; index < height.length - 1; index++) {
            int left = tallestLeft[index];
            int right = tallestRight[index];
            int sumAtLevel = Math.min(left, right) - height[index]; //1
            if (sumAtLevel > 0) waterSum += sumAtLevel; //1
        }
        
        return waterSum;
    }
    
    //[0,1,0,2,1,0,1,3,2,1,2,1]
    // -- >[0,1,1,2,2,2,2,3,3,3,3,3]
    private int[] tallestFromLeft(int[] height) {
        int[] tallest = new int[height.length];
        int max = 0;
        
        for (int index = 0; index < height.length; index++) {
            max = Math.max(max, height[index]);
            tallest[index] = max;
        }
        
        return tallest;
    }
    
    //[0,1,0,2,1,0,1,3,2,1,2,1]
    // -> [3,3,3,3,3,3,3,3,2,2,2,1]
    private int[] tallestFromRight(int[] height) {
        int[] tallest = new int[height.length];
        int max = 0;
        
        for (int index = height.length - 1; index >= 0; index--) {
            max = Math.max(max, height[index]);
            tallest[index] = max;
        }
        
        return tallest;
    }
}