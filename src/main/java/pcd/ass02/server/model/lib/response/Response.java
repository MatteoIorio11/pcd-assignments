package pcd.ass02.server.model.lib.response;

import io.vertx.core.json.JsonObject;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Response {
    private final Map<String, Paragraphs> results;
    private record Paragraphs(List<String> list){

        public static Paragraphs from(final String line) {
            return new Paragraphs(new LinkedList<>(List.of(Objects.requireNonNull(line))));
        }

        public static Paragraphs from(final List<String> lines){
            return new Paragraphs(new LinkedList<>(Objects.requireNonNull(lines)));
        }

        public Paragraphs addLines(final List<String> lines){
            list.addAll(Objects.requireNonNull(lines));
            return this;
        }

        public Paragraphs addLine(final String line){
            list.add(Objects.requireNonNull(line));
            return this;
        }
    }
    private final String word;

    public Response(final String word){
        this.results = new ConcurrentHashMap<>();
        this.word = Objects.requireNonNull(word).toLowerCase();
    }

    public void addParagraph(final String fileName, final String line){
        if(this.results.containsKey(Objects.requireNonNull(fileName))){
            this.results.computeIfPresent(Objects.requireNonNull(fileName), (key, value) -> value.addLine(line));
        }else{
            this.results.putIfAbsent(Objects.requireNonNull(fileName), Paragraphs.from(line));
        }
    }

    public void addParagraph(final String fileName, final List<String> lines){
        if(this.results.containsKey(Objects.requireNonNull(fileName))){
            this.results.computeIfPresent(fileName, (key, value) -> value.addLines(Objects.requireNonNull(lines)));
        }else{
            this.results.putIfAbsent(fileName, Paragraphs.from(Objects.requireNonNull(lines)));
        }
    }



    public JsonObject toJson(){
        final JsonObject response = new JsonObject();
        this.results.forEach((key, value) -> response.put(key, value.list().stream().toList()));
        return response;
    }

    public Map<String, Long> count(){
        return Map.copyOf(this.results).keySet().stream()
                .map(fileName -> Map.entry(fileName, this.countWords(fileName)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private long countWords(final String fileName){
        final Optional<Long> result = this.results.get(Objects.requireNonNull(fileName))
                .list()
                .stream()
                .filter(paragraph -> paragraph.contains(this.word))
                .distinct()
                .parallel()
                .map(paragraph -> Arrays.stream(paragraph.split(" "))
                        .map(String::toLowerCase)
                        .filter(word -> word.equals(this.word))
                        .count())
                .reduce(Long::sum);
        return result.orElseGet(() -> 0L);
    }
}
