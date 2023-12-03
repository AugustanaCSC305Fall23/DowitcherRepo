package edu.augustana;

import java.util.ArrayList;
import java.util.List;

import edu.augustana.ui.CardUI;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
//import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FilterSearch {
    private final List<CheckBox> categoryCheckboxes;
    private final List<Card> allCards;
    private final ListView<VBox> cardImageView;

    public FilterSearch(List<CheckBox> categoryCheckboxes, List<Card> allCards, ListView<VBox> cardImageView) {
        this.categoryCheckboxes = categoryCheckboxes;
        this.allCards = allCards;
        this.cardImageView = cardImageView;


        for (CheckBox checkbox : categoryCheckboxes) {
            checkbox.setOnAction(event -> applyFilter());
        }
    }

    ////////////////////////////////////////////////////////// **UPDATED CODE***

    public void applyFilter() {
        List<Card> filteredCards = new ArrayList<>();
        cardImageView.getItems().clear(); // Clear existing items before adding filtered cards

        for (CheckBox checkbox : categoryCheckboxes) {
            if (checkbox.isSelected()) {
                String category = checkbox.getText();
                for (Card card : allCards) {
                    for (String level : card.getLevel()) {
                        if (card.getCategory().equalsIgnoreCase(category) || card.getEvent().equalsIgnoreCase(category) ||
                                card.getGender().equalsIgnoreCase(category) || level.equalsIgnoreCase(category)) {
                            VBox cardThumbnail = generateCardThumbnail(card);
                            cardThumbnail.setId(card.getCode() + "-" + card.getEvent());
                            cardImageView.getItems().add(cardThumbnail);
                            filteredCards.add(card);
                        }
                    }
                }
            }
        }
    }

    public void clearFilter() {
        // Clear checkboxes and update cardImageView
        for (CheckBox checkbox : categoryCheckboxes) {
            checkbox.setSelected(false);
        }
        applyFilter();
        cardImageView.getItems().clear();
        for (Card card : allCards) {
            VBox cardThumbnail = generateCardThumbnail(card);
            cardImageView.getItems().add(cardThumbnail);
        }
    }

    private VBox generateCardThumbnail(Card card) {
        return new CardUI(card);
    }


////////////////////////////////////////////////////////////////////////

//    public void updateCardImageView(List<Card> searchResults) {
//        cardImageView.getItems().clear();
//
//        for (Card card : searchResults) {
//            VBox thumbnail = generateCardThumbnail(card);
//            cardImageView.getItems().add(thumbnail);
//        }
//    }
//
//
//    public List<Card> performFilterSearch(String query) {
//        List<Card> searchResults = new ArrayList<>();
//
//        if (query.trim().isEmpty()) {
//            return allCards;
//        }
//
//        for (Card card : allCards) {
//            if (card.getCode().equalsIgnoreCase(query) ||
//                    card.getTitle().toLowerCase().contains(query.toLowerCase()) ||
//                    card.getCategory().equalsIgnoreCase(query)) {
//                searchResults.add(card);
//            }
//        }
//
//        return searchResults;
//    }
//
//

}