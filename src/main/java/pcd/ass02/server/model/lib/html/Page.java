package pcd.ass02.server.model.lib.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public record Page(String url, Document document) {
    public static Page from(final String url) {
        try {
            final Document document = Jsoup.connect(Objects.requireNonNull(url)).get();
            return new Page(url, document);
        }catch (IOException exception){
            exception.printStackTrace(System.err);
            return null;
        }
    }

    public List<String> getParagraphs(){

    }

}
