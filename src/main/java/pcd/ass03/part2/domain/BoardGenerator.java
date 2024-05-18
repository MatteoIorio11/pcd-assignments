package pcd.ass03.part2.domain;

import java.util.Random;

public class BoardGenerator {

    private static final int TOTAL_NUMBERS = 9;
    private static final int RND_LOWER_BOUND = 0;
    private static final int RND_UPPER_BOUND = 9;

    private BoardGenerator() {}

    public static void initializeBoard(final Board board, final int difficulty) {
        final Random random = new Random();
        int i = 0;
        final var solvedBoard = SudokuSolver.solve(board);
        if (solvedBoard.isPresent()){
            int removedN = 0;
            final int totalNumbers = difficulty * TOTAL_NUMBERS;
            while (removedN <= totalNumbers){
                final int row = random.nextInt(0, 9);
                final int col = random.nextInt(0, 9);
                if (board.removeValue(new Cell(row, col))){
                    removedN += 1;
                }
            }
        }
    }
}