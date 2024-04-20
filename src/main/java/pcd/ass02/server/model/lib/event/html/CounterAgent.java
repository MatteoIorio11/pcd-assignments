package pcd.ass02.server.model.lib.event.html;

import io.vertx.core.*;
import pcd.ass02.server.model.lib.html.Page;
import pcd.ass02.server.model.lib.response.Response;

import java.util.stream.Collectors;


public class CounterAgent extends AbstractVerticle {

    private final Vertx vertx;
    private final String word;
    private final Page page;
    private final int depth;

    public CounterAgent(final Vertx vertx, final Page page, final String word, final int depth) {
        this.vertx = vertx;
        this.word = word;
        this.page = page;
        this.depth = depth;
    }

    public Promise<Response> countOccurrenciesOnPage(final Response res, Promise<Response> promise) {
        this.vertx.runOnContext(h -> {
            if (depth == 1) {
                res.addParagraph(this.page.url(), this.page.getParagraphs());
                promise.complete(res);
                return;
            }

            this.vertx.runOnContext(a -> {
                Future.all(
                    this.page.getLinks().stream()
                        .map(p -> new CounterAgent(this.vertx, p, this.word, this.depth - 1))
                        .map(agent -> agent.countOccurrenciesOnPage(res, Promise.promise()).future())
                        .collect(Collectors.toList())
                ).onComplete(handler -> promise.complete(res));
                res.addParagraph(this.page.url(), this.page.getParagraphs());
            });
        });
        return promise;
    }

}
