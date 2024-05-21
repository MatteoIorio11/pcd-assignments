package pcd.ass03.part2.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SudokuSolverTest {
    private Board board;

    @BeforeEach
    public void setUp() {
        this.board = new Board();
        BoardGenerator.initializeBoard(this.board, Difficulty.EASY);
    }

    @Test
    public void testBoardInitialization() {
        this.board = new Board();
        BoardGenerator.initializeBoard(this.board, Difficulty.DEBUG);
        assertTrue(this.isGameOver());
    }

    @Test
    public void testSolver() {
        SudokuSolver.solve(this.board);
        assertTrue(this.isGameOver());
    }

    private boolean isGameOver() {
        return this.board.getCells().values().stream().allMatch(t -> t != -1);
    }
}
