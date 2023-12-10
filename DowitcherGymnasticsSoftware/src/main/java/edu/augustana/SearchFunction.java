package edu.augustana;

import javafx.scene.control.TextField; 
import javafx.scene.layout.TilePane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SearchFunction {
    private TilePane cardImageView;
    private final Map<String, Card> cardMap;

    public SearchFunction() {
        this.cardMap = CardLibrary.cardMap;
    }

    public void initializeSearchField(TextField filterSearchField, TilePane cardImageView) {
        this.cardImageView = cardImageView;

        // Add a listener to the text property of the search field
        filterSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Trigger search when the text changes
            List<Card> searchResults = performSearch(newValue);

            // Update the ListView with the search results
            updateCardImageView();
        });
    }

    private void updateCardImageView() {
        // Update your ListView with the new search results
        cardImageView.getChildren();
    }

    public List<Card> performSearch(String query) {
        List<Card> searchResults = new ArrayList<>();

        if (query.trim().isEmpty()) {
            return new ArrayList<>(cardMap.values());
        }

        for (Card card : cardMap.values()) {
            if (card.getCode().equalsIgnoreCase(query) ||
                    card.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                    card.getCategory().equalsIgnoreCase(query) || card.getGender().equalsIgnoreCase(query) ||
                    Arrays.stream(card.getLevel()).anyMatch(level -> level.equalsIgnoreCase(query)) ||
                    card.getModelSex().equalsIgnoreCase(query)){

                searchResults.add(card);
            }
        }

        return searchResults;
    }
}
