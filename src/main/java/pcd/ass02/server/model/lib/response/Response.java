package pcd.ass02.server.model.lib.response;

import io.vertx.core.json.JsonObject;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class Response {
    private final Map<String, Integer> results;

    public Response(){
        this.results = new ConcurrentHashMap<>();
    }

    public void addFile(final String fileName, final int counter){
        this.results.put(Objects.requireNonNull(fileName),
                counter);
    }

    public JsonObject toJson(){
        final JsonObject response = new JsonObject();
        this.results.forEach(response::put);
        return response;
    }
}
