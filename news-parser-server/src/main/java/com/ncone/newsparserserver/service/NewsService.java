package com.ncone.newsparserserver.service;

import com.ncone.newsparserserver.dto.NewsArticle;
import com.ncone.newsparserserver.entity.News;
import com.ncone.newsparserserver.repository.NewsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@Service
public class NewsService {

    private final NewsRepository newsRepository;

    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public List<News> findAll() {
        return newsRepository.findAll();
    }

    public void saveAll(List<NewsArticle> articles) {
        if (articles == null || articles.isEmpty()) {
            return;  // No articles to save
        }
        // Save all articles to the repository
        newsRepository.saveAll(articles.stream().map(NewsArticle::toEntity).toList());
    }
    public News update(Long id, News updatedArticle) {
        Optional<News> existingArticleOpt = newsRepository.findById(id);
        if (existingArticleOpt.isPresent()) {
            News existingArticle = existingArticleOpt.get();
            existingArticle.setHeadline(updatedArticle.getHeadline());
            existingArticle.setDescription(updatedArticle.getDescription());
            existingArticle.setContent(updatedArticle.getContent());


            return newsRepository.save(existingArticle);
        } else {
            throw new RuntimeException("News article not found with id: " + id);
        }
    }

    public void delete(Long id) {
        newsRepository.deleteById(id);
    }

    public List<News> searchByTimePeriod(String timePeriod) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start, end;

        // Define start and end based on the time period (morning, day, evening)
        end = switch (timePeriod.toLowerCase()) {
            case "morning" -> {
                start = now.with(LocalTime.of(0, 0));
                yield now.with(LocalTime.of(12, 0));
            }
            case "day" -> {
                start = now.with(LocalTime.of(12, 0));
                yield now.with(LocalTime.of(18, 0));
            }
            case "evening" -> {
                start = now.with(LocalTime.of(18, 0));
                yield now.with(LocalTime.of(23, 59));
            }
            default -> throw new IllegalArgumentException("Invalid time period: " + timePeriod);
        };

        // Fetch news articles between the start and end times
        return newsRepository.findByPublicationTimeBetween(start, end);
    }

    public News save(NewsArticle article) {
        return newsRepository.save(article.toEntity());
    }
}
