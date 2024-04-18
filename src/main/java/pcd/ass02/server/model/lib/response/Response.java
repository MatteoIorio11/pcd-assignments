package pcd.ass02.server.model.lib.response;

import io.vertx.core.json.JsonObject;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Response {
    private final Map<String, List<String>> results;
    private final String word;

    public Response(final String word){
        this.results = new ConcurrentHashMap<>();
        this.word = Objects.requireNonNull(word).toLowerCase();
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

    public Map<String, Long> count(){
        return this.results.keySet().stream()
            .map(fileName -> Map.entry(fileName, this.countWords(fileName)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private long countWords(final String fileName){
        final Optional<Long> result = this.results.get(Objects.requireNonNull(fileName)).parallelStream()
                .filter(lines -> lines.contains(this.word))
                .map(lines -> Arrays.stream(lines.split(" "))
                        .map(String::toLowerCase)
                        .filter(word -> word.contains(this.word))
                        .count())
                .reduce(Long::sum);
        if (result.isPresent()){
            return result.get();
        }
        return 0;
    }
}
