package pcd.ass02.server.model.lib.fs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileSystemTests {
    @Test
    void testDirectoryCreation() {
        final Directory dir = Directory.from("./");

        final var files = dir.files();
        final var subDirs = dir.subDirectories();
        assertFalse(files.isEmpty());
        assertFalse(subDirs.isEmpty());

        assertTrue(files.stream().map(Document::name).toList().contains("build.gradle.kts"));
        assertTrue(subDirs.stream().map(Directory::name).toList().contains("src"));
    }

    @Test
    void testDocumentCreation() {
        final Document doc = Document.from("build.gradle.kts");
        assertFalse(doc.lines().isEmpty());
    }
}
