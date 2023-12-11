package edu.augustana;

import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * The SearchFunction class provides functionality for searching and updating a TilePane
 * with card images based on user input.   
 */
public class SearchFunction {
    private TilePane cardImageView;                // The TilePane used for displaying card images.
    private final Map<String, Card> cardMap;       //  A map containing cards where the key is the card code and the value is the Card object.
                                                   //  It represents the set of cards available for searching and display.

    /**
     * Constructs a SearchFunction object and initializes the cardMap.
     */
    public SearchFunction() {
        this.cardMap = CardLibrary.cardMap;
    }

    /**
     * Initializes the search field with a listener for text changes, triggering card searches
     * and updating the TilePane accordingly.
     *
     * @param filterSearchField The TextField used for input.
     * @param cardImageView     The TilePane used for displaying card images.
     */
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

    /**
     * Updates the TilePane with the search results.
     */
    private void updateCardImageView() {
        // Update your ListView with the new search results
        cardImageView.getChildren();
    }

    /**
     * Performs a search based on the provided query and returns a list of matching cards.
     *
     * @param query The search query.
     * @return A list of cards that match the search query.
     */
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
