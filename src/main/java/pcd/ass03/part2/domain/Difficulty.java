package pcd.ass03.part2.domain;

public enum Difficulty {
    DEBUG(0),
    EASY(2),
    NORMAL(4),
    HARD(6);

    private final int difficulty;
    Difficulty(final int difficulty){
        this.difficulty = difficulty;
    }

    public int getDifficulty() {
        return difficulty;
    }
}
