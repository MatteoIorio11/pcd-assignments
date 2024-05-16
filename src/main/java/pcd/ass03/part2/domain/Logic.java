package pcd.ass03.part2.domain;

import java.util.Objects;
import java.util.stream.IntStream;

/**
 * Implements Sudoku's logic (i.e. if moves is legit)
 */
public class Logic {
    private Logic() {}

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
        if (Logic.checkCell(cell)){
            return false;
        }
        final int startRow = cell.i() / 3; //(cell.i() / 3) * 3; // NOTE: x/3 * 3 = x
        final int startCol = cell.j() / 3; //(cell.j() / 3) * 3; // NOTE: y/3 * 3 = y
        final var grid  = board.getCells();
        System.err.println("Row " + cell.i() + " Starting row " + startRow);
        System.err.println("Col " + cell.j() + " Starting col " + startCol);
        System.err.println(startCol);
        System.exit(0);
        return IntStream.range(0, 3)
                .flatMap(rowOffset -> IntStream.range(0, 3)
                        .map(colOffset -> grid.get(new Cell(startRow + rowOffset, startCol + colOffset))))
                        .filter(Objects::nonNull)
                .noneMatch(value -> value == number);
    }

    private static boolean checkCell(final Cell cell){
        System.err.println(cell);
        return cell.i() < 0 || cell.i() >= 9 || cell.j() < 0 || cell.j() >= 9;
    }
}
