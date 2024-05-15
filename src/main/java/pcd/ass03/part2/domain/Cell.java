package pcd.ass03.part2.domain;

public record Cell(int i, int j) {
    public static Cell copyOf(final Cell cell) {
        return new Cell(cell.i, cell.j);
    }
}
