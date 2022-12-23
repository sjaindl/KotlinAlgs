class ContainerWithMostWater {
    public int maxArea(int[] height) {
        int left = 0;
        int right = height.length - 1;
        int best = 0;
        
        while (left < right) {
            int containerHeight = Math.min(height[left], height[right]);
            int area = containerHeight * (right - left);
            
            if (area > best) {
                best = area;
            }
            
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        
        return best;
    }
}
