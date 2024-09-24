package com.ncone.newsparserserver.cron;

import com.ncone.newsparserserver.dto.NewsArticle;
import com.ncone.newsparserserver.service.NewsScraperService;
import com.ncone.newsparserserver.service.NewsService;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NewsScraperCronJob {

    private final NewsScraperService newsScraperService;
    private final NewsService newsService;

    public NewsScraperCronJob(NewsScraperService newsScraperService, NewsService newsService) {
        this.newsScraperService = newsScraperService;
        this.newsService = newsService;
    }

    // This will run every 20 minutes as per the cron schedule
    @Scheduled(cron = "0 */20 * * * *")
    public void runScraper() {
        scrapeAndSave();
    }

    // This will run on application startup (when the context is refreshed)
    @EventListener(ContextRefreshedEvent.class)
    public void runOnStartup() {
        scrapeAndSave();
    }

    // Centralized method for scraping and saving articles
    private void scrapeAndSave() {
        try {
            // Scrape the news articles
            List<NewsArticle> articles = newsScraperService.scrapeBBCNews();

            // Save all the new articles
            newsService.saveAll(articles);
            System.out.println("News scraping job executed and saved successfully.");
        } catch (Exception e) {
            System.err.println("Error during scraping job: " + e.getMessage());
        }
    }
}
