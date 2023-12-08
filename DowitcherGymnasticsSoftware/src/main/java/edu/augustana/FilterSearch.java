package edu.augustana;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import edu.augustana.ui.CardUI;
import javafx.scene.control.CheckBox;
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

    public void applyFilter() {
        List<Card> filteredCards = new ArrayList<>();
        cardImageView.getChildren().clear(); // Clear existing items before adding filtered cards

        boolean anyCheckboxSelected = false;

        for (CheckBox checkbox : categoryCheckboxes) {
            if (checkbox.isSelected()) {
                anyCheckboxSelected = true;
                String category = checkbox.getText();
                for (Map.Entry<String, Card> entry : cardMap.entrySet()) {
                    Card card = entry.getValue();
                    for (String level : card.getLevel()) {
                        if (card.getCategory().equalsIgnoreCase(category) ||
                                card.getEvent().equalsIgnoreCase(category) ||
                                card.getGender().equalsIgnoreCase(category) ||
                                level.equalsIgnoreCase(category)) {

                            VBox cardThumbnail = generateCardThumbnail(card);
                            cardThumbnail.setId(card.getCode() + "-" + card.getEvent());
                            cardImageView.getChildren().add(cardThumbnail);
                            filteredCards.add(card);
                        }
                    }
                }
            }
        }

        // If no checkbox is selected, display all cards
        if (!anyCheckboxSelected) {
            for (Map.Entry<String, Card> entry : cardMap.entrySet()) {
                Card card = entry.getValue();
                VBox cardThumbnail = generateCardThumbnail(card);
                cardThumbnail.setId(card.getCode() + "-" + card.getEvent());
                cardImageView.getChildren().add(cardThumbnail);
                filteredCards.add(card);
            }
        }
    }

    public void clearFilter() {
        // Clear checkboxes and update cardImageView
        for (CheckBox checkbox : categoryCheckboxes) {
            checkbox.setSelected(false);
        }
        applyFilter();
        cardImageView.getChildren().clear();

        // Iterate through the original order of entries in cardMap
        for (Map.Entry<String, Card> entry : cardMap.entrySet()) {
            Card card = entry.getValue();
            VBox cardThumbnail = generateCardThumbnail(card);
            cardImageView.getChildren().add(cardThumbnail);
        }
    }

    private VBox generateCardThumbnail(Card card) {
        return new CardUI(card);
    }

}