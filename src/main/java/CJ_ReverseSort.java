import java.util.*;
import java.io.*;

// Code Jam
public class CJ_ReverseSort {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int numTestcases = Integer.valueOf(reader.readLine()); // Scanner has functions to read ints, longs, strings, chars, etc.
        List<String> results = new ArrayList<>();
        for (int testCase = 1; testCase <= numTestcases; testCase++) {
            int numElements = Integer.valueOf(reader.readLine());
            String inputList = reader.readLine();
            int[] intList = split(inputList);

            ReverseSortSolution reverseSort = new CJ_ReverseSort().new ReverseSortSolution();
            int count = reverseSort.reverseCount(numElements, intList);

            results.add("Case #" + testCase + ": " + count);
        }

        for (String result : results) {
            System.out.println(result);
        }
    }

    public static int[] split(String list) {
        if (list.length() == 0) {
            return new int[0];
        }

        String[] strArray = list.split(" ");
        int[] intArray = new int[strArray.length];
        for(int i = 0; i < strArray.length; i++) {
            intArray[i] = Integer.parseInt(strArray[i]);
        }

        return intArray;
    }

    class ReverseSortSolution {
        int reverseCount(int numElements, int[] intList) {
            int count = 0;

            for (int index = 0; index < numElements - 1; index++) {
                int minIndex = index;
                int minValue = intList[index];
                for (int rightIndex = index + 1; rightIndex < numElements; rightIndex++) {
                    if (intList[rightIndex] < minValue) {
                        minIndex = rightIndex;
                        minValue = intList[rightIndex];
                    }
                }

                reverse(intList, index, minIndex);
                count += minIndex - index + 1;
            }

            return count;
        }
    }

    private void reverse(int[] intList, int from, int to) {
        while (from < to) {
            int temp = intList[from];
            intList[from] = intList[to];
            intList[to] = temp;
            from++;
            to--;
        }
    }
}
