package pcd.ass02.server.model.lib.reactive;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.exceptions.OnErrorNotImplementedException;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.checkerframework.checker.units.qual.A;
import pcd.ass02.server.model.lib.WordOccurrence;
import pcd.ass02.server.model.lib.html.Page;
import pcd.ass02.server.model.lib.response.Response;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

public class ReactiveCounter implements WordOccurrence<Response> {
    private Response response;
    private Disposable disposable;
    @Override
    public Response getWordOccurrences(String url, String word, int depth) {
        this.response = new Response(Objects.requireNonNull(word));
        assert depth > 0;
        final Optional<Page> optPage = Page.from(Objects.requireNonNull(url));
        optPage.ifPresent(page -> this.explorePages(depth, page, response));
        return this.response;
    }

    private void explorePages(final int depth, final Page page, final Response response){
        if(depth <= 0){
            return;
        }
        try{
            this.disposable = Flowable
                    .fromArray(page.getParagraphs())
                    .subscribeOn(Schedulers.computation())
                    .doOnNext(paragraphs -> response.addParagraph(page.url(), paragraphs))
                    .subscribe();
        }catch (OnErrorNotImplementedException exception){
            //
        }
        page.getLinks().forEach(link -> this.explorePages(depth - 1, link, response));
    }

    @Override
    public Response partialResult() {
        return this.response;
    }

    @Override
    public void stopProcess() {
        if(Objects.nonNull(this.disposable)){
            System.out.println(this.disposable.isDisposed());
            System.out.println(this.disposable);
            this.disposable.dispose();
            System.out.println(this.disposable.isDisposed());
        }
    }
}
