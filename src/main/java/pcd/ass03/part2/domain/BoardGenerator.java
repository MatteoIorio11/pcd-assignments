package pcd.ass03.part2.domain;

import java.util.Random;

public class BoardGenerator {

    private static final int TOTAL_NUMBERS = 9;
    private static final int RND_LOWER_BOUND = 0;
    private static final int RND_UPPER_BOUND = 9;

    private BoardGenerator() {}

    public static Board initializeBoard(final Board board, final Difficulty difficulty) {
        final Random random = new Random();
        int i = 0;
        final var solvedBoard = SudokuSolver.solve(board).orElseThrow();
        int totalNumbers = difficulty.getDifficulty() * TOTAL_NUMBERS;
        while (totalNumbers > 0){
            final int row = random.nextInt(RND_LOWER_BOUND, RND_UPPER_BOUND);
            final int col = random.nextInt(RND_LOWER_BOUND, RND_UPPER_BOUND);
            if (solvedBoard.removeValue(new Cell(row, col))){
                totalNumbers -= 1;
            }
        }
        return solvedBoard;
    }
}