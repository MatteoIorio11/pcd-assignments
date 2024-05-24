package pcd.ass03.part2.domain.serialization;

import com.google.gson.*;
import pcd.ass03.part2.domain.Board;
import pcd.ass03.part2.domain.Cell;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class BoardDeserializer implements JsonDeserializer<Board> {

    @Override
    public Board deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json instanceof JsonObject) {
            final var obj = json.getAsJsonObject();
            final var cells = obj.getAsJsonArray("cells");
            final Map<Cell, Integer> res = new HashMap<>();
            cells.forEach(e -> {
                final var o = e.getAsJsonObject();
                final Cell cell = context.deserialize(o.get("key"), Cell.class);
                final int value = o.get("value").getAsInt();
                res.put(cell, value);
            });
            return Board.fromCells(res);
        } else {
            throw new IllegalStateException("Ops...👉👈");
        }
    }
}
