package pcd.ass02.server.model.lib.component.fs;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public record Directory(String name, List<Document> files, List<Directory> subDirectories) {

    public static Directory from(final String dirname) {
        return Directory.from(new File(dirname));
    }

    public static Directory from(final File file) {
        final var files = Arrays.stream(Objects.requireNonNull(file.listFiles())).peek(Objects::requireNonNull).toList();
        return new Directory(
            file.getName(),
            files.stream().filter(f -> !f.isDirectory()).map(Document::from).collect(Collectors.toList()),
            files.stream().filter(File::isDirectory).map(Directory::from).collect(Collectors.toList())
        );
    }
}