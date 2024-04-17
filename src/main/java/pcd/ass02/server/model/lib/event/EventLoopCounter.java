package pcd.ass02.server.model.lib.event;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import pcd.ass02.server.model.lib.WordOccurrence;
import pcd.ass02.server.model.lib.response.Response;

public class EventLoopCounter extends AbstractVerticle implements WordOccurrence {

    public void start(){

    }
    @Override
    public Response getWordOccurrences(String path, String word, int depth) {
        final Promise<Response> promise = Promise.promise();

        return null;
    }
}
