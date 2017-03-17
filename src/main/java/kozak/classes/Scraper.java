package kozak.classes;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Олексій on 16.03.2017.
 */
@Component
public class Scraper {
    private static List<String> parsedURLs = new ArrayList<>();
    private String url;
    private String cssQuery;
    private CachedWriter cachedWriter;

    public void init() throws IOException {
    }

    public void destroy() {}

    public Scraper() {
    }

    public Scraper(CachedWriter writer) {
        this.cachedWriter = writer;
        this.url = "https://tsn.ua";
        this.cssQuery = "article[class=\"h-entry c-entry   \"] a[class=\"u-url u-uid c-post-img-wrap\"]";
    }

    public void parse() throws IOException {
        Document document = Jsoup.connect(this.url).get();
        Elements paragraphs = document.select(this.cssQuery);
        for (Element p : paragraphs) {
            String articleURL = p.attr("href");
            if (!parsedURLs.contains(articleURL)) {
                parsedURLs.add(articleURL);
                try
                {
                    cachedWriter.write(scrapArticle(articleURL));
                }
                catch (IOException e){
                    System.out.println("Проблема з записом в файл.");
                }
            } else {
                break;
            }
        }
    }

    public String scrapArticle(String url) throws IOException {
        Document document = Jsoup.connect(url).get();
        Elements paragraphs = document.select("h1[class=\"p-name c-post-title u-uppercase\"]");
        StringBuilder article = new StringBuilder();
        for (Element p : paragraphs) {
            article.append(p.ownText() + "\n\n");
        }
        paragraphs.clear();
        paragraphs = document.select("div[class=\"e-content\"] p");
        for (Element p : paragraphs) {
            if (!p.text().isEmpty()) {
                article.append(p.text() + "\n");
            }
        }
        return article.toString();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCssQuery() {
        return cssQuery;
    }

    public void setCssQuery(String cssQuery) {
        this.cssQuery = cssQuery;
    }

    public CachedWriter getCachedWriter() {
        return cachedWriter;
    }
    public void setCachedWriter(CachedWriter cachedWriter) {
        this.cachedWriter = cachedWriter;
    }
}
