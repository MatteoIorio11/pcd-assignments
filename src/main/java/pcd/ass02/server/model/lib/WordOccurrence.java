package pcd.ass02.server.model.lib;

import pcd.ass02.server.model.lib.response.Response;

public interface WordOccurrence<E> {
    public E getWordOccurrences(String url, String word, int depth);

    public void stopProcess();
}
