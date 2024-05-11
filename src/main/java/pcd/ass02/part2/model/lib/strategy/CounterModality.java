package pcd.ass02.part2.model.lib.strategy;

import java.util.Objects;

public enum CounterModality {
    EVENT("Event Loop"),
    REACTIVE("Reactive"),
    VIRTUAL("Virtual");

    private final String algorithm;
    CounterModality(final String algorith){
        this.algorithm = Objects.requireNonNull(algorith);
    }

    public String getName(){
        return this.algorithm;
    }

    @Override
    public String toString() {
        return "Algorithm type: " + this.algorithm;
    }
}
