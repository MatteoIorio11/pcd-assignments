package pcd.ass02.server.model.lib.strategy.virtual.html;

import pcd.ass02.server.model.lib.WordOccurrence;
import pcd.ass02.server.model.lib.component.html.Page;
import pcd.ass02.server.model.lib.response.Response;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class VirtualCounter implements WordOccurrence<Response> {
    private Response response;
    private final List<Thread> threads = new CopyOnWriteArrayList<>();
    @Override
    public Response getWordOccurrences(String url, String word, int depth) {
        if(depth > 0) {
            final String inputWord = Objects.requireNonNull(word);
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
        if(depth == 0){
            return;
        }

        page.getParagraphs().forEach(p -> response.addParagraph(page.url(), p));

        final var startedThreads = page.getLinks().stream()
                .map(link -> Thread.ofVirtual()
                        .start(() -> {
            this.explorePath(depth - 1, link, response);
        })).toList();

        this.threads.addAll(startedThreads);
        this.joinThreads(startedThreads);
        this.threads.removeAll(startedThreads);

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
    public void stopProcess(){
        this.joinThreads(this.threads);
    }
}
