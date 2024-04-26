package pcd.ass02.server.model.lib.virtual.html;

import pcd.ass02.server.model.lib.WordOccurrence;
import pcd.ass02.server.model.lib.html.Page;
import pcd.ass02.server.model.lib.response.Response;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class VirtualCounter implements WordOccurrence<Response> {
    private Response response;
    private volatile boolean stop = false;
    @Override
    public Response getWordOccurrences(String url, String word, int depth) {
        if(depth > 0) {
            this.stop = false;
            String inputWord = Objects.requireNonNull(word);
            this.response = new Response(inputWord);
            Page.from(Objects.requireNonNull(url)).ifPresent(page -> this.explorePath(depth, page, response));
            return response;
        }
        throw new IllegalArgumentException("The depth can not be equals or lower than 0");
    }

    @Override
    public Optional<Response> partialResult() {
        return Optional.ofNullable(this.response);
    }

    private void explorePath(final int depth, final Page page, final Response response){
        if(depth == 0 || this.stop){
            return;
        }
        this.joinThreads(page.getParagraphs().stream()
                .map(p -> Thread.ofVirtual().start(() -> {
                    response.addParagraph(page.url(), p);
                })).toList());

        /*
        this.joinThreads(page.getLinks().stream()
                .map(l -> Thread.ofVirtual().start(() -> {
                    this.explorePath(depth-1, l, response);
        })).toList());
         */
        page.getLinks().forEach(link -> this.explorePath(depth - 1, link, response));

    }

    private void joinThreads(final List<Thread> list){
        list.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public synchronized void stopProcess(){

        this.stop = true;
    }
}
