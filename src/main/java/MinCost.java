import java.util.*;

public class MinCost {

    public double mincostToHireWorkers(int[] quality, int[] wage, int K) {
        if (quality == null || wage == null || quality.length == 0 || wage.length == 0 
           || quality.length != wage.length || K > quality.length) {
            return Double.MAX_VALUE;
        }
        
        Worker[] workers = new Worker[quality.length];
        for (int index = 0; index < quality.length; index++) {
            workers[index] = new Worker(quality[index], wage[index]);
        }
        
        // Sorted by ratio in descending order. So all are valid going with this order.
        Arrays.sort(workers);
        
        double min = Double.MAX_VALUE;
        PriorityQueue<Integer> qualityQueue = new PriorityQueue<>(Comparator.reverseOrder());
        double qualitySum = 0;
        // quality = [10,20,5], wage = [70,50,30], K = 2
        for (Worker worker : workers) {
            qualityQueue.add(worker.quality);
            qualitySum += worker.quality;
            
            if (qualityQueue.size() > K) {
                qualitySum -= qualityQueue.poll();
            }
            if (qualityQueue.size() == K) {
                min = Math.min(min, qualitySum * worker.ratio());
            }
            
        }
        
        return min;
    }

    class Worker implements Comparable<Worker> {
        int quality;
        int wage;
        
        Worker(int quality, int wage) {
            this.quality = quality;
            this.wage = wage;
        }
        
        @Override
        public int compareTo(Worker other) {
            return Double.compare(ratio(), other.ratio());
        }
        
        private double ratio() {
            return (double) wage / (double) quality;
        }
    }

    public double mincostToHireWorkers2(int[] quality, int[] wage, int K) {
        if (quality == null || wage == null || quality.length == 0 || wage.length == 0 
           || quality.length != wage.length || K > quality.length) {
            return Double.MAX_VALUE;
        }
        
        HashMap<IndexResult, Double> indexRes = new HashMap<>();
        //List<Worker> workers = new ArrayList<>();
        
        return mincostToHireWorkersRec(quality, wage, K, indexRes, null, 0, 0);
    }
    
    /*
    base case k = 0 -> return value;
        k + start index > return Double.MAX_VALUE
        double min = Double.MAX_VALUE;
        check index res map -> return if there
        for index = start index to quality.length:
            include if first element, or valid with first element (check ratio for sum, check min wage)
            min = Math.min(min, rec(k-1, set))
            remove element //backtrack
        update index res map with min
        
        return min
        */
    
    
    //quality = [10,20,5], wage = [70,50,30], K = 2
    private double mincostToHireWorkersRec(int[] quality, int[] wage, int remainingK, 
                                          Map<IndexResult, Double> indexRes,
                                          Worker worker, double sum, int curIndex) {
        //70
        if (remainingK == 0) {
            return sum;
        }

        if (quality.length == 1) {
            return wage[0];
        }
        
        double min = Double.MAX_VALUE;
        
        if (curIndex + remainingK > quality.length) {
            return min;
        }
        
        IndexResult result = new IndexResult(remainingK, curIndex);
        if (indexRes.containsKey(result)) {
            return indexRes.get(result) + sum;
        }

        double ratioAdjustment = 1;
        
        // quality = [10,20,5], wage = [70,50,30], K = 2
        for (int index = curIndex; index < quality.length; index++) { // 1,2
            //if (isValid(worker, quality[index], wage[index])) {
                
                double localSum = sumForWorker(worker, quality[index], wage[index]); // 70,100
                
                Worker localWorker = worker;
                if (localWorker == null) {
                    localWorker = new Worker(quality[index], wage[index]);
                }

                double recMin = mincostToHireWorkersRec(quality, wage, remainingK - 1, indexRes,
                                                        localWorker, sum + localSum, index + 1);
                min = Math.min(min, recMin);
            //}
        }
        
        indexRes.put(result, min); //140
        
        return min;
    }
    
    private boolean isValid(Worker worker, int quality, int minWage) {
        if (worker == null) return true;
        //Worker firstWoker = workers.get(0);
        double ratio = ((double) quality) / ((double) worker.quality); //2
        double wage = (worker.wage * ratio); //100
        return minWage >= wage;
    }
    
    private double sumForWorker(Worker worker, int quality, int wage) { //10,70
        if (worker == null) return (double) wage;
        //Worker firstWoker = workers.get(0); //10,70
        double ratio = ((double) quality) / ((double) worker.quality);
        return (worker.wage * ratio);
    }
    
    class IndexResult {
        int remainingK;
        int fromIndex;
        
        IndexResult(int remainingK, int fromIndex) {
            this.remainingK = remainingK;
            this.fromIndex = fromIndex;
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(remainingK, fromIndex);
        }
        
        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof IndexResult)) return false;
            if (this == obj) return true;
            IndexResult other = (IndexResult) obj;
            return other.remainingK == remainingK && other.fromIndex == fromIndex;
        }
    }

    public static void main(String[] args) {
        MinCost mincost = new MinCost();
        // System.out.println(mincost.mincostToHireWorkers(new int[] { 4,5 }, new int[] { 8,14 }, 2));
        // System.out.println(mincost.mincostToHireWorkers(new int[] { 2 }, new int[] { 14 }, 1));
        System.out.println(mincost.mincostToHireWorkers(new int[] { 10,20,5 }, new int[] { 70,50,30 }, 2));
    }
}
