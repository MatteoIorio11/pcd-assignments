package pcd.ass03.part2.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class Board implements Serializable {
    public static final int EMPTY_CELL = -1;
    private final HashMap<Cell, Integer> cells;

    public Board() {
        this.cells = new HashMap<>();
        this.initializeGrid();
    }

    private Board(final HashMap<Cell, Integer> cells) {
        this.cells = cells;
    }

    private void initializeGrid() {
        IntStream.range(0, 9)
                .forEach(i -> IntStream.range(0, 9)
                        .forEach(j -> this.cells.put(new Cell(i, j), -1))
                );
    }

    public boolean putValue(final Cell cell, final int number) {
        if ((number < 1 && number != Board.EMPTY_CELL) || number > 9) {
            throw new IllegalStateException("Number " + number + " is not in range 1..9");
        }

        if (SudokuLogic.isMoveAllowed(this, cell, number)) {
            this.cells.put(Cell.copyOf(cell), number);
            return true;
        }

        return false;
    }

    public boolean removeValue(final Cell cell){
        if (this.cells.containsKey(cell) && this.cells.get(cell) != Board.EMPTY_CELL){
            this.cells.put(cell, Board.EMPTY_CELL);
            return true;
        }
        return false;
    }

    public Map<Cell, Integer>getCells() {
        return this.cells;
    }

    public static Board fromCells(final Map<Cell, Integer> cells) {
        return new Board(new HashMap<>(cells));
    }
}
