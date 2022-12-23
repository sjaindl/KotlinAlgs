class SudokuSolver {
    char[][] board;
    int[] rows;
    int[] cols;
    int[] boxes;

    public void solveSudoku(char[][] board) {
        //1. def rows/cols/block Set<Int>[] availables
        this.board = board;
        solveSudoku(0, 0);
        
        for(int i = 0; i < 9; i++) {
            System.out.println(board[i]);    
        }
        
    }
    
    public boolean solveSudoku(int row, int col) {
        int nextCol = (col + 1) % board.length;
        int nextRow = row;
        if (nextCol == 0) {
            nextRow++;
        }
        
        if (!(row == 8 && col == 8) && board[row][col] != '.') {
            if (solveSudoku(nextRow, nextCol)) {
                return true;
            }
        }
        
        //1. def rows/cols/block Set<Int>[] availables
        boolean[] possibles = possibleNumbers(row, col);
        int possibleCount = 0;
        for (int index = 0; index < possibles.length; index++) {
            if (possibles[index]) {
                possibleCount++;
            }
        }
        
        if (row == 8 && col == 8) {
            if (board[row][col] != '.') {
                return true;
            } else if (possibleCount == 1) {
                for (int index = 0; index < possibles.length; index++) {
                    if (possibles[index]) {
                        board[row][col] = (char) ('0' + index + 1);
                        return true;
                    }
                }
            }
            
            return false;
        }
        
        for (int index = 0; index < possibles.length; index++) {
            if (!possibles[index]) {
                continue;
            }
            
            board[row][col] = (char) ('0' + index + 1);
            
            if(solveSudoku(nextRow, nextCol)) {
                return true;
            }
            
            board[row][col] = '.';
        }
        
        return false;
    }
    
    private boolean[] possibleNumbers(int row, int col) {
        boolean[] possible = new boolean[9];
        for (int index = 0; index < possible.length; index++) {
            possible[index] = true;
        }
        
        for (int colOfRow = 0; colOfRow < possible.length; colOfRow++) {
            char digit = board[row][colOfRow];
            if (digit != '.') {
                int number = (int) (digit - 1 - '0');
                possible[number] = false;
            }
        }
        
        for (int rowOfCol = 0; rowOfCol < possible.length; rowOfCol++) {
            char digit = board[rowOfCol][col];
            if (digit != '.') {
                int number = (int) (digit - 1 - '0');
                possible[number] = false;
            }
        }
        
        int blockRow = (int) (row / 3) * 3;
        int blockCol = (int) (col / 3)  * 3;
        for (int rowOfBlock = blockRow; rowOfBlock < blockRow + 3; rowOfBlock++) {
            for (int colOfBlock = blockCol; colOfBlock < blockCol + 3; colOfBlock++) {
                char digit = board[rowOfBlock][colOfBlock];
                if (digit != '.') {
                    int number = (int) (digit - 1 - '0');
                    possible[number] = false;
                }
            }
        }
        
        return possible;
    }

    public static void main(String[] args) {
        char[][] board = {
            {'5','3','.','.','7','.','.','.','.'},
            {'6','.','.','1','9','5','.','.','.'},
            {'.','9','8','.','.','.','.','6','.'},
            {'8','.','.','.','6','.','.','.','3'},
            {'4','.','.','8','.','3','.','.','1'},
            {'7','.','.','.','2','.','.','.','6'},
            {'.','6','.','.','.','.','2','8','.'},
            {'.','.','.','4','1','9','.','.','5'},
            {'.','.','.','.','8','.','.','7','9'}};

        SudokuSolver s = new SudokuSolver();
        s.solveSudoku(board);

        System.out.println(s.board);
    }
}
