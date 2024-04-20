package pcd.ass02.server.model.lib.event.html;

import io.vertx.core.AbstractVerticle;
import pcd.ass02.server.model.lib.WordOccurrence;
import pcd.ass02.server.model.lib.response.Response;

import java.util.concurrent.Future;

public class EventLoopCounter extends AbstractVerticle implements WordOccurrence<Future<Response>> {
    @Override
    public Future<Response> getWordOccurrences(String url, String word, int depth) {
        return null;
    }
}
