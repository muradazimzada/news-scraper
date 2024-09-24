package com.ncone.newsparserserver.service;

import com.ncone.newsparserserver.dto.NewsArticle;
import com.ncone.newsparserserver.repository.NewsRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.json.JSONObject;  // Import the org.json library for parsing JSON

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class NewsScraperService {

    private static final String BASE_URL = "https://www.bbc.com/news";
    private static final Logger logger = LoggerFactory.getLogger(NewsScraperService.class);

    private final NewsRepository newsRepository;

    public NewsScraperService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public List<NewsArticle> scrapeBBCNews() {
        Set<String> seenLinks = new HashSet<>();
        List<NewsArticle> uniqueArticles = new ArrayList<>();

        try {
            // Fetch the HTML document from BBC News
            Document document = Jsoup.connect(BASE_URL).get();

            // Select articles using multiple data-testid values (broaden the selection)
            Elements newsItems = document.select("div[data-testid='edinburgh-card'], div[data-testid='london-article'], div[data-testid='anchor-inner-wrapper']");

            // Get today's date for comparison
            LocalDate today = LocalDate.now();

            for (Element item : newsItems) {
                try {
                    // Extract the article link and ensure it's unique
                    String articleLink = BASE_URL + item.select("a[data-testid='internal-link']").attr("href").replace("/news", "");

                    if (!articleLink.contains("/articles/") || seenLinks.contains(articleLink) || newsRepository.findByLink(articleLink).isPresent()) {
                        continue; // Skip duplicate articles
                    }

                    seenLinks.add(articleLink);

                    // Extract headline, mini description, and relative time
                    String headline = item.select("h2[data-testid='card-headline']").text();
                    String miniDesc = item.select("p[data-testid='card-description'], p").text();
                    LocalDateTime publicationTime;
                    publicationTime = fetchPublicationTimeFromArticlePage(articleLink);

                    // Skip articles that are not from today
                    if (publicationTime == null) throw new AssertionError();
                    if (!publicationTime.toLocalDate().equals(today)) {
                        continue;
                    }

                    // Extract the article content
                    String content = getArticleContent(articleLink);

                    if (content.isEmpty()) {
                        continue; // Skip articles with no content
                    }

                    // Create and add the article to the unique articles list
                    NewsArticle article = new NewsArticle(headline, miniDesc, articleLink, content, publicationTime);
                    uniqueArticles.add(article);

                } catch (Exception e) {
                    logger.error("Error processing article", e);
                }
            }

        } catch (IOException e) {
            logger.error("Error fetching BBC News homepage", e);
        }

        return uniqueArticles;
    }

    // Parse relative time from strings like "54 minutes ago" or "2 hours ago"

    private LocalDateTime fetchPublicationTimeFromArticlePage(String articleLink) throws IOException {
        Document articlePage = Jsoup.connect(articleLink).get();

        // Select the <script> tag that contains the JSON-LD data
        String jsonLdData = articlePage.select("script[type=application/ld+json]").html();

        // Parse the JSON-LD content using org.json or any JSON library
        JSONObject json = new JSONObject(jsonLdData);

        // Extract the "datePublished" field, which is in ISO 8601 format
        String datePublished = json.optString("datePublished");

        // Optional: You can also extract the "dateModified" if you need it

        // Convert the ISO 8601 string to LocalDateTime
        if (!datePublished.isEmpty()) {
            return OffsetDateTime.parse(datePublished).toLocalDateTime();  // Assuming it's in ISO 8601 format
        } else {
            // Fallback if no publication date is found
            logger.error("No valid publication date found for article: {}", articleLink);
            return null;  // or LocalDateTime.now() as a fallback
        }
    }

    // Fetch and parse the article content from its link
    private String getArticleContent(String articleLink) throws IOException {
        Document articlePage = Jsoup.connect(articleLink).get();
        Elements contentElements = articlePage.select("div[data-component='text-block'] p");

        StringBuilder contentBuilder = new StringBuilder();
        for (Element paragraph : contentElements) {
            contentBuilder.append(paragraph.text()).append("\n");
        }

        return contentBuilder.toString();
    }
}
