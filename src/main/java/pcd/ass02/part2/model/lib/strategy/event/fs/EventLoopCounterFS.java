package pcd.ass02.part2.model.lib.strategy.event.fs;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import pcd.ass02.part2.model.lib.WordOccurrence;
import pcd.ass02.part2.model.lib.component.fs.Directory;
import pcd.ass02.part2.model.lib.response.Response;

import java.util.Objects;
import java.util.Optional;

public class EventLoopCounterFS extends AbstractVerticle implements WordOccurrence<Future<Response>> {

    private String path;
    private String word;
    private int depth;
    public void start(){

    }
    @Override
    public Future<Response> getWordOccurrences(String url, String word, int depth) {
        this.path = Objects.requireNonNull(url);
        this.word = Objects.requireNonNull(word);
        this.depth = depth;
        return this.explore();
    }

    @Override
    public Optional<Response> partialResult() {
        return Optional.empty();
    }

    @Override
    public void stopProcess() {

    }

    private Future<Response> explore(){
        if(Objects.nonNull(this.path) && Objects.nonNull(this.word) && this.depth >= 0){
            final Promise<Response> promise = Promise.promise();
            this.getVertx().runOnContext(handler -> {
                final Response response = new Response(this.word);
                this.explorePaths(Directory.from(this.path), this.depth, response);
                promise.complete(response);
            });
            return promise.future();
        }
        return null;
    }

    private void explorePaths(final Directory currDirectory, final int depth, final Response response){
        if (depth == 0) {
            return;
        }

        currDirectory.subDirectories()
                .forEach(directory -> this.explorePaths(directory, depth - 1, response));

        currDirectory.files().forEach(document -> {
            response.addParagraph(document.toString(), document.lines().stream()
                .filter(word -> word.contains(this.word))
                .toList());
        });
    }
}
