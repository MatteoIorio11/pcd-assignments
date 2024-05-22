package pcd.ass03.part2.domain.mom;

import com.fasterxml.jackson.core.JsonProcessingException;
import pcd.ass03.part2.domain.Cell;
import com.fasterxml.jackson.databind.ObjectMapper;


record Pair(Cell x, int y){
}

public record Message() {
    public static String putValue(final Cell cell, final int value) throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final String jsonString = objectMapper.writeValueAsString(pair);
        System.out.println(jsonString);
        return "";
    }
}
