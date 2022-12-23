import java.util.*;

class QuickSort {
    private Random random = new Random();
    
	public ArrayList<Integer> quickSort(ArrayList<Integer> arr) {
		// write your awesome code here
        if (arr == null || arr.size() < 2) return arr;
        
        ArrayList<Integer> sorted = new ArrayList<Integer>(arr);
        quickSortRec(sorted, 0, arr.size() - 1);
        
        return sorted;
    }	
    
    private void quickSortRec(ArrayList<Integer> arr, int low, int high) {
        if (low < high) {
        	int pivot = partition(arr, low, high);
            quickSortRec(arr, low, pivot - 1);
            quickSortRec(arr, pivot + 1, high);
        }
    }
    
    private int partition(ArrayList<Integer> arr, int low, int high) {
        int pivot = low + (high - low) / 2; //low + random.nextInt(high - low);
        int pivotVal = arr.get(pivot);
        exchange(arr, low, pivot);
        
        int lo = low + 1;
        int hi = high;
        
        while (lo <= hi) {
            while (lo <= hi && arr.get(lo) <= pivotVal) {
                lo++;
            }
            
            while (hi >= lo && arr.get(hi) >= pivotVal) {
                hi--;
            }
            
            if (hi > lo)
                exchange(arr, lo, hi);
        }
        
        exchange(arr, low, hi);
        return hi;
    }
    
    private void exchange(ArrayList<Integer> arr, int low, int high) {
        int temp = arr.get(low);
        arr.set(low, arr.get(high));
        arr.set(high, temp);
    }

    public static void main(String[] args) {
        QuickSort quickSort = new QuickSort();
        ArrayList<Integer> list = new ArrayList<>() {{
            add(5);
            add(2);
            add(4);
            add(1);
            add(1);
        }};
        
        ArrayList<Integer> sorted = quickSort.quickSort(list);
        System.out.println(sorted);
    }
}