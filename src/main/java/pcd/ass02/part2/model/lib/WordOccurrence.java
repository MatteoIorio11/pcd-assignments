package pcd.ass02.part2.model.lib;

import pcd.ass02.part2.model.lib.response.Response;

import java.util.Optional;

public interface WordOccurrence<E> {
    public E getWordOccurrences(String url, String word, int depth);

    public Optional<Response> partialResult();

    public void stopProcess();
}
