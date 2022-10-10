/*
https://codingcompetitions.withgoogle.com/kickstart/round/00000000008f49d7/0000000000bcf0fd

Banny has just bought a new programmable robot. Eager to test his coding skills, he has placed the robot in a grid of squares with R rows (numbered 1 to R from north to south) and C columns (numbered 1 to C from west to east). The square in row r and column c is denoted (r,c).

Initially the robot starts in the square (SR, SC). Banny will give the robot N instructions. Each instruction is one of N, S, E, or W, instructing the robot to move one square north, south, east, or west respectively.

If the robot moves into a square that it has been in before, the robot will continue moving in the same direction until it reaches a square that it has not been in before. Banny will never give the robot an instruction that will cause it to move out of the grid.

Can you help Banny determine which square the robot will finish in, after following the N instructions?

Input
The first line of the input gives the number of test cases, T. T test cases follow. Each test case starts with a line containing the five integers N, R, C, SR, and SC, the number of instructions, the number of rows, the number of columns, the robot's starting row, and the robot's starting column, respectively.

Then, another line follows containing a single string consisting of N characters; the i-th of these characters is the i-th instruction Banny gives the robot (one of N, S, E, or W, as described above).

Output
For each test case, output one line containing Case #x: r c, where x is the test case number (starting from 1), r is the row the robot finishes in, and c is the column the robot finishes in.

        Limits
Memory limit: 1 GB.
1≤T≤100.
1≤R≤5×104.
1≤C≤5×104.
1≤SR≤R.
1≤SC≤C.
The instructions will not cause the robot to move out of the grid.
Test Set 1
Time limit: 20 seconds.
1≤N≤100.
Test Set 2
Time limit: 60 seconds.
1≤N≤5×104.
Sample
Sample Input
        save_alt
content_copy
3
5 3 6 2 3
EEWNS
4 3 3 1 1
SESE
11 5 8 3 4
NEESSWWNESE
Sample Output
        save_alt
content_copy
Case #1: 3 2
Case #2: 3 3
Case #3: 3 7
Sample Case #1 corresponds to the top-left diagram, Sample Case #2 corresponds to the top-right diagram, and Sample Case #3 corresponds to the lower diagram. In each diagram, the yellow square is the square the robot starts in, while the green square is the square the robot finishes in.
 */

var line = 0
var args: Array<String?> = arrayOf()

val deltas = mapOf(
    //row, col
    'N' to (-1 to 0),
    'E' to (0 to 1),
    'S' to (1 to 0),
    'W' to (0 to -1)
)

fun readLine(): String? {
    val result = args[line]
    line++
    return result
}

data class Cell(val row: Int, val col: Int)

// Space: O(N)
// Time: O(N), where N = # of instructions
// We jump over already visited cells
fun main(mainArgs: Array<String?>) {
    args = mainArgs
    line = 0

    val testCases = readLine()?.toInt() ?: 0

    for (testCase in 1 until testCases + 1) {
        val (numInstructions, rows, columns, startRow, startCol) =
            readLine()?.split(" ")?.map { it.toInt() } ?: continue

        val instructions = readLine() ?: continue

        var curCell = Cell(startRow - 1, startCol - 1) // 0-indexed

        // mapping each cell to possible jumps (row or col) for given directions N/E/S/W
        val jumpMap: MutableMap<Cell, MutableMap<Char, Cell>> = mutableMapOf()
        jumpMap[curCell] = mutableMapOf()

        println("start at row ${curCell.row + 1} col ${curCell.col + 1}")

        for (direction in instructions) {
            val prevCell = curCell

            var jumpsFromCurCell = jumpMap[curCell]

            curCell = jump(jumpsFromCurCell, direction, curCell.row, curCell.col)
            while (jumpMap.contains(curCell)) {
                jumpsFromCurCell = jumpMap[curCell]
                curCell = jump(jumpsFromCurCell, direction, curCell.row, curCell.col)
            }

            jumpMap[curCell] = mutableMapOf()
            addJump(jumpMap[curCell]!!, oppositeDirection(direction), prevCell.row, prevCell.col)
            addJump(jumpMap[Cell(prevCell.row, prevCell.col)]!!, direction, curCell.row, curCell.col)

            println("jump $direction to row ${curCell.row + 1} col ${curCell.col + 1}")
        }

        println("Case #$testCase: ${curCell.row + 1} ${curCell.col + 1}")
    }
}

// Either we already know a jump, or we move by 1
fun jump(jumps: MutableMap<Char, Cell>?, direction: Char, row: Int, col: Int): Cell {
    val delta = deltas[direction] ?: throw IllegalArgumentException("Illegal Instruction")

    jumps?.get(direction)?.let {
        return it
    }

    return Cell(row + delta.first, col + delta.second)
}

fun addJump(jumps: MutableMap<Char, Cell>, direction: Char, row: Int, col: Int) {
    val delta = deltas[direction] ?: throw IllegalArgumentException("Illegal Instruction")

    jumps[direction] = Cell(row + delta.first, col + delta.second)
}

fun oppositeDirection(direction: Char): Char {
    return when (direction) {
        'N' -> 'S'
        'S' -> 'N'
        'E' -> 'W'
        'W' -> 'E'
        else -> throw IllegalArgumentException("Illegal direction given")
    }
}

main(arrayOf("3", "5 3 6 2 3", "EEWNS", "4 3 3 1 1", "SESE", "11 5 8 3 4", "NEESSWWNESE"))
//main(arrayOf("1", "5 3 6 2 3", "EWEWEW"))

// Space: O(N), where N = # of instructions (Optimized from O(RxC) for matrix)
// Time: O(N^2) because of possible duplicate moves .. EWEWEWE etc..
fun mainSlow(mainArgs: Array<String?>) {
    args = mainArgs
    line = 0

    val testCases = readLine()!!.toInt()

    // println(testCases)

    for (testCase in 1 until testCases + 1) {
        val (numInstructions, rows, columns, startRow, startCol) = readLine()!!.split(" ")
            .map { it.toInt() }
        val instructions = readLine()!!

        //println("numInstructions $numInstructions rows $rows columns $columns startRow $startRow startCol $startCol")

        var curRow = startRow - 1
        var curCol = startCol - 1

        //val visited = Array(rows) { BooleanArray(columns) }
        //visited[curRow][curCol] = true

        val visited: MutableSet<Cell> = hashSetOf()
        visited.add(Cell(curRow, curCol))

        //println(visited)
        //println(instructions)
        try {
            for (instruction in instructions) {
                //println(instruction)

                when (instruction) {
                    'N' -> {
                        // go rows up
                        while (curRow > 0 && visited.contains(Cell(curRow, curCol))) {
                            curRow--
                        }
                    }
                    'E' -> {
                        // go cols right
                        while (curCol <= columns && visited.contains(Cell(curRow, curCol))) {
                            curCol++
                        }
                    }
                    'S' -> {
                        // go rows down
                        while (curRow < rows - 1 && visited.contains(Cell(curRow, curCol))) {
                            curRow++
                        }
                    }
                    'W' -> {
                        // go cols left
                        while (curCol > 0 && visited.contains(Cell(curRow, curCol))) {
                            curCol--
                        }
                    }
                }

                visited.add(Cell(curRow, curCol))

                //println("visit $curRow:$curCol")


            }
            println("Case #$testCase: ${curRow + 1} ${curCol + 1}")
        } catch (ex: Exception) {

        }
    }
}