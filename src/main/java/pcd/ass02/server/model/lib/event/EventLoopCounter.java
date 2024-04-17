package pcd.ass02.server.model.lib.event;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import pcd.ass02.server.model.lib.WordOccurrence;
import pcd.ass02.server.model.lib.response.Response;

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
        return null;
    }

    private Response explore(){
        final Response response = new Response();
        if(Objects.nonNull(this.path) && Objects.nonNull(this.word) && this.depth >= 0){
        }
        return response;
    }

}
