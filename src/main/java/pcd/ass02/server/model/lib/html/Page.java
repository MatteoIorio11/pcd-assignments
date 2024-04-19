package pcd.ass02.server.model.lib.html;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public record Page(String url, Document document) {
    private final static String PARAGRAPH_TAG = "p";
    private final static String LINK_TAG = "a";
    private final static String HREF_ATTRIBUTE = "href";
    public static Page from(final String url) {
        try {
            final Document document = Jsoup.connect(Objects.requireNonNull(url)).get();
            return new Page(url, document);
        } catch (HttpStatusException httpStatusException){
            System.err.println("[PAGE] The input url: " + url + " does not exists");
            return null;
        } catch (IOException e) {
            e.printStackTrace(System.err);
            return null;
        }
    }

    /**
     * From this page we return the text of all the "p" tags.
     * @return By getting all the paragraphs inside the current page, we get each single text for each paragraph, we return it as a List.
     */
    public List<String> getParagraphs(){
        final Elements paragraphs = this.document.getElementsByTag(Page.PARAGRAPH_TAG);
        return paragraphs.stream().map(Element::text)
                .filter(text -> !text.isEmpty() && !text.isBlank())
                .toList();
    }

    /**
     * From this page we get a new list of all the linked Pages.
     * @return By getting all the "a" links inside this page, we return a new List of all the linked Pages.
     */
    public List<Page> getLinks(){
        final Elements links = this.document.getElementsByTag(Page.LINK_TAG);
        return links.stream()
                .map(link ->link.attr(Page.HREF_ATTRIBUTE))
                .map(href -> this.url + href)
                .map(Page::from)
                .filter(Objects::nonNull)
                .toList();
    }

}
