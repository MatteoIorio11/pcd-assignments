package pcd.ass02.server.model.lib.response;

import io.vertx.core.json.JsonObject;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Response {
    private final Map<String, List<String>> results;

    public Response(){
        this.results = new ConcurrentHashMap<>();
    }

    public void addFile(final String fileName, final String line){
        if(this.results.containsKey(Objects.requireNonNull(fileName))){
            this.results.get(fileName).add(Objects.requireNonNull(line));
        }else{
            this.results.put(Objects.requireNonNull(fileName),
                    new LinkedList<String>(Collections.singleton(Objects.requireNonNull(line))));
        }
    }

    public void addFile(final String fileName, final List<String> lines){
        lines.forEach(line -> this.addFile(fileName, line));
    }

    public JsonObject toJson(){
        final JsonObject response = new JsonObject();
        this.results.forEach(response::put);
        return response;
    }
}
