package com.ncone.newsparserserver.dto;

import com.ncone.newsparserserver.entity.News;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public record NewsArticle(String heading, String miniDesc, String articleLink, String content, LocalDateTime publicationTime) {
    public News toEntity() {
        return new News(heading, miniDesc, publicationTime, articleLink, content);
    }
}
