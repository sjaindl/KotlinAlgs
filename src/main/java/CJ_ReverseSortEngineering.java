import java.util.*;
import java.io.*;

// Code Jam
public class CJ_ReverseSortEngineering {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int numTestcases = Integer.valueOf(reader.readLine()); // Scanner has functions to read ints, longs, strings, chars, etc.
        List<String> results = new ArrayList<>();
        for (int testCase = 1; testCase <= numTestcases; testCase++) {
            String inputList = reader.readLine();
            int[] intList = split(inputList);

            int numElements = intList[0];
            int count = intList[1];

            ReverseSortSolution reverseSort = new CJ_ReverseSortEngineering().new ReverseSortSolution();
            Integer[] list = reverseSort.reverseCount(numElements, count);

            StringBuilder sb = new StringBuilder();
            sb.append("Case #" + testCase + ": ");
            for (int el : list) {
                sb.append(el + " ");
            }
            if (list.length == 0) {
                sb.append("IMPOSSIBLE");
            }

            results.add(sb.toString().trim());
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
        Integer[] reverseCount(int numElements, int count) {
            if (count < numElements - 1) {
                return new Integer[0];
            }

            if (count > (numElements) * (numElements - 1) / 2) {
                return new Integer[0];
            }

            Integer[] elements = new Integer[numElements];
            for (int i = 1; i <= numElements; i++) {
                elements[i-1] = i;
            }

            for (List<Integer> perm : permutations(numElements, elements)) {
                Integer[] intList = Arrays.asList(perm.toArray()).toArray(new Integer[0]);
                int countForPerm = reverseCount(numElements, intList.clone());
                if (count == countForPerm) {
                    //System.out.println(Arrays.toString(intList) + ": " + countForPerm);
                    return intList;
                }
            }

            return elements;
        }

        int reverseCount(int numElements, Integer[] intList) {
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

    private void reverse(Integer[] intList, int from, int to) {
        while (from < to) {
            int temp = intList[from];
            intList[from] = intList[to];
            intList[to] = temp;
            from++;
            to--;
        }
    }

    private List<List<Integer>> permutations(int n, Integer[] elements) {
        int[] indexes = new int[n];
        for (int i = 0; i < n; i++) {
            indexes[i] = 0;
        }

        List<List<Integer>> permutations = new ArrayList<>();

        List<Integer> initList = new ArrayList<Integer>();
        for (int element : elements) {
            initList.add(element);
        }

        permutations.add(initList);

        int i = 0;
        while (i < n) {
            if (indexes[i] < i) {
                swap(elements, i % 2 == 0 ?  0: indexes[i], i);
                List<Integer> list = new ArrayList<Integer>();
                for (int element : elements) {
                    list.add(element);
                }

                permutations.add(list);
                indexes[i]++;
                i = 0;
            }
            else {
                indexes[i] = 0;
                i++;
            }
        }

        return permutations;
    }

    private void swap(Integer[] input, int a, int b) {
        int tmp = input[a];
        input[a] = input[b];
        input[b] = tmp;
    }
}
