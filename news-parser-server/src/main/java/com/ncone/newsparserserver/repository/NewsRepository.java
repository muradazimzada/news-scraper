package com.ncone.newsparserserver.repository;

import com.ncone.newsparserserver.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    // Find all articles published between two times
    List<News> findByPublicationTimeBetween(LocalDateTime start, LocalDateTime end);

    // Find an article by its unique link
    Optional<News> findByLink(String link);
}
