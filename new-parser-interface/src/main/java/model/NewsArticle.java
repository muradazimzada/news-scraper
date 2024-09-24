package model;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public class NewsArticle {
    private Long id;
    private String headline;
    private String description;
    private String link;
    private String content;
    private LocalDateTime publicationTime;

    // Constructors

    public NewsArticle() {}

    public NewsArticle(String headline, String description, String link, String content, LocalDateTime publicationTime) {
        this.headline = headline;
        this.description = description;
        this.link = link;
        this.content = content;
        this.publicationTime = publicationTime;
    }

    // Getters and setters

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getPublicationTime() {
        return publicationTime;
    }

    public void setPublicationTime(LocalDateTime publicationTime) {
        this.publicationTime = publicationTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
