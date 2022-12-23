import java.util.*;

class RandomizedSet {
    
    static final int MIN_CAPACITY = 4;
    static final int DEFAULT_CAPACITY = 8;
        
    int size = 0; // 2
    int[] values;
    Map<Integer, Integer> valueToIndexMapping;
    Random random;
    
    /** Initialize your data structure here. */
    public RandomizedSet() {
        values = new int[DEFAULT_CAPACITY]; //[1]
        valueToIndexMapping = new HashMap<>(); // { 2:0 }
        random = new Random();
    }
    
    //["RandomizedSet","insert","remove","insert","getRandom","remove","insert","getRandom"]
    // [[],[1],[2],[2],[],[1],[2],[]]
    
    /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
    public boolean insert(int val) {
        /*
        insert: if (contains(value) return false)
            append to values[]
            insert in map:  { value:index from array } .. O(1)
            upsize array, if necessary
        */
        Integer key = val; //2
        if (valueToIndexMapping.containsKey(key)) return false;
        
        if (isFull()) upsize();
        
        valueToIndexMapping.put(key, size);
        values[size] = key;
        size++;
        
        return true;
    }
    
    /** Removes a value from the set. Returns true if the set contained the specified element. */
    public boolean remove(int val) {
        /*
        remove: if (!contains(value) return false)
            lookup array index from map
            exchange element at index in array with last array element.
            downsize array, if necessary
        */
        
        Integer key = val; //1
        if (!valueToIndexMapping.containsKey(key)) return false;
        
        int index = valueToIndexMapping.get(key); //0
        valueToIndexMapping.remove(key);
        
        moveElementToEnd(index);
        size--;
        
        if (isMaxQuarterFull()) downsize();
        
        return true;
    }
    
    /** Get a random element from the set. */
    public int getRandom() {
        /*
        getRandom: size of set .. gen random nr from (0 ..< size of set) .. O(1)
            return values[random];
        */
        
        int randomIndex = random.nextInt(size); //0 or 1
        return values[randomIndex];
    }
    
    private boolean isFull() {
        return size == values.length;
    }
    
    private boolean isMaxQuarterFull() {
        return size <= values.length / 4;
    }
    
    private void upsize() {
        int[] newValues = new int[values.length * 2];
        System.arraycopy(values, 0, newValues, 0, size);
        values = newValues;
    }
    
    private void downsize() {
        if (size < MIN_CAPACITY) return;
        int newSize = Math.max(MIN_CAPACITY, values.length / 2);
        int[] newValues = new int[newSize];
        System.arraycopy(values, 0, newValues, 0, size);
        values = newValues;
    }
    
    private void moveElementToEnd(Integer index) {
        if (index == size - 1) return;
        
        Integer temp = values[size - 1]; //2
        values[size - 1] = values[index]; 
        values[index] = temp;
        
        valueToIndexMapping.put(temp, index);
    }

    public static void main(String[] args) {
        RandomizedSet obj = new RandomizedSet();
        // System.out.println(obj.insert(1));
        // System.out.println(obj.remove(2));
        // System.out.println(obj.insert(2));
        // System.out.println(obj.getRandom());
        // System.out.println(obj.remove(1));
        // System.out.println(obj.insert(2));
        // System.out.println(obj.getRandom());

        System.out.println(obj.remove(0));
        System.out.println(obj.remove(0));
        System.out.println(obj.insert(0));
        System.out.println(obj.getRandom());
        System.out.println(obj.remove(0));
        System.out.println(obj.insert(0));
    }

    /*
    ["RandomizedSet","remove","remove","insert","getRandom","remove","insert"]
    [[],[0],[0],[0],[],[0],[0]]
    */
}
