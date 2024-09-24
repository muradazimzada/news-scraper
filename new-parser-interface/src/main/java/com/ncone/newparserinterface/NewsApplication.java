package com.ncone.newparserinterface;

import client.NewsApiClient;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.NewsArticle;

import java.util.ArrayList;
import java.util.List;

public class NewsApplication extends Application {

    private List<NewsArticle> newsArticles = new ArrayList<>(); // Initialize as an empty list
    private int currentIndex = 0; // Track current article index

    private Button prevButton;
    private Button nextButton;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("News Viewer");

        // ComboBox for selecting time period (Morning, Day, Evening)
        ComboBox<String> timePeriodComboBox = new ComboBox<>();
        timePeriodComboBox.getItems().addAll("Morning", "Day", "Evening");
        timePeriodComboBox.setValue("Morning"); // Set default value to Morning

        // Label and TextArea to display news
        Label headlineLabel = new Label();
        TextArea contentArea = new TextArea();
        contentArea.setWrapText(true);
        contentArea.setEditable(false);

        // Buttons to navigate between news
        prevButton = new Button("Previous");
        nextButton = new Button("Next");

        prevButton.setOnAction(e -> showPreviousArticle(headlineLabel, contentArea));
        nextButton.setOnAction(e -> showNextArticle(headlineLabel, contentArea));

        // Initially disable previous button since we start at the first article
        prevButton.setDisable(true);

        // Action when time period is selected
        timePeriodComboBox.setOnAction(e -> {
            String selectedTimePeriod = timePeriodComboBox.getValue();
            loadArticles(selectedTimePeriod); // Fetch news from backend based on time period
            if (newsArticles == null || newsArticles.isEmpty()) {
                headlineLabel.setText("No articles available for this time period.");
                contentArea.clear();
                currentIndex = 0;
                updateButtonStates(); // Update button states when no articles are found
            } else {
                currentIndex = 0;
                showArticle(currentIndex, headlineLabel, contentArea);
                updateButtonStates(); // Update button states when articles are loaded
            }
        });

        // Layout
        HBox navigationBox = new HBox(10, prevButton, nextButton);
        VBox layout = new VBox(10, timePeriodComboBox, headlineLabel, contentArea, navigationBox);
        layout.setPadding(new Insets(10));

        // Scene
        Scene scene = new Scene(layout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Load default articles for "Morning"
        loadArticles("Morning");
        if (!newsArticles.isEmpty()) {
            showArticle(currentIndex, headlineLabel, contentArea);
            updateButtonStates();
        }
    }

    // Function to load articles based on time period (API call to the backend)
    private void loadArticles(String timePeriod) {
        newsArticles.clear(); // Clear previous articles
        newsArticles = NewsApiClient.getArticlesByTimePeriod(timePeriod); // Fetch new articles

        // Ensure we handle null or empty responses
        if (newsArticles == null) {
            newsArticles = new ArrayList<>();
        }
    }

    // Function to display the current article
    private void showArticle(int index, Label headlineLabel, TextArea contentArea) {
        if (index >= 0 && index < newsArticles.size()) {
            NewsArticle article = newsArticles.get(index);
            headlineLabel.setText(article.getHeadline());
            contentArea.setText(article.getContent());
        }
        updateButtonStates(); // Update button states after displaying an article
    }

    // Function to show previous article
    private void showPreviousArticle(Label headlineLabel, TextArea contentArea) {
        if (currentIndex > 0) {
            currentIndex--;
            showArticle(currentIndex, headlineLabel, contentArea);
        }
        updateButtonStates(); // Update button states after navigating
    }

    // Function to show next article
    private void showNextArticle(Label headlineLabel, TextArea contentArea) {
        if (currentIndex < newsArticles.size() - 1) {
            currentIndex++;
            showArticle(currentIndex, headlineLabel, contentArea);
        }
        updateButtonStates(); // Update button states after navigating
    }

    // Function to update the states of the next and previous buttons
    private void updateButtonStates() {
        prevButton.setDisable(currentIndex == 0); // Disable if at the first article
        nextButton.setDisable(currentIndex == newsArticles.size() - 1); // Disable if at the last article
    }
}
