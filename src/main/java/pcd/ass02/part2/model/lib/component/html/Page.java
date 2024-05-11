package pcd.ass02.part2.model.lib.component.html;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public record Page(String url, Document document) {
    private final static String PARAGRAPH_TAG = "p";
    private final static String LINK_TAG = "a";
    private final static String HREF_ATTRIBUTE = "href";
    private final static String HTTPS_WEBSITE = "https://";
    private final static long LINKS_LIMIT = 20;

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
            System.err.println("[PAGE] An error occurred: " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * From this page we return the text of all the "p" tags.
     * @return By getting all the paragraphs inside the current page, we get each single text for each paragraph, we return it as a List.
     */
    public List<String> getParagraphs(){
        return this.document.getElementsByTag(Page.PARAGRAPH_TAG)
                .stream()
                .map(Element::text)
                .filter(text -> !text.isEmpty() && !text.isBlank())
                .toList();
    }

    /**
     * From this page we get a new list of all the linked Pages.
     * @return By getting all the "a" links inside this page, we return a new List of all the linked Pages.
     */
    public List<Page> getLinks(){
        return this.document.getElementsByTag(Page.LINK_TAG)
                .stream()
                .limit(Page.LINKS_LIMIT)
                .map(this::mapElement)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    private String getHref(final Element element){
        return element.attr(Page.HREF_ATTRIBUTE);
    }

    private String createUrl(final String inputUrl){

        return inputUrl.startsWith(Page.HTTPS_WEBSITE) ? inputUrl :
                inputUrl.startsWith("/") ? this.url + inputUrl.substring(1, inputUrl.length() - 1) : this.url;
    }

    private Optional<Page> createPage(final String url){
        return Page.from(url);
    }

    private Optional<Page> mapElement(final Element element){
        return this.createPage(
                this.createUrl(
                        this.getHref(element)));
    }

}
