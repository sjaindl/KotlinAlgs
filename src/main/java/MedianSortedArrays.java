import java.util.*;

public class MedianSortedArrays {
    public double findMedianSortedArraysSlow(int[] nums1, int[] nums2) {
        if (nums1.length + nums2.length == 0) {
            return 0.0;
        }
        
        List<Integer> merged = new ArrayList();
        int firstIndex = 0;
        int secondIndex = 0;
        boolean even = (nums1.length + nums2.length) % 2 == 0;
        int stopAfter = (nums1.length + nums2.length) / 2 + 1;
        while(merged.size() < stopAfter) {
            if (secondIndex >= nums2.length 
                || firstIndex < nums1.length && nums1[firstIndex] < nums2[secondIndex]) {
                merged.add(nums1[firstIndex]);
                firstIndex++;
            } else {
                merged.add(nums2[secondIndex]);
                secondIndex++;
            }
        }
        
        if(even) {
            return ((double) (merged.get(stopAfter - 1) + merged.get(stopAfter - 2))) / 2;
        } else {
            return merged.get(stopAfter - 1);
        }
    }
}
