package pcd.ass02.server.model.lib.fs;

import org.junit.jupiter.api.Test;
import pcd.ass02.server.model.lib.WordOccurrence;
import pcd.ass02.server.model.lib.factory.WordCounterFactory;
import pcd.ass02.server.model.lib.response.Response;

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

    }
}
