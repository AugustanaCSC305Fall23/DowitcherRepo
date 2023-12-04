package edu.augustana;

import java.util.ArrayList;
import java.util.List;

import edu.augustana.ui.CardUI;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
//import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import static edu.augustana.CardLibrary.cardMap;

public class FilterSearch {
    private final List<CheckBox> categoryCheckboxes;
    private final List<Card> allCards;
    private final TilePane cardImageView;

    public FilterSearch(List<CheckBox> categoryCheckboxes, List<Card> allCards, TilePane cardImageView) {
        this.categoryCheckboxes = categoryCheckboxes;
        this.allCards = allCards;
        this.cardImageView = cardImageView;


        for (CheckBox checkbox : categoryCheckboxes) {
            checkbox.setOnAction(event -> applyFilter());
        }
    }

    ////////////////////////////////////////////////////////// **UPDATED CODE***

//    public void applyFilter() {
//        List<Card> filteredCards = new ArrayList<>();
//        cardImageView.getItems().clear(); // Clear existing items before adding filtered cards
//
//        for (CheckBox checkbox : categoryCheckboxes) {
//            if (checkbox.isSelected()) {
//                String category = checkbox.getText();
//                for (Card card : allCards) {
//                    for (String level : card.getLevel()) {
//                        if (card.getCategory().equalsIgnoreCase(category) || card.getEvent().equalsIgnoreCase(category) ||
//                                card.getGender().equalsIgnoreCase(category) || level.equalsIgnoreCase(category)) {
//                            VBox cardThumbnail = generateCardThumbnail(card);
//                            cardThumbnail.setId(card.getCode() + "-" + card.getEvent());
//                            cardImageView.getItems().add(cardThumbnail);
//                            filteredCards.add(card);
//                        }
//                    }
//                }
//            }
//        }
//    }



    public void applyFilter() {
        List<Card> filteredCards = new ArrayList<>();
        cardImageView.getChildren().clear(); // Clear existing items before adding filtered cards
        for (CheckBox checkbox : categoryCheckboxes) {
            if (checkbox.isSelected()) {
                String category = checkbox.getText();
                for (Card card : allCards) {
                    for (String level : card.getLevel()) {
                        if (card.getCategory().equalsIgnoreCase(category) || card.getEvent().equalsIgnoreCase(category) ||
                                card.getGender().equalsIgnoreCase(category) || level.equalsIgnoreCase(category)) {
                            VBox cardThumbnail = generateCardThumbnail(card);
                            cardThumbnail.setId(card.getCode() + "-" + card.getEvent());
                            cardImageView.getChildren().add(cardThumbnail);
                            filteredCards.add(card);
                        }
                    }
                }
            }
        }
    }

    private void addCardToFilteredList(Card card, List<Card> filteredCards) {
        VBox cardThumbnail = generateCardThumbnail(card);
        cardThumbnail.setId(card.getCode() + "-" + card.getEvent());
        cardImageView.getChildren().add(cardThumbnail);
        filteredCards.add(card);
    }

    private boolean cardMatchesCategory(Card card, String category) {
        return card.getCategory().equalsIgnoreCase(category) || card.getEvent().equalsIgnoreCase(category);
    }

    private boolean cardMatchesGender(Card card, String category) {
        String gender = card.getGender();
        return gender != null && gender.equalsIgnoreCase(category);
    }

    private boolean levelMatchesCategory(String level, String category) {
        return level.equalsIgnoreCase(category);
    }


//    public void clearFilter() {
//        // Clear checkboxes and update cardImageView
//        for (CheckBox checkbox : categoryCheckboxes) {
//            checkbox.setSelected(false);
//        }
//        applyFilter();
//        cardImageView.getChildren().clear();
//        for (Card card : allCards) {
//            VBox cardThumbnail = generateCardThumbnail(card);
//            cardImageView.getChildren().add(cardThumbnail);
//        }
//    }

    public void clearFilter() {
        // Clear checkboxes and update cardImageView
        for (CheckBox checkbox : categoryCheckboxes) {
            checkbox.setSelected(false);
        }

        applyFilter();
        cardImageView.getChildren().clear();

        // Iterate through the values in the cardMap
        for (Card card : cardMap.values()) {
            VBox cardThumbnail = generateCardThumbnail(card);
            cardImageView.getChildren().add(cardThumbnail);
        }
    }


    private VBox generateCardThumbnail(Card card) {
        return new CardUI(card);
    }


////////////////////////////////////////////////////////////////////////

}