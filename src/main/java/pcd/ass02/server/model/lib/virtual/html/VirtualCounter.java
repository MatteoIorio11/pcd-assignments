package pcd.ass02.server.model.lib.virtual.html;

import pcd.ass02.server.model.lib.WordOccurrence;
import pcd.ass02.server.model.lib.fs.Directory;
import pcd.ass02.server.model.lib.html.Page;
import pcd.ass02.server.model.lib.response.Response;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class VirtualCounter implements WordOccurrence<Response> {
    private String word;
    @Override
    public Response getWordOccurrences(String url, String word, int depth) {
        this.word = Objects.requireNonNull(word);
        final var response = new Response(this.word);
        this.explorePath(depth, Page.from(url).orElseThrow(), response);
        return response;
    }

    private void explorePath(final int depth, final Page page, final Response response){
        if(depth == 0){
            return;
        }
        this.joinThreads(page.getParagraphs().stream()
                .map(p -> Thread.ofVirtual().start(() -> {
                    response.addFile(page.url(), p);
                })).toList());


        this.joinThreads(page.getLinks().stream()
                .map(l -> Thread.ofVirtual().start(() -> {
                    this.explorePath(depth-1, l, response);
        })).toList());

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
}
