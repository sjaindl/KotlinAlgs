class NumMatrix {

    int[][] matrix;
    
    public NumMatrix(int[][] matrix) {
        this.matrix = matrix;
        
        updateSumsFrom(0, 0);
    }
    
    public void update(int row, int col, int val) {
        if (!isInBounds(row, col)) return;
        
        matrix[row][col] = val;
        updateSumsFrom(row, col);
    }
    
    public int sumRegion(int row1, int col1, int row2, int col2) {
        if (!isInBounds(row1, col1) || !isInBounds(row2, col2)) return 0;
        
        /*
        for (int row = row1; row <= row2; row++) {
            for (int col = col1; col <= col2; col++) {
                sum += matrix[row][col];
            }
        }
        */
        int sumTo = sumAt(row2, col2);
        int topSum = sumAt(row1 - 1, col2);
        int leftSum = sumAt(row2, col1 - 1);
        int diagoSum = sumAt(row1 - 1, col1 - 1);
        
        return sumTo - topSum -leftSum + diagoSum;
    }
    
    private boolean isInBounds(int row, int col) {
        return row >= 0 && col >= 0 && row < matrix.length && col < matrix[0].length;
    }
    
    private void updateSumsFrom(int startRow, int startCol) {
        for (int row = startRow; row < matrix.length; row++) {
            for (int col = startCol; col < matrix[0].length; col++) {
                matrix[row][col] = sumAt(row, col);
            }
        }
    }
    
    private int sumAt(int row, int col) {
        int leftSum = sumTo(row -1, col);
        int topSum = sumTo(row, col - 1);
        int diagoSum = sumTo(row - 1, col -1);
        return leftSum + topSum - diagoSum + matrix[row][col];
    }
    
    private int sumTo(int row, int col) {
        if (!isInBounds(row, col)) return 0;
        
        return matrix[row][col];
    }

    public static void main(String[] args) {
        int[][] matrix = {
            { 1 }
        };

        NumMatrix numMatrix = new NumMatrix(matrix);
        System.out.println(numMatrix.;
    }
}

/**
 * Your NumMatrix object will be instantiated and called as such:
 * NumMatrix obj = new NumMatrix(matrix);
 * obj.update(row,col,val);
 * int param_2 = obj.sumRegion(row1,col1,row2,col2);
 */