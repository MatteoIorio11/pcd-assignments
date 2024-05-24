package pcd.ass03.part2.domain.serialization;

import com.google.gson.*;
import pcd.ass03.part2.domain.Board;

import java.lang.reflect.Type;

public class BoardSerializer implements JsonSerializer<Board> {

    @Override
    public JsonElement serialize(Board src, Type typeOfSrc, JsonSerializationContext context) {
        final var jsonObj = new JsonObject();
        final var jsonArray = new JsonArray();
        src.getCells().forEach((k, v) -> {
            final var jsonObj1 = new JsonObject();
            jsonObj1.add("key", context.serialize(k));
            jsonObj1.addProperty("value", v);
            jsonArray.add(jsonObj1);
        });

        jsonObj.add("cells", jsonArray);
        return jsonObj;
    }
}
