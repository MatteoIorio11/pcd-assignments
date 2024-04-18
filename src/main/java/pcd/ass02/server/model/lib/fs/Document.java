package pcd.ass02.server.model.lib.fs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public record Document(String documentName, List<String> lines) {
    public static Document from(final String path) {
        return Document.from(path, new File(path));
    }

    public static Document from(final String name, final File file) {
        try {
            try (final BufferedReader reader = new BufferedReader(new FileReader(file))) {
                final var lines = reader.lines().toList();
                return new Document(name, lines);
            }
        } catch (final IOException e) {
            e.printStackTrace(System.err);
            return null;
        }
    }
}
