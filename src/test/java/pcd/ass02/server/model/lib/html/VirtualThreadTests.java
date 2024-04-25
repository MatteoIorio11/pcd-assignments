package pcd.ass02.server.model.lib.html;

import org.junit.jupiter.api.Test;
import pcd.ass02.server.model.lib.WordOccurrence;
import pcd.ass02.server.model.lib.factory.WordCounterFactory;
import pcd.ass02.server.model.lib.response.Response;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VirtualThreadTests {
    final WordOccurrence<Response> virtualThread = WordCounterFactory.fromVirtual();

    @Test
    public void testNullInputs(){
        assertThrows(NullPointerException.class, () -> this.virtualThread.getWordOccurrences(null, "the", 1));
        assertThrows(NullPointerException.class, () -> this.virtualThread.getWordOccurrences("url.com", null, 1));
    }

    @Test
    public void testInvalidDepth(){
        assertThrows(IllegalArgumentException.class, () -> this.virtualThread.getWordOccurrences("url.com", "the", 0));
        assertThrows(IllegalArgumentException.class, () -> this.virtualThread.getWordOccurrences("url.com", "the", -1));
    }

    @Test
    public void testCountWords(){
        final Response response = new Response("the");
        response.addParagraph("https://matteoiorio11.github.io",
                List.of("p1 the", "p2 the", "p3 the", "hello world", "p4 the", "p5 the"));

        assertEquals(response.count(),
                this.virtualThread.getWordOccurrences("https://matteoiorio11.github.io", "the", 1).count());
    }

    @Test
    public void testCountZeroWords(){
        final String impossibleWord = "supercalifragilistichespiralidoso";
        final Response response = new Response(impossibleWord);
        response.addParagraph("https://matteoiorio11.github.io",
                List.of("p1 the", "p2 the", "p3 the", "hello world", "p4 the", "p5 the"));

        assertEquals(response.count(),
                this.virtualThread.getWordOccurrences("https://matteoiorio11.github.io", impossibleWord, 1).count());
    }
}
