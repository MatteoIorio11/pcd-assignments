package pcd.ass03.part2.domain;

import java.util.Random;

public class BoardGenerator {

    private static final int TOTAL_NUMBERS = 9;
    private static final int RND_LOWER_BOUND = 0;
    private static final int RND_UPPER_BOUND = 9;

    private BoardGenerator() {}

    public static void initializeBoard(final Board board) {
        final Random random = new Random();
        int positionedNumbers = 1;
        // add 9 numbers in random positions
        /*
        while (positionedNumbers <= TOTAL_NUMBERS) {
            final int x = random.nextInt(RND_LOWER_BOUND, RND_UPPER_BOUND);
            final int y = random.nextInt(RND_LOWER_BOUND, RND_UPPER_BOUND);
            final Cell boardCell = new Cell(x, y);
            if (board.putInitialValue(boardCell, positionedNumbers)) {
                positionedNumbers = positionedNumbers + 1;
            }
        }
         */
        board.putInitialValue(new Cell(0, 0), 5);
        board.putInitialValue(new Cell(0, 1), 3);
        board.putInitialValue(new Cell(0, 4), 7);
        board.putInitialValue(new Cell(1, 0), 6);
        board.putInitialValue(new Cell(1, 3), 1);
        board.putInitialValue(new Cell(1, 4), 9);
        board.putInitialValue(new Cell(1, 5), 5);
        board.putInitialValue(new Cell(2, 1), 9);
        board.putInitialValue(new Cell(2, 2), 8);
        board.putInitialValue(new Cell(2, 7), 6);
    }
}