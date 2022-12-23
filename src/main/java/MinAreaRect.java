import java.util.*;

public class MinAreaRect {
    public int minAreaRect(int[][] points) {
        if (points == null || points.length == 0) return 0;
        
        Edge topLeft = new Edge(copyArray(points));
        Edge topRight = new Edge(copyArray(points));
        Edge bottomLeft = new Edge(copyArray(points));
        Edge bottomRight = new Edge(copyArray(points));
        
        sortEdges(topLeft, topRight, bottomLeft, bottomRight);
        
        while (true) {
            int minRow = Math.max(topLeft.curPoint()[0], bottomLeft.curPoint()[0]);
            int maxRow = Math.max(topRight.curPoint()[0], bottomRight.curPoint()[0]);
            int minCol = Math.max(bottomLeft.curPoint()[1], bottomRight.curPoint()[1]);
            int maxCol = Math.max(topLeft.curPoint()[1], topRight.curPoint()[1]);
            
            if (minRow >= maxRow || minCol >= maxCol) return 0;
            
            if (isValid(topLeft.curPoint(), topRight.curPoint(), 
                        bottomLeft.curPoint(), bottomRight.curPoint())) {
                return area(topLeft.curPoint(), topRight.curPoint(), 
                            bottomLeft.curPoint(), bottomRight.curPoint());
            }
            
            if (topLeft.curPoint()[0] < minRow || topLeft.curPoint()[1] > maxCol) topLeft.moveOn();
            if (topRight.curPoint()[0] > maxRow || topRight.curPoint()[1] > maxCol) topRight.moveOn();
            if (bottomLeft.curPoint()[0] < minRow 
                || bottomLeft.curPoint()[1] < minCol) bottomLeft.moveOn();
            if (bottomRight.curPoint()[0] < minRow 
                || bottomRight.curPoint()[1] > maxCol) bottomRight.moveOn();
        }
    }
    
    private boolean isValid(int[] topLeft, int[] topRight, int[] bottomLeft, int[] bottomRight) {
        return topLeft[0] == bottomLeft[0] && topLeft[1] == topRight[1] 
            && topRight[0] == bottomRight[0] && bottomRight[1] == bottomLeft[1];
    }
    
    private int area(int[] topLeft, int[] topRight, int[] bottomLeft, int[] bottomRight) {
        int rowDiff = topRight[0] - topLeft[0];
        int colDiff = topLeft[1] - bottomLeft[1];
        
        return rowDiff * colDiff;
    }
    
    private void sortEdges(Edge topLeft, Edge topRight, Edge bottomLeft, Edge bottomRight) {
        Arrays.sort(topLeft.points, (int[] first, int[] second) -> 
                    first[0] == second[0] ? second[1] - first[1] : first[0] - second[0]);
        
        Arrays.sort(topRight.points, (int[] first, int[] second) -> 
                    first[0] == second[0] ? second[1] - first[1] : second[0] - first[0]);
        
        Arrays.sort(bottomLeft.points, (int[] first, int[] second) -> 
                    first[0] == second[0] ? first[1] - second[1] : first[0] - second[0]);            
        
        Arrays.sort(bottomRight.points, (int[] first, int[] second) -> 
                    first[0] == second[0] ? first[1] - second[1] : second[0] - first[0]);
                    
    }
    
    private int[][] copyArray(int[][] points) {
        int[][] copy = new int[points.length][2];
        
        for (int index = 0; index < points.length; index++) {
            copy[index][0] = points[index][0];
            copy[index][1] = points[index][1];
        }
        
        return copy;
    }
    
    class Edge {
        int[][] points;
        private int curIndex = 0;
        
        Edge(int[][] points) {
            this.points = points;
        }
        
        int[] curPoint() {
            return points[curIndex];
        }
        
        boolean moveOn() {
            if (curIndex >= points.length) return false;
            curIndex++;
            return true;
        }
    }

    public int minAreaRect2(int[][] points) {
        Map<RowInfo, Integer> rowInfoMap = new HashMap<>(); //mapping column span : row
        TreeMap<Integer, List<Integer>> rows = new TreeMap<>();
        //Arrays.sort(points, (p1, p2) -> p1[0] < p2[0]); //sort by rows asc.

        //build rows with columns asc.
        for (int[] point : points) {
            Integer row = point[0];
            Integer col = point[1];
            List<Integer> columns = rows.getOrDefault(row, new ArrayList<>());
            columns.add(col);
            rows.put(row, columns);
        }

        int minArea = Integer.MAX_VALUE;

        for (Map.Entry<Integer, List<Integer>> entry : rows.entrySet()) {
            Integer row = entry.getKey();
            List<Integer> columns = entry.getValue();
            Collections.sort(columns);
            for (int columnOneIndex = 0; columnOneIndex < columns.size(); columnOneIndex++) {
                for (int columnTwoIndex = columnOneIndex + 1; columnTwoIndex < columns.size(); columnTwoIndex++) {
                    RowInfo rowInfo = new RowInfo(columns.get(columnOneIndex), columns.get(columnTwoIndex));
                    if (rowInfoMap.containsKey(rowInfo)) {
                        int colSpan = rowInfo.colSpan();
                        int lastRow = rowInfoMap.get(rowInfo);
                        int rowSpan = row - lastRow;
                        minArea = Math.min(minArea, rowSpan * colSpan);
                    }
                    rowInfoMap.put(rowInfo, row);
                }
            }
        }

        return minArea == Integer.MAX_VALUE ? 0 : minArea;
    }

    class RowInfo {
        int firstColumn;
        int secondColumn;

        RowInfo(int firstColumn, int secondColumn) {
            this.firstColumn = firstColumn;
            this.secondColumn = secondColumn;
        }

        int colSpan() {
            return Math.abs(firstColumn - secondColumn);
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof RowInfo)) return false;
            if (this == obj) return true;
            RowInfo other = (RowInfo) obj;
            return this.firstColumn == other.firstColumn && this.secondColumn == other.secondColumn;
        }

        @Override
        public int hashCode() {
            return Objects.hash(firstColumn, secondColumn);
        }
    }

    public static void main(String[] args) {
        MinAreaRect minAreaRect = new MinAreaRect();
        System.out.println(minAreaRect.minAreaRect2(new int[][] {
            { 1,1 }, { 1,3 }, { 3,1 }, { 3,3 }, { 2,2 }
        }));

        System.out.println(minAreaRect.minAreaRect2(new int[][] {
            {1,1},{1,3},{3,1},{3,3},{4,1},{4,3}
        }));


        
    }
}

/*

	3		x		x									
	2			x			Bucketsort:	buckets are rows		NO!				
y	1		x		x									
	0													
		0	1	2	3		4 sorted lists/arrays			O(4 n log n) = O(n log n)				
		x						topleft		4 lists with diff. Lambda expr.				
								topright						
								bottomleft		return first valid match				
								bottomright		check valid function (same rows/cols check)				O(1)
										if !match: try next .. Depending on missing row/col				
										while top cols > bottom cols && left rows > right rows				
										else return 0				
										O(n) runtime	
                                        
                                    YES!

*/