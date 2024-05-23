package pcd.ass03.part2.domain.mom;

import pcd.ass03.part2.domain.Board;
import pcd.ass03.part2.domain.Cell;
import pcd.ass03.part2.domain.Difficulty;

import java.io.IOException;
import java.util.Objects;

public class MOMPlayer {
    private final Board board;
    private final MOMMiddleware middleware;
    public MOMPlayer(final Board board, final Difficulty difficulty) {
        this.board = Objects.requireNonNull(board);
        this.middleware = new MOMMiddleware(difficulty);
    }

    public void putValue(final Cell cell, final int value) throws IOException {
        this.board.putValue(cell, value);
        this.middleware.marshall(Message.marshall(cell, value));
    }

    public void removeValue(final Cell cell){
        this.removeValue(cell);
    }
}
