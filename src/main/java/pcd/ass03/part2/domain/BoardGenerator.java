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
        board.putInitialValue(new Cell(0, 2), -1);
        board.putInitialValue(new Cell(0, 3), -1);
        board.putInitialValue(new Cell(0, 4), 7);
        board.putInitialValue(new Cell(0, 5), -1);
        board.putInitialValue(new Cell(0, 6), -1);
        board.putInitialValue(new Cell(0, 7), -1);
        board.putInitialValue(new Cell(0, 8), -1);
        board.putInitialValue(new Cell(1, 0), 6);
        board.putInitialValue(new Cell(1, 1), -1);
        board.putInitialValue(new Cell(1, 2), -1);
        board.putInitialValue(new Cell(1, 3), 1);
        board.putInitialValue(new Cell(1, 4), 9);
        board.putInitialValue(new Cell(1, 5), 5);
        board.putInitialValue(new Cell(1, 6), -1);
        board.putInitialValue(new Cell(1, 7), -1);
        board.putInitialValue(new Cell(1, 8), -1);
        board.putInitialValue(new Cell(2, 0), -1);
        board.putInitialValue(new Cell(2, 1), 9);
        board.putInitialValue(new Cell(2, 2), 8);
        board.putInitialValue(new Cell(2, 3), -1);
        board.putInitialValue(new Cell(2, 4), -1);
        board.putInitialValue(new Cell(2, 5), -1);
        board.putInitialValue(new Cell(2, 6), -1);
        board.putInitialValue(new Cell(2, 7), 6);
        board.putInitialValue(new Cell(2, 8), -1);
        board.putInitialValue(new Cell(3, 0), 8);
        board.putInitialValue(new Cell(3, 1), -1);
        board.putInitialValue(new Cell(3, 2), -1);
        board.putInitialValue(new Cell(3, 3), -1);
        board.putInitialValue(new Cell(3, 4), 6);
        board.putInitialValue(new Cell(3, 5), -1);
        board.putInitialValue(new Cell(3, 6), -1);
        board.putInitialValue(new Cell(3, 7), -1);
        board.putInitialValue(new Cell(3, 8), 3);
        board.putInitialValue(new Cell(4, 0), 4);
        board.putInitialValue(new Cell(4, 1), -1);
        board.putInitialValue(new Cell(4, 2), -1);
        board.putInitialValue(new Cell(4, 3), 8);
        board.putInitialValue(new Cell(4, 4), -1);
        board.putInitialValue(new Cell(4, 5), 3);
        board.putInitialValue(new Cell(4, 6), -1);
        board.putInitialValue(new Cell(4, 7), -1);
        board.putInitialValue(new Cell(4, 8), 1);
        board.putInitialValue(new Cell(5, 0), 7);
        board.putInitialValue(new Cell(5, 1), -1);
        board.putInitialValue(new Cell(5, 2), -1);
        board.putInitialValue(new Cell(5, 3), -1);
        board.putInitialValue(new Cell(5, 4), 2);
        board.putInitialValue(new Cell(5, 5), -1);
        board.putInitialValue(new Cell(5, 6), -1);
        board.putInitialValue(new Cell(5, 7), -1);
        board.putInitialValue(new Cell(5, 8), 6);
        board.putInitialValue(new Cell(6, 0), -1);
        board.putInitialValue(new Cell(6, 1), 6);
        board.putInitialValue(new Cell(6, 2), -1);
        board.putInitialValue(new Cell(6, 3), -1);
        board.putInitialValue(new Cell(6, 4), -1);
        board.putInitialValue(new Cell(6, 5), -1);
        board.putInitialValue(new Cell(6, 6), 2);
        board.putInitialValue(new Cell(6, 7), 8);
        board.putInitialValue(new Cell(6, 8), -1);
        board.putInitialValue(new Cell(7, 0), -1);
        board.putInitialValue(new Cell(7, 1), -1);
        board.putInitialValue(new Cell(7, 2), -1);
        board.putInitialValue(new Cell(7, 3), 4);
        board.putInitialValue(new Cell(7, 4), 1);
        board.putInitialValue(new Cell(7, 5), 9);
        board.putInitialValue(new Cell(7, 6), -1);
        board.putInitialValue(new Cell(7, 7), -1);
        board.putInitialValue(new Cell(7, 8), 5);
        board.putInitialValue(new Cell(8, 0), -1);
        board.putInitialValue(new Cell(8, 1), -1);
        board.putInitialValue(new Cell(8, 2), -1);
        board.putInitialValue(new Cell(8, 3), -1);
        board.putInitialValue(new Cell(8, 4), 8);
        board.putInitialValue(new Cell(8, 5), -1);
        board.putInitialValue(new Cell(8, 6), -1);
        board.putInitialValue(new Cell(8, 7), 7);
        board.putInitialValue(new Cell(8, 8), 9);
    }
}