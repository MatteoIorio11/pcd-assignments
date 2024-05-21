package pcd.ass03.part2.domain;

import scala.Int;

import java.awt.*;
import java.sql.Time;
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
            if (board.putValue(emptyCell, n)) {
                if (solveHelper(board)) return true;
            }
            board.removeValue(emptyCell);
            //board.getCells().put(emptyCell, Board.EMPTY_CELL); // restore cell if no solution can be found
        }
        return false;
    }

    private static Optional<Cell> findEmptyCell(final Board board) {
        return board.getCells().entrySet().stream()
                .filter(e -> e.getValue() == Board.EMPTY_CELL)
                .map(Map.Entry::getKey)
                .findFirst();
    }

    public static void printSudokuBoard(Map<Cell, Integer> sudokuMap) {
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0 && i != 0) {
                System.out.println("+-------+-------+-------+");
            }
            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0 && j != 0) {
                    System.out.print("| ");
                }
                Cell position = new Cell(i, j);
                Integer value = sudokuMap.getOrDefault(position, 0);
                System.out.print(value == -1 ? "_ " : value + " ");
            }
            System.out.println();
        }
    }
}

