package pcd.ass03.part2.domain.serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import pcd.ass03.part2.domain.Board;

public class CustomGson {
    public static Gson getCustomBoardGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Board.class, new BoardSerializer())
                .registerTypeAdapter(Board.class, new BoardDeserializer())
                .create();
    }
}
