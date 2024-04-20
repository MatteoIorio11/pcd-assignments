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

    public synchronized void addParagraph(final String fileName, final String line){
        if(this.results.containsKey(Objects.requireNonNull(fileName))){
            this.results.get(fileName).add(Objects.requireNonNull(line));
        }else{
            this.results.put(Objects.requireNonNull(fileName),
                    new LinkedList<>(Collections.singleton(Objects.requireNonNull(line))));
        }
    }

    public void addParagraph(final String fileName, final List<String> lines){
        if(this.results.containsKey(Objects.requireNonNull(fileName))){
            this.results.get(fileName).addAll(Objects.requireNonNull(lines));
        }else{
            this.results.put(Objects.requireNonNull(fileName),
                    new LinkedList<>(Objects.requireNonNull(lines)));
        }
    }



    public JsonObject toJson(){
        final JsonObject response = new JsonObject();
        this.results.forEach((key, value) -> response.put(key, value.stream().toList()));
        return response;
    }

    public Map<String, Long> count(){
        return Map.copyOf(this.results).keySet().stream().parallel()
            .map(fileName -> Map.entry(fileName, this.countWords(fileName)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private long countWords(final String fileName){
        final Optional<Long> result = this.results.get(Objects.requireNonNull(fileName))
                .stream()
                .filter(lines -> lines.toLowerCase().contains(this.word))
                .distinct()
                .parallel()
                .map(lines -> Arrays.stream(lines.split(" "))
                        .map(String::toLowerCase)
                        .filter(word -> word.equals(this.word))
                        .count())
                .reduce(Long::sum);
        if (result.isPresent()){
            return result.get();
        }
        return 0;
    }
}
