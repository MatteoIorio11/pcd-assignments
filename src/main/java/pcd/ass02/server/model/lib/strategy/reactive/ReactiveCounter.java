package pcd.ass02.server.model.lib.strategy.reactive;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.OnErrorNotImplementedException;
import pcd.ass02.server.model.lib.WordOccurrence;
import pcd.ass02.server.model.lib.component.html.Page;
import pcd.ass02.server.model.lib.response.Response;

import java.util.*;

public class ReactiveCounter implements WordOccurrence<Response> {
    private Response response;
    private final List<Disposable> disposable;
    private volatile boolean stop = false;

    public ReactiveCounter(){
        this.disposable = new LinkedList<>();
    }
    @Override
    public Response getWordOccurrences(String url, String word, int depth) {
        this.response = new Response(Objects.requireNonNull(word));
        assert depth > 0;
        this.stop = false;
        final Optional<Page> optPage = Page.from(Objects.requireNonNull(url));
        optPage.ifPresent(page -> this.explorePages(depth, page, response));
        return this.response;
    }

    private void explorePages(final int depth, final Page page, final Response response){
        if(depth <= 0 || this.stop){
            return;
        }
        try{
            this.disposable.add(Observable
                    .fromArray(page.getParagraphs())
                    .doOnNext(paragraphs -> response.addParagraph(page.url(), paragraphs))
                    .subscribe());
        }catch (OnErrorNotImplementedException exception){
            //
        }
        page.getLinks().forEach(link -> this.explorePages(depth - 1, link, response));
    }

    @Override
    public Optional<Response> partialResult() {
        return Optional.ofNullable(this.response);
    }

    @Override
    public void stopProcess() {
        this.stop = true;
        if(!this.disposable.isEmpty()){
            this.disposable.parallelStream().forEach(Disposable::dispose);
            this.disposable.clear();
        }
    }
}
