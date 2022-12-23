import java.util.*;

class MyCalendarTwo {

    Node root;
    /*
        10-20, 2
        50-60, 1
        
    */
    private static final int MAX_BOOKINGS = 3;

    private List<Interval> counts = new ArrayList<>();
    private List<Interval> singleBooked = new ArrayList<>();
    private List<Interval[]> doubleBooked = new ArrayList<>();
    
    public MyCalendarTwo() {
        
    }
    
    public boolean book(int start, int end) {
        Interval intervalToAdd = new Interval(start, end);
        
        for (Interval[] interval : doubleBooked) {
            if (interval[0].isInside(start, end) 
                && interval[1].isInside(start, end)
                && intervalToAdd.isInside(interval[0])
                && intervalToAdd.isInside(interval[1])) {
                return false;
            }
        }
        
        for (Interval interval : singleBooked) {
            if (interval.isInside(start, end)) {
                doubleBooked.add(new Interval[] { interval, intervalToAdd });
            }
        }
        
        singleBooked.add(intervalToAdd);
        
        return true;
    }

    public boolean bookBF(int start, int end) {
        List<Interval> found = new ArrayList<>();
        found.add(new Interval(start, end));
        for (Interval interval : counts) {
            if (interval.isInside(start, end)) {
                found.add(interval);
            } 
            // if (count == MAX_BOOKINGS - 1) {
            //     return false;
            // } 
        }


        
        counts.add(new Interval(start, end));
        
        return true;
    }
    
    // start <= x < end
    public boolean book2(int start, int end) {  // 10, 40
        if (start >= end) return false;
        
        if (root == null) {
            root = new Node(new Interval(start, end), 1);
            return true;
        }
        
        return bookRec(start, end, root);
    }
    
    public boolean bookRec(int start, int end, Node node) { // 10, 40
        if (node.isInside(start, end)) {
            if (node.count >= MAX_BOOKINGS - 1) return false;
            node.count++;
        }

        Interval leftInterval = left(start, end, node);
        Interval rightInterval = right(start, end, node); //20,40
        
        if (leftInterval != null) {
            if (node.left == null) {
                node.left = new Node(leftInterval, 1);
            } else {
                boolean leftValid = bookRec(start, leftInterval.end, node.left);
                if (!leftValid) return false;
            }
        }
        
        if (rightInterval != null) {
            if (node.right == null) {
                node.right = new Node(rightInterval, 1);
            } else {
                boolean rightValid = bookRec(rightInterval.start, end, node.right);
                if (!rightValid) return false;
            }
        }
        
        return true;
    }
    
    private Interval left(int start, int end, Node node) {
        if (start >= node.interval.start) return null;
        return new Interval(start, Math.min(end, node.interval.start));
    }
    
    private Interval right(int start, int end, Node node) { // 10, 40
        if (end < node.interval.end) return null;
        return new Interval(Math.max(start, node.interval.end), end); //20,40
    }
    
    private class Interval {
        int start;
        int end;
        
        Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }

        private boolean isInside(int checkedStart, int checkedEnd) {
            if (checkedEnd <= start) return false;
            if (checkedStart >= end) return false;
            
            return true;
        }

        private boolean isInside(Interval other) {
            if (other.end < start) return false;
            if (other.start >= end) return false;
            
            return true;
        }
    }
    
    private class Node {
        Interval interval;
        int count;
        Node left;
        Node right;
        
        Node(Interval interval, int count) {
            this.interval = interval;
            this.count = count;
        }
        
        boolean isInside(int start, int end) { // 10, 40
            return interval.isInside(start, end);
        }
    }

    public static void main(String[] args) {
        MyCalendarTwo cal = new MyCalendarTwo();

        System.out.println(cal.book(10,20));
        System.out.println(cal.book(50,60));
        System.out.println(cal.book(10,40));
        System.out.println(cal.book(5,15));
        System.out.println(cal.book(5,10));
        System.out.println(cal.book(25, 55));

        // cal.book(47, 50);
        // cal.book(1, 10);
        // cal.book(27, 36);
        // cal.book(40, 47);
        // cal.book(20, 27);
        // cal.book(15, 23);
        // cal.book(10, 18);
        // cal.book(27, 36);
        // cal.book(17, 25);
        // cal.book(8, 17);
        // cal.book(24, 33);
        // cal.book(23, 28);
        // cal.book(21, 27);
        // cal.book(47, 50);
        // cal.book(14,21);
        // cal.book(26,32);
        // cal.book(6,21);
        // cal.book(2, 7);
        

//         ["MyCalendarTwo","book","book","book","book","book","book","book","book","book","book","book","book","book","book","book","book","book","book","book","book","book","book","book","book","book","book","book","book","book","book"]
// [[],[47,50],[1,10],[27,36],[40,47],[20,27],[15,23],[10,18],[27,36],[17,25],[8,17],
// [24,33],[23,28],[21,27],[47,50],[14,21],[26,32],[16,21],[2,7],
// [24,33],[6,13],[44,50],[33,39],[30,36],[6,15],[21,27],[49,50],[38,45],[4,12],[46,50],[13,21]]
    }
}