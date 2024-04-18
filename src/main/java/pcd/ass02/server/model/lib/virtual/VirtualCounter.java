package pcd.ass02.server.model.lib.virtual;

import pcd.ass02.server.model.lib.WordOccurrence;
import pcd.ass02.server.model.lib.fs.Directory;
import pcd.ass02.server.model.lib.response.Response;

import java.util.List;
import java.util.Objects;

public class VirtualCounter implements WordOccurrence<Response> {
    private String path;
    private String word;
    private int depth;
    @Override
    public Response getWordOccurrences(String path, String word, int depth) {
        this.path = Objects.requireNonNull(path);
        this.word = Objects.requireNonNull(word);
        this.depth = depth;
        final var response = new Response();
        this.explorePath(depth, Directory.from(path), response);
        return response;
    }

    private void explorePath(final int depth, final Directory directory, final Response response){
        if(depth == 0){
            return;
        }
        var virtualThreads = directory.files().stream().map(f -> Thread.ofVirtual().start(() -> {
            response.addFile(f.name(), f.lines().stream().filter(l -> l.contains(this.word)).toList());
        })).toList();

        this.joinThreads(virtualThreads);
        virtualThreads = directory.subDirectories().stream().map(d -> Thread.ofVirtual().start(() -> {
            this.explorePath(depth-1, d, response);
        })).toList();
        this.joinThreads(virtualThreads);
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
