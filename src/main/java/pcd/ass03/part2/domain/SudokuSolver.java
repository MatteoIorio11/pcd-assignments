package pcd.ass03.part2.domain;

import java.util.Map;
import java.util.Optional;

public class SudokuSolver {
    /**
     * Solve a given sudoku board in a brute force fashion.
     * This implementation heavily relies on side effects and time complexity
     * both in space (recursion call for each possible solution) and
     * time can be highly optimized.
     *
     * @param board the Sudoku board to solve.
     * @return an optional representing the solution.
     */
    public static Optional<Board> solve(final Board board) {
        final var cells = board.getCells();
        final var solBoard = Board.fromCells(cells);
        return solveHelper(solBoard) ? Optional.of(solBoard) : Optional.empty();
    }

    /**
     * Finds a solution to the provided Sudoku board in a brute force fashion.
     *
     * @param board the Sudoku board to solve.
     * @return true if the solution is found, false otherwise.
     */
    private static boolean  solveHelper(final Board board) {
        final var optCell = findEmptyCell(board);

        // No free cells means the game is won
        if (optCell.isEmpty()) {
            return true;
        }

        final var emptyCell = optCell.get();
        for (var n = 1; n <= 9; n++) {
            if (Logic.isMoveAllowed(board, emptyCell, n)) {
                board.putValue(emptyCell, n);
                if (solveHelper(board)) return true;
            }
            board.putValue(emptyCell, -1); // restore cell if no solution can be found
        }
        return false;
    }

    private static Optional<Cell> findEmptyCell(final Board board) {
        return board.getCells().entrySet().stream()
                .filter(e -> e.getValue() != -1)
                .map(Map.Entry::getKey)
                .findFirst();
    }
}
