package pcd.ass02.server.model.lib.event.html;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import pcd.ass02.server.model.lib.WordOccurrence;
import pcd.ass02.server.model.lib.response.Response;


public class EventLoopCounter extends AbstractVerticle implements WordOccurrence<Future<Response>> {
    @Override
    public Future<Response> getWordOccurrences(String url, String word, int depth) {
        return null;
    }
}
