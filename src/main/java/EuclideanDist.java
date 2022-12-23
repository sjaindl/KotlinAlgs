import java.util.*;

public class EuclideanDist {
    int[] origin = new int[] { 0, 0 };
    
    public int[][] kClosest(int[][] points, int k) {
        if (points == null || k < 1 || k > points.length || points.length == 0) {
            return null;
        }
        
        PriorityQueue<Point> closest = new PriorityQueue<>(Comparator.reverseOrder());
        
        for (int[] point : points) {
            Point p = new Point(point);
            closest.add(p);
            if (closest.size() > k) {
                closest.poll();
            }
        }
        
        int[][] result = new int[k][];
        for (int index = 0; index < result.length; index++) {
            Point nextPoint = closest.poll();
            result[index] = nextPoint.point;
        }
        
        return result;
    }

    private double euclideanDistance(int[]point1, int[] point2) {
        double xDiff = (double) point1[0] - point2[0];
        double yDiff = (double) point1[1] - point2[1];
        return Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
    }
    
    class Point implements Comparable<Point> {
        int[] point;
        
        Point(int[] point) {
            this.point = point;
        }
        
        @Override
        public int compareTo(Point other) {
            return Double.compare(distanceToOrigin(), other.distanceToOrigin());
        }
        
        private Double distanceToOrigin() {
            return euclideanDistance(point, origin);
        }
    }

    public static void main(String[] args) {
        EuclideanDist dist = new EuclideanDist();
        int[][] points = new int[][] { new int[] { 1,3 }, new int[] { -2, 2 } };
        System.out.println(dist.kClosest(points, 1));
    }
}