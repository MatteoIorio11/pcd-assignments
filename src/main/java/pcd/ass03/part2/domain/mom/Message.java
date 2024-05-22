package pcd.ass03.part2.domain.mom;

import com.fasterxml.jackson.core.JsonProcessingException;
import pcd.ass03.part2.domain.Cell;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;


record Pair(Cell cell, int value){
    public static Pair createPair(final Cell cell, final int value){
        return new Pair(Objects.requireNonNull(cell), value);
    }
}

public record Message() {
    public static String putValue(final Cell cell, final int value) throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(Pair.createPair(cell, value));
    }
}
