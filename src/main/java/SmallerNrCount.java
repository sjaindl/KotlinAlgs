import java.util.*;

public class SmallerNrCount {
    // mergesort
    public List<Integer> countSmaller(int[] nums) {
        if (nums == null || nums.length < 1) return new ArrayList<>();

        Item[] items = new Item[nums.length];
        for (int index = 0; index < nums.length; index++) {
            items[index] = new Item(index, nums[index]);
        }

        Integer[] counts = new Integer[nums.length];
        for (int index = 0; index < counts.length; index++) {
            counts[index] = 0;
        }

        mergeSort(counts, items, 0, nums.length - 1);

        return Arrays.asList(counts);
    }

    private void mergeSort(Integer[] counts, Item[] items, int low, int high) {
        if (high <= low) return;

        int middle = low + (high - low) / 2;
        mergeSort(counts, items, low, middle);
        mergeSort(counts, items, middle + 1, high);
        merge(counts, items, low, middle, middle + 1, high);
    }

    private void merge(Integer[] counts, Item[] items, int low, int lowEnd, int high, int highEnd) {
        int span =  highEnd - low + 1;
        Item[] auxiliary = new Item[span];
        int start = low;
        //System.arraycopy(items, low, auxiliary, 0, span);

        int rightLowerCount = 0;
        int index = 0;
        while (low <= lowEnd || high <= highEnd) {
            if (low > lowEnd) {
                auxiliary[index++] = items[high++];
            } else if (high > highEnd) {
                counts[items[low].index] += rightLowerCount;
                auxiliary[index++] = items[low++];
            } else if (items[high].value < items[low].value) {
                // inconsistency: right element is smaller:
                rightLowerCount++;
                auxiliary[index++] = items[high++];
            } else {
                // consistent - persist former inconsistencies
                counts[items[low].index] += rightLowerCount;
                auxiliary[index++] = items[low++];
            }
        }

        System.arraycopy(auxiliary, 0, items, start, span);
    }
    
    class Item {
        int index;
        int value;

        Item(int index, int value) {
            this.index = index;
            this.value = value;
        }
    }

    /* ---------------------------------------------------------------------------------------- */ 

    
    public List<Integer> countSmallerWrong(int[] nums) {
        if (nums == null || nums.length < 1) return new ArrayList<Integer>();
        
        //[6,5,5,2,6,1] 
        Integer[] smaller = new Integer[nums.length]; // [0,0,0,0,0,0]
        TreeMap<Integer, Integer> tree = new TreeMap<>(); 
        Map<Integer, Integer> counts = new HashMap();
        
        for (int index = nums.length - 1; index >= 0; index--) { //5
            int smallerCount = tree.lowerEntry(nums[index]).getValue();  //
            smaller[index] = smallerCount;
            
            addOrIncrementCount(counts, nums[index]);
            tree.put(nums[index], smallerCount + counts.get(nums[index]));
        }
        
        return Arrays.asList(smaller);
    }
    
    private void addOrIncrementCount(Map<Integer, Integer> counts, Integer number) {
        Integer count = counts.getOrDefault(number, 0);
        counts.put(number, count + 1);
    }

    public static void main(String[] args) {
        SmallerNrCount snc = new SmallerNrCount();
        System.out.println(snc.countSmaller(new int[] { 6,5,5,2,6,1 }));
    }
}
