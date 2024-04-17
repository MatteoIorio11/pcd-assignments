package pcd.ass02.server.model.lib.virtual;

import pcd.ass02.server.model.lib.WordOccurrence;
import pcd.ass02.server.model.lib.response.Response;

public class VirtualCounter implements WordOccurrence<Response> {
    @Override
    public Response getWordOccurrences(String path, String word, int depth) {
        return null;
    }
}
