package pcd.ass02.server.model.lib;

import pcd.ass02.server.model.lib.response.Response;

public interface WordOccurrences {
    public Response getWordOccurrences(String path, String word, int depth);
}