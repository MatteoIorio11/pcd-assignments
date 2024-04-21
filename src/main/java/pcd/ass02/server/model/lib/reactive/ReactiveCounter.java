package pcd.ass02.server.model.lib.reactive;

import pcd.ass02.server.model.lib.WordOccurrence;
import pcd.ass02.server.model.lib.response.Response;

public class ReactiveCounter implements WordOccurrence {
    @Override
    public Response getWordOccurrences(String url, String word, int depth) {
        return null;
    }

    @Override
    public Response partialResult() {
        return null;
    }

    @Override
    public void stopProcess() {

    }
}
