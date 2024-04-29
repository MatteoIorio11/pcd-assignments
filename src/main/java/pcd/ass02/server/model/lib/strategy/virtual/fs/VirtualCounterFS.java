package pcd.ass02.server.model.lib.strategy.virtual.fs;

import pcd.ass02.server.model.lib.WordOccurrence;
import pcd.ass02.server.model.lib.fs.Directory;
import pcd.ass02.server.model.lib.response.Response;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public class VirtualCounterFS implements WordOccurrence<Response> {
    private String word;
    @Override
    public Response getWordOccurrences(String url, String word, int depth) {
        this.word = Objects.requireNonNull(word);
        final var response = new Response(this.word);
        this.explorePath(depth, Directory.from(url), response);
        return response;
    }

    @Override
    public Optional<Response> partialResult() {
        return Optional.empty();
    }

    @Override
    public void stopProcess() {

    }

    private void explorePath(final int depth, final Directory directory, final Response response){
        if(depth == 0){
            return;
        }
        this.joinThreads(directory.files().stream()
                .map(f -> Thread.ofVirtual().start(() -> {
                    response.addParagraph(f.name(), f.lines().stream()
                            .filter(l -> l.contains(this.word))
                            .toList());
        })).toList());


        this.joinThreads(directory.subDirectories().stream()
                .map(d -> Thread.ofVirtual().start(() -> {
                    this.explorePath(depth-1, d, response);
        })).toList());

        this.createTask(directory.subDirectories(),
                (a) -> Thread.ofVirtual().start(() -> {
                    this.explorePath(depth-1, a, response);}));
    }

    private void createTask(final List<Directory> directories, final Function<Directory, Thread> mapper){
        this.joinThreads(directories.stream().map(mapper).toList());
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
