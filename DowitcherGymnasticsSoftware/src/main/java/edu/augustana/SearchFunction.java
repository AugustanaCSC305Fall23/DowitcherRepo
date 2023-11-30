package edu.augustana;

import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SearchFunction {
    private TextField filterSearchField;
    private ListView<Card> cardImageView; 
    private final Map<String, Card> cardMap;

    public SearchFunction(CardLibrary cardLibrary) {
        this.cardMap = CardLibrary.cardMap;
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

//    public Card searchByCode(String code) {
//        return cardMap.get(code);
//    }
//
//    public List<Card> searchByTitle(String title) {
//        List<Card> matchingCards = new ArrayList<>();
//        for (Card card : cardMap.values()) {
//            if (card.getTitle().equalsIgnoreCase(title)) {
//                matchingCards.add(card);
//            }
//        }
//        return matchingCards;
//    }
//
//    public List<Card> searchByCategory(String category) {
//        List<Card> matchingCards = new ArrayList<>();
//        for (Card card : cardMap.values()) {
//            if (card.getCategory().equalsIgnoreCase(category)) {
//                matchingCards.add(card);
//            }
//        }
//        return matchingCards;
//    }
}
