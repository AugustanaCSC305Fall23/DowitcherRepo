package edu.augustana;
import java.util.*;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class SearchFunction {
    private TextField filterSearchField;

    private ListView<Card> cardImageView;
    private final List<Card> cards;


    public SearchFunction(List<Card> cards) {
        this.cards = cards;
    }  // Constructor of the SearchFunction Class

    public List<Card> performSearch(String query) {


        // performSearch creates an arrayList to store the cards that meet the text inside of text SearchBox

        List<Card> searchResults = new ArrayList<>();

        if (query.trim().isEmpty()) {
            return cards;
        }

        for (Card card : cards) {
            if (card.getCode().equalsIgnoreCase(query) ||
                    card.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                    card.getCategory().equalsIgnoreCase(query)) {
                searchResults.add(card);
            }
        }

        return searchResults;
    }

    public Card searchByCode(String code) {                         // Will be utilized LATER FOR CSV 
        for (Card card : cards) {
            if (card.getCode().equals(code)) {
                return card;
            }
        }
        return null;
    }

    public List<Card> searchByTitle(String title) {                 // Will be utilized LATER FOR CSV
        List<Card> matchingCards = new ArrayList<>();
        for (Card card : cards) {
            if (card.getTitle().equalsIgnoreCase(title)) {
                matchingCards.add(card);
            }
        }
        return matchingCards;
    }

    public List<Card> searchByCategory(String category) {           // Will be utilized LATER FOR CSV
        List<Card> matchingCards = new ArrayList<>();
        for (Card card : cards) {
            if (card.getCategory().equalsIgnoreCase(category)) {
                matchingCards.add(card);
            }
        }
        return matchingCards;
    }

}