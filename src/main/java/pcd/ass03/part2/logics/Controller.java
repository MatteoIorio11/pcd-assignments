package pcd.ass03.part2.logics;

import pcd.ass03.part2.domain.*;

import java.util.Map;

public class Controller {
    private final Board sudokuBoard;
    private final Map<Cell, Integer> initialBoard;

    public Controller(final Difficulty difficulty) {
        this.sudokuBoard = new Board();
        SudokuSolver.solve(this.sudokuBoard);
        //BoardGenerator.initializeBoard(this.sudokuBoard, Difficulty.DEBUG);
        SudokuSolver.printSudokuBoard(this.sudokuBoard.getCells());
        this.initialBoard = this.sudokuBoard.getCells();
    }

    public boolean putValue(final Cell cell, final int value) {
        return this.sudokuBoard.putValue(cell, value);
    }

    public Map<Cell, Integer> getInitialBoard() {
        return this.initialBoard;
    }
}
