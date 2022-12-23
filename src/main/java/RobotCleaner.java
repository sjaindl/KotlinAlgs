/**
 * // This is the robot's control interface.
 * // You should not implement it, or speculate about its implementation
 * interface Robot {
 *     // Returns true if the cell in front is open and robot moves into the cell.
 *     // Returns false if the cell in front is blocked and robot stays in the current cell.
 *     public boolean move();
 *
 *     // Robot will stay in the same cell after calling turnLeft/turnRight.
 *     // Each turn will be 90 degrees.
 *     public void turnLeft();
 *     public void turnRight();
 *
 *     // Clean the current cell.
 *     public void clean();
 * }
 */

class Solution {
    //int visitingCount = 0;
    int directions = 4;
    
    public void cleanRoom(Robot robot) {
        HashMap<RoomIndex, CellState> cleanMap = new HashMap<>();
        RoomIndex start = new RoomIndex(0, 0);
        
        cleanRoomRec(robot, cleanMap, start, Direction.TOP);
    }
    
    private void cleanRoomRec(Robot robot, HashMap<RoomIndex, CellState> cleanMap, 
                              RoomIndex curIndex, Direction direction) {
        robot.clean();
        cleanMap.put(curIndex, CellState.VISITING);
        //visitingCount++;
        //boolean hasValidNeighbourCells = false;
        
        //System.out.println(curIndex.row + ":" + curIndex.col);
        
        for (int index = 0; index < directions; index++) {
            Direction newDir = dirForIndex(index, direction);
            int[] offset = offsetForDirection(newDir);
            RoomIndex newRoomIndex 
                = new RoomIndex(curIndex.row + offset[0], curIndex.col + offset[1]);
            
            if (index != 0) robot.turnRight();
            
            //visited, visiting or obstacle. always try new cells:
            if (cleanMap.containsKey(newRoomIndex)) continue; 
            
            boolean accessible = robot.move();
            if (!accessible) {
                cleanMap.put(newRoomIndex, CellState.OBSTACLE);
                continue;
            }
            
            cleanRoomRec(robot, cleanMap, newRoomIndex, newDir);
            //hasValidNeighbourCells = true;
        }
        
        cleanMap.put(curIndex, CellState.VISITED);
        //visitingCount--;
        robot.turnLeft(); //backtrack to original position
        robot.move();
        robot.turnRight();
        robot.turnRight();
        
        //System.out.println("backtrack " + curIndex.row + ":" + curIndex.col);
    }
    
    private Direction dirForIndex(int index, Direction from) {
        return Direction.valueOf((index + from.getValue()) % directions);
    }
    
    private int[] offsetForDirection(Direction direction) {
        switch (direction) {
            case TOP: return new int[] { -1, 0 }; //row, col offset
            case RIGHT: return new int[] { 0, 1 };
            case BOTTOM: return new int[] { 1, 0 };
            case LEFT: return new int[] { 0, -1 };
            default:  return new int[] { 0, 0 };
        }
    }
    
    class RoomIndex {
        int row;
        int col;
        
        RoomIndex(int row, int col) {
            this.row = row;
            this.col = col;
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }
        
        @Override
        public boolean equals(Object oth) {
            if (!(oth instanceof RoomIndex)) return false;
            if (oth == this) return true;
            
            RoomIndex other = (RoomIndex) oth;
            return this.row == other.row && this.col == other.col;
        }
    }
    
    enum CellState {
        VISITING,
        VISITED,
        OBSTACLE
    }
    
    enum Direction {
        TOP(0),
        RIGHT(1),
        BOTTOM(2),
        LEFT(3);
        
        private int value;
        private static Map map = new HashMap<>();

        private Direction(int value) {
            this.value = value;
        }

        static {
            for (Direction direction : Direction.values()) {
                map.put(direction.value, direction);
            }
        }

        public static Direction valueOf(int direction) {
            return (Direction) map.get(direction);
        }

        public int getValue() {
            return value;
        }
    }
    
    // DFS
        // inital row/col = 0/0
        /*
            1. try move() -> if false: turnRight(), then move() ..
            better: try each 4 directions -> always use new cells. 
                        if only visiting or visited left -> backtrack.
            
            -> class RoomIndex { int row; int col; }
            CellState { visiting, visited, obstacle }. new doesn't exist (once discovered -> visiting)
            HashMap<RoomIndex, CellState>
            
            rec. gen. use turnRight, only turnLeft if left room is only accessible (=visiting)
            clean new cells + put in hashmap as visiting. when loop finished -> visited.
            
            completed if all cells have state visited or obstacle. hold visiting count -> if 0, terminate.
        */
}