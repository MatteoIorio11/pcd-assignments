package pcd.ass02.server.model.lib.event;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import pcd.ass02.server.model.lib.WordOccurrence;
import pcd.ass02.server.model.lib.fs.Directory;
import pcd.ass02.server.model.lib.fs.Document;
import pcd.ass02.server.model.lib.response.Response;

import java.io.IOException;
import java.util.Objects;

public class EventLoopCounter extends AbstractVerticle implements WordOccurrence<Future<Response>> {

    private String path;
    private String word;
    private int depth;
    public void start(){

    }
    @Override
    public Future<Response> getWordOccurrences(String path, String word, int depth) {
        this.path = Objects.requireNonNull(path);
        this.word = Objects.requireNonNull(word);
        this.depth = depth;
        return this.explore();
    }

    private Future<Response> explore(){
        if(Objects.nonNull(this.path) && Objects.nonNull(this.word) && this.depth >= 0){
            final Promise<Response> promise = Promise.promise();
            this.getVertx().runOnContext(handler -> {
                try {
                    final Response response = new Response();
                    this.explorePaths(Directory.from(this.path), this.depth, response);
                    promise.complete(response);
                } catch (IOException exception) {
                    promise.fail(exception);
                }
            });
            return promise.future();
        }
        return null;
    }

    private void explorePaths(final Directory currDirectory, final int depth, final Response response){
        if(depth == 0){
            return;
        }else{
            for(final Directory dir : currDirectory.subDirectories()){
                this.explorePaths(dir, depth - 1, response);
            }
            for(final Document document : currDirectory.files()){
                response.addFile(document.toString(), (int) document.lines().stream().filter(word -> word.equals(this.word)).count());
            }
        }
    }

}
