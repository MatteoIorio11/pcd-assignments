package pcd.ass03.part2.domain;

import java.util.Objects;
import java.util.Random;
import java.util.stream.IntStream;

public class BoardGenerator {
    private final Board board;
    public BoardGenerator(final Board board){
        this.board = Objects.requireNonNull(board);
        this.initializeBoard();
    }

    private void initializeBoard(){
        final Random random = new Random();
        int positionedNumbers = 0;
        // add 9 numbers in random positions
        while (positionedNumbers < 9) {
            final int x = random.nextInt(0, 10);
            final int y = random.nextInt(0, 10);
            final Cell boardCell = new Cell(x, y);
            if (this.board.isCellEmpty(new Cell(x, y))) {
                positionedNumbers = positionedNumbers + 1;
                this.board.putValue(boardCell, positionedNumbers);
            }
        }
    }

    public static void main(String[] args) {
        var b = new BoardGenerator();
    }
}
