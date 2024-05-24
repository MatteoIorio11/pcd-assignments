package pcd.ass03.part2.domain;

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
        final var row = board.getCells().entrySet().stream().filter(e -> e.getKey().j() == cell.j());
        return row.noneMatch(c -> c.getValue().equals(number));
    }

    private static boolean checkCol(final Board board, final Cell cell, final int number) {
        final var col = board.getCells().entrySet().stream().filter(e -> e.getKey().i() == cell.i());
        return col.noneMatch(c -> c.getValue().equals(number));
    }

    private static boolean checkSubGrid(final Board board, final Cell cell, final int number) {
        final int startRow = (cell.i() / 3) * 3; //(cell.i() / 3) * 3; // NOTE: x/3 * 3 = x
        final int startCol = (cell.j() / 3) * 3; //(cell.j() / 3) * 3; // NOTE: y/3 * 3 = y
        final var grid  = board.getCells();

        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (grid.get(new Cell(i, j)).equals(number)) {
                    return false;
                }
            }
        }
        return true;
    }
}
