package pcd.ass03.part2.domain.mom;

import com.fasterxml.jackson.core.JsonProcessingException;
import pcd.ass03.part2.domain.Cell;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;


public record Message() {
    public record Move(Cell cell, int value){

        /**
         * Create the move
         * @param cell: input cell of the move
         * @param value: input value of the move
         * @return the move to apply
         */
        public static Move createMove(final Cell cell, final int value){
            return new Move(Objects.requireNonNull(cell), value);
        }
    }

    /**
     * Send the current message in JSON format
     * @param cell: the sudoku cell where to apply the move
     * @param value: value to put inside the input cell
     * @return JSON format string
     * @throws JsonProcessingException if the serialization does not go as planned
     */
    public static String sendMove(final Cell cell, final int value) throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(Move.createMove(cell, value));
    }

    /**
     * Get the current message
     * @param message: input message to de-serialize
     * @return the Move to apply in the sudoku board
     * @throws JsonProcessingException if the de-serialization does not go as planned
     */
    public static Move getMove(final String message) throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(message, Move.class);
    }
}
