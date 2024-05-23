package pcd.ass03.part2.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuSolverTest {
    private Board board;

    @BeforeEach
    public void setUp() {
        this.board = BoardGenerator.initializeBoard(new Board(), Difficulty.EASY);
    }

    @Test
    public void testBoardInitialization() {
        this.board = BoardGenerator.initializeBoard(this.board, Difficulty.DEBUG);
        assertTrue(this.isGameOver());
    }

    @Test
    public void testSolver() {
        final var sol = SudokuSolver.solve(this.board);
        sol.ifPresentOrElse(s -> assertTrue(this.isGameOver(s)), () -> { throw new IllegalStateException(); } );
    }

    @Test
    public void testRemoval() {
        this.board = BoardGenerator.initializeBoard(new Board(), Difficulty.DEBUG);
        final Cell cell = new Cell(2, 2);
        assertNotEquals(-1, this.board.getCells().get(cell));
        this.board.removeValue(cell);
        assertEquals(-1, this.board.getCells().get(cell));
    }

    @Test
    public void testInsertion() {
        final Cell cell = new Cell(2, 2);
        var oldval = this.board.getCells().get(cell);
        this.board.removeValue(cell);
        this.board.putValue(cell, oldval);
        assertEquals(oldval, (int) this.board.getCells().get(cell));
    }

    private boolean isGameOver() {
        return this.isGameOver(this.board);
    }

    private boolean isGameOver(final Board board) {
        return board.getCells().values().stream().allMatch(t -> t != -1);
    }
}
