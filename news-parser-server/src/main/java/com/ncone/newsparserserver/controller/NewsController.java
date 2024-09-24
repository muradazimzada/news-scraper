package com.ncone.newsparserserver.controller;

import com.ncone.newsparserserver.dto.NewsArticle;
import com.ncone.newsparserserver.entity.News;
import com.ncone.newsparserserver.service.NewsService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    // Create (add) news article
    @PostMapping
    public ResponseEntity<News> addNewsArticle(@RequestBody @Validated NewsArticle article) {
        News savedArticle = newsService.save(article);
        return ResponseEntity.ok(savedArticle);
    }

    // Read (get) all news articles
    @GetMapping
    public ResponseEntity<List<News>> getAllNews() {
        List<News> articles = newsService.findAll();
        return ResponseEntity.ok(articles);
    }

    // Read (get) news articles by time period
    @GetMapping("/search")
    public ResponseEntity<List<News>> searchByTimePeriod(
            @RequestParam String timePeriod) {
        List<News> articles = newsService.searchByTimePeriod(timePeriod);
        return ResponseEntity.ok(articles);
    }

    // Update a news article
    @PutMapping("/{id}")
    public ResponseEntity<News> updateNewsArticle(
            @PathVariable Long id, @RequestBody News updatedArticle) {
        News updated = newsService.update(id, updatedArticle);
        return ResponseEntity.ok(updated);
    }

    // Delete a news article
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNewsArticle(@PathVariable Long id) {
        newsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
