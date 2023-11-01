package edu.augustana;
import javafx.fxml.FXML;
import java.util.*;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;

public class SearchFunction {
    private TextField filterSearchField;

    private ListView<Card> cardImageView;
    private final List<Card> cards;

    public SearchFunction(List<Card> cards) {
        this.cards = cards;
    }


    public List<Card> performSearch(String query) {
        List<Card> searchResults = new ArrayList<>();

        if (query.trim().isEmpty()) {
            return cards;
        }

        for (Card card : cards) {
            if (card.getCode().equalsIgnoreCase(query) ||
                    card.getTitle().toLowerCase().contains(query.toLowerCase())) {
                searchResults.add(card);
            }
        }

        return searchResults;
    }

    public Card searchByCode(String code) {
        for (Card card : cards) {
            if (card.getCode().equals(code)) {
                return card;
            }
        }
        return null;
    }

    public List<Card> searchByTitle(String title) {
        List<Card> matchingCards = new ArrayList<>();
        for (Card card : cards) {
            if (card.getTitle().equalsIgnoreCase(title)) {
                matchingCards.add(card);
            }
        }
        return matchingCards;
    }

}
