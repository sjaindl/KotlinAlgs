import java.util.Random;

public class QuickSelect {
    
    Random random = new Random();

    //{7, 10, 4, 3, 20, 15}
    //k = 3
    // expected: 7
    Integer chooseKSmallestElement(int k, int[] array) {
        if (array == null || k > array.length || k <= 0) return null;

        k--;
        int low = 0; //0
        int high = array.length -1; //5

        while (low <= high) {
            int pivot = partition(low, high, array); //2
            if (pivot == k) return array[k];
            else if (pivot > k) high = pivot - 1;
            else low = pivot + 1;
        }

        return -1;
    }

    private int partition(int low, int high, int[] array) {
        int pivot = low + (high - low) / 2; //low + random.nextInt(high - low + 1); // 2
        int pivotVal = array[pivot]; //4
        //{7, 10, 4, 3, 20, 15}
        exchange(pivot, low, array);
        //{7, 3, 4, 10, 20, 15}

        int left = low + 1; //1
        int right = high; //5

        while (left <= right) {
            while (left <= right && array[left] < pivotVal) left++; //2
            while (right >= left && array[right] > pivotVal) right--; //1
            if (left < right) exchange(left, right, array);
        }

        exchange(low, right, array);

        return right;
    }

    private void exchange(int index1, int index2, int[] array) {
        int temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }

    public static void main(String[] args) {
        QuickSelect quickSelect = new QuickSelect();
        System.out.println(quickSelect.chooseKSmallestElement(1, new int[] { 7, 10, 4, 3, 20, 15 }));
        System.out.println(quickSelect.chooseKSmallestElement(2, new int[] { 7, 10, 4, 3, 20, 15 }));
        System.out.println(quickSelect.chooseKSmallestElement(3, new int[] { 7, 10, 4, 3, 20, 15 }));
        System.out.println(quickSelect.chooseKSmallestElement(4, new int[] { 7, 10, 4, 3, 20, 15 }));
        System.out.println(quickSelect.chooseKSmallestElement(5, new int[] { 7, 10, 4, 3, 20, 15 }));
        System.out.println(quickSelect.chooseKSmallestElement(6, new int[] { 7, 10, 4, 3, 20, 15 }));
    }
}
