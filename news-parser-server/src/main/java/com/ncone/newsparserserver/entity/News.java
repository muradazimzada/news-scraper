package com.ncone.newsparserserver.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ncone.newsparserserver.dto.NewsArticle;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
@Table(name = "news")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    private String headline;

    @Setter
    @Getter
    @Column(columnDefinition = "TEXT")
    private String description;

    @Setter
    @Getter
    private LocalDateTime publicationTime;

    @Setter
    @Getter
    @Column(nullable = false, unique = true)
    private String link;

    @Setter
    @Getter
    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    public News() {}

    public News(String headline, String description, LocalDateTime publicationTime, String link, String content) {
        this.headline = headline;
        this.description = description;
        this.publicationTime = publicationTime;
        this.link = link;
        this.content = content;
    }

    public NewsArticle toDto() {
        return new NewsArticle(headline, description, link, content, publicationTime);
    }

}
