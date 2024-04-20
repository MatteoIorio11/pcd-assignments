package pcd.ass02.server.model.lib.event.html;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import pcd.ass02.server.model.lib.WordOccurrence;
import pcd.ass02.server.model.lib.html.Page;
import pcd.ass02.server.model.lib.response.Response;

import java.util.Objects;


public class EventLoopCounter extends AbstractVerticle implements WordOccurrence<Future<Response>> {

    @Override
    public Future<Response> getWordOccurrences(String url, String word, int depth) {
        assert depth >= 0;
        Objects.requireNonNull(url);
        Objects.requireNonNull(word);

        final var page = Page.from(url);
        assert page.isPresent();

        final Response res = new Response(word);
        final Promise<Response> promise = Promise.promise();

        this.getVertx().runOnContext(h -> {
            new CounterAgent(this.getVertx(), page.get(), word, depth).countOccurrenciesOnPage(res, promise);
        });

        return promise.future();
    }

    @Override
    public void stopProcess() {
        this.getVertx().close(h -> System.out.println("Verticle closed gracefully!"));
    }
}
