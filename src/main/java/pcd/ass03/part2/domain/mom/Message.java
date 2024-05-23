package pcd.ass03.part2.domain.mom;

import com.fasterxml.jackson.core.JsonProcessingException;
import pcd.ass03.part2.domain.Board;
import pcd.ass03.part2.domain.Cell;
import com.fasterxml.jackson.databind.ObjectMapper;
import scala.Int;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public record Message() {
    /**
     * Send the current message in JSON format
     * @param cell: the sudoku cell where to apply the move
     * @param value: value to put inside the input cell
     * @return JSON format string
     * @throws JsonProcessingException if the serialization does not go as planned
     */
    public static String marshall(final Cell cell, final int value) throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(Move.createMove(cell, value));
    }

    public static String marshall(final Map<Cell, Integer> board) throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(Objects.requireNonNull(board));
    }

    /**
     * Get the current message
     * @param message: input message to de-serialize
     * @return the Move to apply in the sudoku board
     * @throws JsonProcessingException if the de-serialization does not go as planned
     */
    public static Move unmarshall(final String message) throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(message, Move.class);
    }
}
