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
        while (positionedNumbers <= TOTAL_NUMBERS) {
            final int x = random.nextInt(RND_LOWER_BOUND, RND_UPPER_BOUND);
            final int y = random.nextInt(RND_LOWER_BOUND, RND_UPPER_BOUND);
            final Cell boardCell = new Cell(x, y);
            if (board.putInitialValue(boardCell, positionedNumbers)) {
                positionedNumbers = positionedNumbers + 1;
            }
        }
    }
}