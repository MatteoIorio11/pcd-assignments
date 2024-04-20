package pcd.ass02.server.model.lib.html;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public record Page(String url, Document document) {
    private final static String PARAGRAPH_TAG = "p";
    private final static String LINK_TAG = "a";
    private final static String HREF_ATTRIBUTE = "href";
    private final static String HTTPS_WEBSITE = "https://";

    /**
     * @param url: input url of the page
     * @return a new Page from the url, this value CAN be NULL if the Page does not exist.
     */
    public static Optional<Page> from(final String url) {
        try {
            final Document document = Jsoup.connect(Objects.requireNonNull(url)).get();
            return Optional.of(new Page(url, document));
        } catch (HttpStatusException httpStatusException){
            System.err.println("[PAGE] The input url: " + url + " does not exists.");
            return Optional.empty();
        } catch (IOException e) {
            System.err.println("[PAGE] An error occurred lol.");
            return Optional.empty();
        }
    }

    /**
     * From this page we return the text of all the "p" tags.
     * @return By getting all the paragraphs inside the current page, we get each single text for each paragraph, we return it as a List.
     */
    public List<String> getParagraphs(){
        final Elements paragraphs = this.document.getElementsByTag(Page.PARAGRAPH_TAG);
        return paragraphs.parallelStream()
                .map(Element::text)
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
                .limit(100)
                .parallel()
                .map(link ->link.attr(Page.HREF_ATTRIBUTE))
                .map(this::createUrl)
                .map(Page::from)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    private String createUrl(final String inputUrl){

        return inputUrl.startsWith(Page.HTTPS_WEBSITE) ? inputUrl :this.url;
    }

}
