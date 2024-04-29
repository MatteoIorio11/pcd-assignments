package pcd.ass02.server.model.lib.strategy.virtual.html;

import pcd.ass02.server.model.lib.WordOccurrence;
import pcd.ass02.server.model.lib.component.html.Page;
import pcd.ass02.server.model.lib.response.Response;

import java.util.*;

public class VirtualCounter implements WordOccurrence<Response> {
    private Response response;
    private record Task(int depth, Page page, Response response){}
    private boolean run = true;
    @Override
    public Response getWordOccurrences(String url, String word, int depth) {
        if(depth > 0) {
            final String inputWord = Objects.requireNonNull(word);
            this.response = new Response(inputWord);
            Page.from(Objects.requireNonNull(url)).ifPresent(page -> this.explorePages(depth, page, response));
            return response;
        }
        throw new IllegalArgumentException("The depth can not be equals or lower than 0");
    }

    @Override
    public Optional<Response> partialResult() {
        return Optional.ofNullable(this.response);
    }

    private void explorePages(final int depth, final Page page, final Response response){
        final Deque<Task> tasks = new ArrayDeque<>();
        final Set<String> pages = new HashSet<>();
        tasks.push(new Task(depth, page, response));
        while (!tasks.isEmpty() && this.run) {

            final Task currentTask = tasks.pop();
            System.out.println("Running: " + currentTask.page.url() + " Depth: " + currentTask.depth);
            if (currentTask.depth >= 1 && !pages.contains(currentTask.page.url())) {
                final Page currentPage = currentTask.page;
                System.out.println("Running: " + currentTask.depth);
                System.out.println("Page: " + currentTask.page.url());
                currentPage.getParagraphs().forEach(paragraph -> Thread.ofVirtual().start(() -> {
                    currentTask.response.addParagraph(currentPage.url(), paragraph);
                }));
                page.getLinks().stream().filter(link -> !pages.contains(link.url())).forEach(link -> {
                    System.out.println("Link: " + link.url());
                    tasks.push(new Task(currentTask.depth - 1, link, currentTask.response));
                });
                pages.add(currentPage.url());
            }
        }
    }

    @Override
    public void stopProcess(){
        this.run = false;
    }
}
