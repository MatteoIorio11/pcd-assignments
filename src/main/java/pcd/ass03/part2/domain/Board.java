package pcd.ass03.part2.domain;

import java.util.HashMap;
import java.util.Map;

public class Board {
    private final Map<Cell, Integer> cells;

    public Board(/* TODO: insert generator */) {
        this.cells = new HashMap<>();
    }

    private void initializeGrid() {
        // TODO call generator
    }

    public void putValue(final Cell cell, final int number) {
        if (number < 1 || number > 9) {
            throw new IllegalStateException("Number " + number + " is not in range 1..9");
        }

        if (this.isCellEmpty(cell)) {
            return; // TODO: change to something meaningful
        }

        // TODO check if move is legit

        this.cells.put(Cell.copyOf(cell), number);
    }

    public Map<Cell, Integer> getCells() {
        return Map.copyOf(this.cells);
    }

    public boolean isCellEmpty(final Cell cell) {
        return !this.cells.containsKey(cell);
    }
}
