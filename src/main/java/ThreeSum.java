import java.util.*;

public class ThreeSum {

    public static void main(String[] args) {
        ThreeSum sum = new ThreeSum();
        int[] nums = { -1, 0, 1, 2, -1, -4 };
        System.out.println(sum.threeSum(nums));
    }

    public List<List<Integer>> threeSum(int[] nums) {
        Set<Triple> resultSet = new HashSet();
        Map<Integer, Integer> counts = counts(nums);

        for (int firstIndex = 0; firstIndex < nums.length; firstIndex++) {
            for (int secondIndex = firstIndex + 1; secondIndex < nums.length; secondIndex++) {
                int searched = -(nums[firstIndex] + nums[secondIndex]);
                int indexCount = 0;
                
                if (nums[firstIndex] == searched) {
                    indexCount++;
                }
                if (nums[secondIndex] == searched) {
                    indexCount++;
                }
                
                Integer counterPartCount = counts.get(searched);
                if (counterPartCount != null && counterPartCount > indexCount) {
                    insertInOrderIntoTriple(firstIndex, secondIndex, searched, nums, resultSet);
                }
            }
        }
        
        return convertToList(resultSet);
    }

    private Map<Integer, Integer> counts(int[] nums) {
        Map<Integer, Integer> counts = new HashMap();
        
        for (int num : nums) {
            Integer curCount = counts.get(num);
            if (curCount == null) {
                counts.put(num, 1);
            } else {
                counts.put(num, curCount + 1);
            }
        }

        return counts;
    }
    
    private List<List<Integer>> convertToList(Set<Triple> resultSet) {
        List<List<Integer>> result = new ArrayList();
        
        for (Triple triple : resultSet) {
            List<Integer> list = new ArrayList();
            list.add(triple.first);
            list.add(triple.second);
            list.add(triple.third);
            
            result.add(list);
        }
        
        return result;
    }
    
    private void insertInOrderIntoTriple(int firstIndex, int secondIndex, int searched, int[] nums, Set<Triple> resultSet) {
        int[] triples = { nums[firstIndex], nums[secondIndex], searched };
        Arrays.sort(triples);

        Triple triple = new Triple(triples[0], triples[1], triples[2]);
        if (!resultSet.contains(triple)) {
            resultSet.add(triple);
        }
    }

    class Triple {
        int first;
        int second;
        int third;
    
        Triple(int first, int second, int third) {
            this.first = first;
            this.second = second;
            this.third = third;
        }
    
        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            } 
    
            if (!(obj instanceof Triple)) {
                return false;
            }
    
            Triple other = (Triple) obj;
    
            return other.first == this.first && other.second == this.second && other.third == this.third;
        }
    
        @Override
        public int hashCode() {
            return Objects.hash(first, second, third);
        }
    }    
}

