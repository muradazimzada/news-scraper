package client;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import model.NewsArticle;

public class NewsApiClient {

    private static final String API_URL = "http://localhost:8080/api/news/search?timePeriod=";

    // Method to fetch news articles based on time period
    public static List<NewsArticle> getArticlesByTimePeriod(String timePeriod) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL + timePeriod))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.readValue(response.body(), new TypeReference<List<NewsArticle>>() {});
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return List.of(); // Return empty list on error
        }
    }
}
