package pcd.ass03.part2.domain;

import java.util.Objects;

public record Cell(int i, int j) {
    public static Cell copyOf(final Cell cell) {
        return new Cell(cell.i, cell.j);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return i == cell.i && j == cell.j;
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, j);
    }
}
