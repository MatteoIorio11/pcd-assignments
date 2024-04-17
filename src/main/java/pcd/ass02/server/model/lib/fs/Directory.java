package pcd.ass02.server.model.lib.fs;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public record Directory(List<Document> files, List<Directory> subDirectories) {

    public static Directory from(final String dirname) throws IOException {
        return Directory.from(new File(dirname));
    }

    public static Directory from(final File file){
        final var files = Arrays.stream(file.listFiles()).peek(Objects::requireNonNull).toList();
        return new Directory(
            files.stream().filter(f -> !f.isDirectory()).map(Document::from).collect(Collectors.toList()),
            files.stream().filter(File::isDirectory).map(Directory::from).collect(Collectors.toList())
        );
    }
}
