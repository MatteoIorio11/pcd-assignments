package pcd.ass02.server.model.lib;

import pcd.ass02.server.model.lib.response.Response;

public interface WordOccurrence<E> {
    public E getWordOccurrences(String path, String word, int depth);
}
