package pcd.ass03.part2.logics;

import pcd.ass03.part2.domain.*;

import java.util.Map;
import java.util.Optional;

public abstract class Controller {
    protected Board sudokuBoard;
    private final Map<Cell, Integer> initialBoard;
    private final Map<Cell, Integer> solvedBoard;

    public Controller(final Difficulty difficulty, final Optional<String> host) {
        this.sudokuBoard = BoardGenerator.initializeBoard(new Board(), difficulty);
        this.solvedBoard = SudokuSolver.solve(this.sudokuBoard).get().getCells();
        this.initialBoard = this.sudokuBoard.getCells();
    }

    public boolean putValue(final Cell cell, final int value) {
        return this.sudokuBoard.putValue(cell, value);
    }

    public boolean isGameOver() {
        return this.solvedBoard.entrySet().stream()
                .allMatch(e -> this.sudokuBoard.getCells().getOrDefault(e.getKey(), -1).equals(e.getValue()));
    }

    public Map<Cell, Integer> getInitialBoard() {
        return this.initialBoard;
    }

    public Board getCurrentBoard() {
        return this.sudokuBoard;
    }

    public void removeValue(final Cell cell) {
        this.sudokuBoard.removeValue(cell);
    }
}
