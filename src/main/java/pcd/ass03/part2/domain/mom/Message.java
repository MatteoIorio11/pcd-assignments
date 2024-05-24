package pcd.ass03.part2.domain.mom;

import com.google.gson.Gson;
import pcd.ass03.part2.domain.*;
import pcd.ass03.part2.domain.serialization.CustomGson;



public record Message() {
    private static final Gson GSON = CustomGson.getCustomBoardGson();

    /**
     * Marshall the input board.
     * @param board: input board to serialize
     * @return  JSON format string
     */
    public static String marshallBoard(final Board board) {
        return Message.GSON.toJson(board);
    }

    /**
     * Unmarshall the JSON serialized board;
     * @param board the serialized board
     * @return the deserialized boad
     */
    public static Board unmarshallBoard(final String board) {
        return Message.GSON.fromJson(board, Board.class);
    }
}
