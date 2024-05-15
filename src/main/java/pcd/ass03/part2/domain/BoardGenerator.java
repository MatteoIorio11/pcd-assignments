package pcd.ass03.part2.domain;

import java.util.Objects;
import java.util.Random;
import java.util.stream.IntStream;

public class BoardGenerator {

    private static final int TOTAL_NUMBERS = 9;
    private static final int RND_LOWER_BOUND = 0;
    private static final int RND_UPPER_BOUND = 10;
    private final Board board;

    public BoardGenerator(final Board board){
        this.board = Objects.requireNonNull(board);
        this.initializeBoard();
    }

    private void initializeBoard(){
        final Random random = new Random();
        int positionedNumbers = 0;
        // add 9 numbers in random positions
        while (positionedNumbers < TOTAL_NUMBERS) {
            final int x = random.nextInt(RND_LOWER_BOUND, RND_UPPER_BOUND);
            final int y = random.nextInt(RND_LOWER_BOUND, RND_UPPER_BOUND);
            final Cell boardCell = new Cell(x, y);
            if (this.board.isCellEmpty(new Cell(x, y))) {
                positionedNumbers = positionedNumbers + 1;
                this.board.putValue(boardCell, positionedNumbers);
            }
        }
    }
}