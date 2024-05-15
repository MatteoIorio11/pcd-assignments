package pcd.ass03.part2.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class Board {
    private final Map<Cell, Integer> cells;

    public Board() {
        this.cells = new HashMap<>();
        this.initializeGrid();
    }

    private Board(final Map<Cell, Integer> cells) {
        this.cells = cells;
    }

    private void initializeGrid() {
        IntStream.range(0, 9)
                .forEach(i -> IntStream.range(0, 9)
                        .forEach(j -> this.cells.put(new Cell(i, j), -1))
                );

        this.cells.putAll(BoardGenerator.initializeBoard().getCells());
    }

    public boolean putValue(final Cell cell, final int number) {
        if (number < 1 || number > 9) {
            throw new IllegalStateException("Number " + number + " is not in range 1..9");
        }

        if (!this.isCellEmpty(cell)) {
            return false;
        }

        if (Logic.isMoveAllowed(this, cell, number)) {
            this.cells.put(Cell.copyOf(cell), number);
            return true;
        }

        return false;
    }

    public Map<Cell, Integer> getCells() {
        return Map.copyOf(this.cells);
    }

    public boolean isCellEmpty(final Cell cell) {
        return !this.cells.containsKey(cell) || this.cells.get(cell) == -1;
    }

    public static Board fromCells(final Map<Cell, Integer> cells) {
        return new Board(cells);
    }
}
