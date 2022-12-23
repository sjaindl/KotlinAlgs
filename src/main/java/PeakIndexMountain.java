public class PeakIndexMountain {
    public int peakIndexInMountainArray(int[] arr) {
        if (arr == null || arr.length < 3) return -1;
        
        // [3,5,3,2,0]
        int low = 0; // 0
        int high = arr.length - 1; // 1
        while (low <= high) {
            int middle = low + (high - low) / 2; // 0
            if (middle > 0 && middle < arr.length - 1 
                && arr[middle] > arr[middle - 1] && arr[middle] > arr[middle + 1]) {
                //isPeak
                return middle;
            } else if (middle == 0 || arr[middle] > arr[middle - 1]) {
                // go right
                low = middle + 1;
            } else {
                // go left
                high = middle - 1;
            }
        }
        
        return -1;
    }
}

/*
    BF: traverse through arr. if arr[index] > arr[index + 1] then return index. 
    O(n) runtime, O(1) space.
    
    Opt.: Binary Search. O(log N) runtime. O(1) space.
    int low = 0; int high = arr.length - 1;
    calc. center:
    if isPeak(center) return center; -> if arr[index] > arr[index-1] && arr[index] < arr[index+1];
    else if arr[index] > arr[index - 1] -> low = center + 1;
    else: high = center - 1;
    
    
*/