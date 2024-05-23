package pcd.ass03.part2.domain.mom;

import pcd.ass03.part2.domain.Cell;

import java.util.Objects;

public record Move(Cell cell, int value) {

    /**
     * Create the move
     *
     * @param cell:  input cell of the move
     * @param value: input value of the move
     * @return the move to apply
     */
    public static Move createMove(final Cell cell, final int value) {
        return new Move(Objects.requireNonNull(cell), value);
    }
}
