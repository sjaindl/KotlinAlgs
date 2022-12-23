import java.util.*;

public class OddEvenJump {
    public int oddEvenJumps(int[] A) {
        int N = A.length;
        if (N <= 1) return N;
        boolean[] odd = new boolean[N];
        boolean[] even = new boolean[N];
        odd[N-1] = even[N-1] = true;

        TreeMap<Integer, Integer> vals = new TreeMap();
        vals.put(A[N-1], N-1);
        for (int i = N-2; i >= 0; --i) {
            int v = A[i];
            if (vals.containsKey(v)) {
                odd[i] = even[vals.get(v)];
                even[i] = odd[vals.get(v)];
            } else {
                Integer lower = vals.lowerKey(v);
                Integer higher = vals.higherKey(v);

                if (lower != null)
                    even[i] = odd[vals.get(lower)];
                if (higher != null) {
                    odd[i] = even[vals.get(higher)];
                }
            }
            vals.put(v, i);
        }

        int ans = 0;
        for (boolean b: odd)
            if (b) ans++;
        return ans;
    }

    public int oddEvenJumpsTreeMapSet(int[] arr) {
        if (arr == null) return 0;
        if (arr.length < 2) return arr.length;
        
        TreeMap<Integer, TreeSet<Integer>> tree = new TreeMap<>();
        addToTree(tree, arr.length - 1, arr[arr.length - 1]);
        
        int valid = 1;
        for (int index = arr.length - 2; index >= 0; index--) {
            Integer curIndex = index;
            boolean odd = true;
            TreeSet<Integer> restore = null;
            while (curIndex < arr.length) {
                restore = tree.get(arr[curIndex]);
                if (restore != null) {
                    restore.remove(curIndex);
                    if (restore.isEmpty()) {
                        tree.remove(arr[curIndex]);
                    }
                }

                if (odd) {
                    Map.Entry<Integer, TreeSet<Integer>> higherEntry = tree.higherEntry(arr[curIndex] - 1);
                    if (higherEntry == null) break;
                    TreeSet<Integer> higherSet = higherEntry.getValue();
                    Integer higherIndex = higherSet.higher(curIndex);
                    if (higherIndex == null) break;
                    if (higherIndex == arr.length - 1) {
                        valid++;
                        break;
                    }
                    curIndex = higherIndex;
                } else {
                    Map.Entry<Integer, TreeSet<Integer>> lowerEntry = tree.lowerEntry(arr[curIndex] + 1);
                    if (lowerEntry == null) break;
                    TreeSet<Integer> lowerSet = lowerEntry.getValue();
                    Integer lowerIndex = lowerSet.higher(curIndex);
                    if (lowerIndex == null) break;
                    if (lowerIndex == arr.length - 1) {
                        valid++;
                        break;
                    }
                    curIndex = lowerIndex;
                }
                if (restore != null) {
                    restore.add(curIndex);
                    addToTree(tree, curIndex, arr[curIndex]);
                    restore = null;
                }

                odd = !odd;
            }
            if (restore != null) {
                restore.add(curIndex);
                addToTree(tree, curIndex, arr[curIndex]);
                restore = null;
            }
            addToTree(tree, index, arr[index]);
        }
        
        return valid;
    }
    
    private void addToTree(TreeMap<Integer, TreeSet<Integer>> tree, Integer index, Integer value) {
        TreeSet<Integer> newSet = tree.getOrDefault(value, new TreeSet<>());
        newSet.add(index);
        tree.put(value, newSet);
    }

    public int oddEvenJumps2(int[] arr) {
        if (arr == null) return 0;
        if (arr.length < 2) return arr.length;
        
        TreeSet<Element> tree = buildTree(arr);
        
        int valid = 1;
        for (int index = arr.length - 2; index >= 0; index--) {
            boolean odd = true;
            Integer next = index;
            
            while (next != null) {
                next = findNext(tree, odd, next, arr[next]);
                odd = !odd;
                if (next == null) {
                    break;
                } else if (next == arr.length - 1) {
                    valid++;
                    break;
                }
            }
        }
        
        return valid;
    }
    
    private TreeSet<Element> buildTree(int[] arr) {
        TreeSet<Element> tree = new TreeSet<>();
        
        for (int index = 0; index < arr.length; index++) {
            tree.add(new Element(index, arr[index]));
        }
        
        return tree;
    }
    
    private Integer findNext(TreeSet<Element> tree, boolean isOdd, int fromIndex, int value) {
        Integer bestValue = null;
        Integer bestIndex = null;
        
        if (isOdd) {
            for (Element element : tree.tailSet(new Element(fromIndex, value), true)) {
                if (element.index <= fromIndex) continue;
                if (bestValue != null && element.value != bestValue) break;
                bestValue = element.value;
                bestIndex = element.index;
            }
        } else {
            for (Element element : tree.headSet(new Element(fromIndex, value), true)) {
                if (element.index <= fromIndex) continue;
                if (bestValue != null && element.value != bestValue) break;
                bestValue = element.value;
                bestIndex = element.index;
            }
        }
        
        /*
        for (Element element : tree) {
            if (element.index <= fromIndex) continue;
            
            if (isOdd && element.value >= value) { //candidate
                if (bestValue == null || element.value < bestValue) {
                    bestValue = element.value;
                    bestIndex = element.index;
                }
            } else if (!isOdd && element.value <= value) { //candidate
                if (bestValue == null || element.value > bestValue) {
                    bestValue = element.value;
                    bestIndex = element.index;
                }
            }
        }
        */
        
        return bestIndex;
    }
    
    class Element implements Comparable {
        int index;
        int value;
        
        Element(int index, int value) {
            this.index = index;
            this.value = value;
        }
        
        @Override
        public int compareTo(Object other) {
            if (other == null || !(other instanceof Element)) return 1;
            Element oth = (Element) other;
            
            if (oth.value == value) return 0;
            else if (value < oth.value) return -1;
            else return 1;
        }

        @Override
        public boolean equals(Object other) {
            if (other == null || !(other instanceof Element)) return false;
            if (other == this) return true;
            Element oth = (Element) other;
            
            return oth.index == index && oth.value == value;
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(index, value);
        }
    }

    public static void main(String[] args) {
        OddEvenJump oddEvenJump = new OddEvenJump();
        System.out.println(oddEvenJump.oddEvenJumps(new int[] { 10,13,12,14,15 }));
        System.out.println(oddEvenJump.oddEvenJumps(new int[] { 5,1,3,4,2 }));
        System.out.println(oddEvenJump.oddEvenJumps(new int[] { 2,3,1,1,4 }));
    }
        
    public int oddEvenJumpsBF(int[] arr) {
        if (arr == null) return 0;
        if (arr.length < 2) return arr.length;
        
        Set<Jump> invalids = new HashSet<>();
        
        int valid = 0;
        for (int index = arr.length - 1; index >= 0; index--) {
            Integer curIndex = index;
            boolean isOddJump = true;
            
            while (curIndex != null) {
                Jump jump = new Jump(curIndex, isOddJump);
                if (invalids.contains(jump)) break;

                if (curIndex == arr.length - 1) {
                    valid++;
                    break;
                } else {
                    curIndex = nextIndex(arr, isOddJump, curIndex);
                }
                
                if (curIndex == null) invalids.add(jump);
                isOddJump = !isOddJump;
            }
        }
        
        return valid;
    }
    
    private Integer nextIndex(int[] arr, boolean isOddJump, int curIndex) {
        int value = arr[curIndex];
        Integer bestValue = null;
        Integer bestIndex = null;
        for (int index = curIndex + 1; index < arr.length; index++) {
            if (isOddJump && arr[index] >= value) { //candidate
                if (bestValue == null || arr[index] < bestValue) {
                    bestValue = arr[index];
                    bestIndex = index;
                }
            } else if (!isOddJump && arr[index] <= value) { //candidate
                if (bestValue == null || arr[index] > bestValue) {
                    bestValue = arr[index];
                    bestIndex = index;
                }
            }
        }
        
        return bestIndex;
    }
    
    class Jump {
        int index;
        boolean odd;
        
        Jump(int index, boolean odd) {
            this.index = index;
            this.odd = odd;
        }
        
        @Override
        public boolean equals(Object other) {
            if (other == null || !(other instanceof Jump)) return false;
            if (other == this) return true;
            Jump oth = (Jump) other;
            
            return oth.index == index && oth.odd == odd;
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(index, odd);
        }
    }
}
