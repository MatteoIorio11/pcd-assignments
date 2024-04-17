package pcd.ass02.server.model.lib;

import pcd.ass02.server.model.lib.response.Response;

public interface WordOccurrences {
    public Response getWordOccurrences(String httpServer, String word, int depth);
}
