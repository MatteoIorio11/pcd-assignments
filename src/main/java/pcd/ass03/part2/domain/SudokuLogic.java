package pcd.ass03.part2.domain;

import java.util.Objects;
import java.util.stream.IntStream;

/**
 * Implements Sudoku's logic (i.e. if moves is legit)
 */
public class SudokuLogic {
    private SudokuLogic() {}

    public static boolean isMoveAllowed(final Board board, final Cell cell, final int number) {
        return checkCol(board, cell, number)
            && checkRow(board, cell, number)
            && checkSubGrid(board, cell, number);
    }

    private static boolean checkRow(final Board board, final Cell cell, final int number) {
        final var grid = board.getCells();
        return IntStream.range(0, 9)
                .filter(row -> grid.get(new Cell(row, cell.j())) != null)
                .noneMatch(row -> grid.get(new Cell(row, cell.j())) == number);
    }

    private static boolean checkCol(final Board board, final Cell cell, final int number) {
        final var grid = board.getCells();
        return IntStream.range(0, 9)
                .filter(col -> grid.get(new Cell(cell.i(), col)) != null)
                .noneMatch(col -> grid.get(new Cell(cell.i(), col)) == number);
    }

    private static boolean checkSubGrid(final Board board, final Cell cell, final int number) {
        final int startRow = (cell.i() / 3) * 3; //(cell.i() / 3) * 3; // NOTE: x/3 * 3 = x
        final int startCol = (cell.j() / 3) * 3; //(cell.j() / 3) * 3; // NOTE: y/3 * 3 = y
        final var grid  = board.getCells();
        return IntStream.range(0, 3)
                .flatMap(rowOffset -> IntStream.range(0, 3)
                        .map(colOffset -> grid.get(new Cell(startRow + rowOffset, startCol + colOffset))))
                        .filter(Objects::nonNull)
                .noneMatch(value -> value == number);
    }
}
