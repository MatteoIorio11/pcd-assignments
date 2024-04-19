package pcd.ass02.server.model.lib.event.task;

import pcd.ass02.server.model.lib.fs.Document;
import pcd.ass02.server.model.lib.response.Response;

import java.util.Objects;
import java.util.concurrent.RecursiveTask;

public class DocumentSearchTask extends RecursiveTask<Void> {
    private final Document document;
    private final Response response;
    private final String word;
    public DocumentSearchTask(final Response response, final Document document, final String word){
        super();
        this.response = Objects.requireNonNull(response);
        this.document = Objects.requireNonNull(document);
        this.word = Objects.requireNonNull(word);
    }

    @Override
    protected Void compute() {
        response.addFile(this.document.toString(), this.document.lines().stream()
                .filter(word -> word.contains(this.word))
                .toList());
        return null;
    }
}
