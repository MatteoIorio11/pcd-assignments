package pcd.ass03.part2.domain.mom;

import com.fasterxml.jackson.core.JsonProcessingException;
import pcd.ass03.part2.domain.Cell;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;


public record Message() {
    public record Pair(Cell cell, int value){
        public static Pair createPair(final Cell cell, final int value){
            return new Pair(Objects.requireNonNull(cell), value);
        }
    }

    public static String sendMove(final Cell cell, final int value) throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(Pair.createPair(cell, value));
    }

    public static Pair getMove(final String message) throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(message, Pair.class);
    }
}
