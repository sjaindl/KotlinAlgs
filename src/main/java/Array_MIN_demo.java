import java.util.*;

public class Array_MIN_demo {
    // you can also use imports, for example:

// you can write to stdout for debugging purposes, e.g.
// System.out.println("this is a debug message");

    private final String noContact = "NO CONTACT";

    public String contactSolution(String[] A, String[] B, String P) {
        // write your code in Java SE 8
        if (A == null || B == null || A.length == 0 || B.length == 0 || A.length != B.length
        || P == null || P.length() == 0) {
            return noContact;
        }

        String[] names = A;
        String[] numbers = B;
        String pattern = P;

        String result = null;
        for (int index = 0; index < numbers.length; index++) {
            if (numbers[index].contains(pattern)) {
                String name = names[index];
                if (result == null || name.compareTo(result) < 0) {
                    result = name;
                }
            }
        }

        return result == null ? noContact : result;
    }

    public int solution(int[] A) {
        // write your code in Java SE 8
        // insert > 0 ints in min PQ
        // edge case: empty or top != 1 -> return 1
        // cur = 1
        // if top > cur + 1 : return cur + 1
        // pop from PQ, update cur
        if (A == null || A.length == 0) return 1;

        PriorityQueue<Integer> pq = buildPQ(A);
        if (pq.isEmpty() || pq.peek() != 1) return 1;

        int current = 1;

        while (!pq.isEmpty()) {
            Integer element = pq.poll();
            if (element > current + 1) {
                return current + 1;
            }
            current = element;
        }

        return current + 1;
    }

    public int solution2(int[] A) {
        // write your code in Java SE 8
        // insert > 0 ints in min PQ
        // edge case: empty or top != 1 -> return 1
        // cur = 1
        // if top > cur + 1 : return cur + 1
        // pop from PQ, update cur
        if (A == null || A.length == 0) return 1;

        Arrays.sort(A);
        int current = 0;
        for (int element : A) {
            if (element < 1) continue;

            if (element > current + 1) {
                return current + 1;
            }
            current = element; 
        }

        return current + 1;
    }

    private PriorityQueue<Integer> buildPQ(int[] A) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (Integer element : A) {
            if (element > 0) pq.add(element);
        }

        return pq;
    }


    private List<String> days = Arrays.asList(new String[] {
        "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
    });

    private List<String> months = Arrays.asList(new String[] {
        "January", "February", "March", "April", "May", "June", 
        "July", "August", "September", "October", "November", "December"
    });

    private List<Integer> daysInMonth = Arrays.asList(new Integer[] {
        31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
    });

    private List<Integer> daysInMonthLeap = Arrays.asList(new Integer[] {
        31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
    });

    public int solutionDate(int year, String beginMonth, String endMonth, String firstDayOfYear) {
        if (beginMonth == null || endMonth == null || firstDayOfYear == null) return 0;

        boolean isLeapYear = year % 4 == 0;
        List<Integer> daysList = isLeapYear ? daysInMonthLeap : daysInMonth;
        
        int beginMonthIndex = months.indexOf(beginMonth);
        int endMonthIndex = months.indexOf(endMonth);

        if (beginMonthIndex == -1 || endMonthIndex == -1 || endMonthIndex < beginMonthIndex) {
            return 0;
        }

        int firstDayOfYearIndex = days.indexOf(firstDayOfYear);
        int firstMondayInYear = (days.size() - firstDayOfYearIndex) % days.size() + 1; //day

        int daysBeforeBeginMonth = 0;
        for (Integer curMonth = 0; curMonth < beginMonthIndex; curMonth++) {
            daysBeforeBeginMonth += daysList.get(curMonth);
            if (curMonth == 0) daysBeforeBeginMonth = daysBeforeBeginMonth - firstMondayInYear + 1;
        }

        int firstMondayInBeginMonth = (daysBeforeBeginMonth - 1) % days.size();
        if (firstMondayInBeginMonth == 0) firstMondayInBeginMonth = 7;

        int daysCount = daysList.get(beginMonthIndex) - firstMondayInBeginMonth + 1;
        for (Integer curMonth = beginMonthIndex; curMonth < endMonthIndex; curMonth++) {
            daysCount += daysList.get(curMonth);
        }

        return daysCount / 7;
    }

    public static void main(String[] args) {
        Array_MIN_demo demo = new Array_MIN_demo();

        System.out.println(demo.solutionDate(2014, "April", "May", "Wednesday"));

        // System.out.println(demo.solution2(new int[] { 1, 3, 6, 4, 1, 2 }));
        
        // int[] arr = new int[1000001];

        // int index = 0;
        // while (index <= 1000000) {
        //     arr[index] = index - 100;
        //     index++;
        // }

        // long start = System.currentTimeMillis();
        // System.out.println(demo.solution(arr));
        // long end = System.currentTimeMillis();
        // System.out.println("Time sol1:" + (end - start));

        // start = end;
        // System.out.println(demo.solution2(arr));
        // end = System.currentTimeMillis();
        // System.out.println("Time sol2:" + (end - start));

        // Array_MIN_demo cont = new Array_MIN_demo();
        // System.out.println(cont.contactSolution(new String[] { "sander", "amy", "ann", "michael" }, new String[] { "123456789", "234567890", "789123456", "123123123" }, "1"));

    }
}
