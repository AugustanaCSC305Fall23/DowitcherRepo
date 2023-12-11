package edu.augustana.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.augustana.datastructure.Card;
import edu.augustana.ui.CardUI;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import static edu.augustana.datastructure.CardLibrary.cardMap;


/**
 * The FilterSearch class is responsible for filtering and displaying a set of cards based on checkboxes.
 */
public class FilterSearch {
    private final List<CheckBox> categoryCheckboxes;    // A list of checkboxes representing different categories for filtering.
    private final List<Card> allCards;                  // A list of all cards that can be filtered.
    private final TilePane cardImageView;               // The TilePane used for displaying card images.

    /**
     * Constructs a FilterSearch object with the specified parameters.
     *
     * @param categoryCheckboxes A list of checkboxes representing different categories for filtering.
     * @param allCards           A list of all cards that can be filtered.
     * @param cardImageView      The TilePane used for displaying card images.
     */
    public FilterSearch(List<CheckBox> categoryCheckboxes, List<Card> allCards, TilePane cardImageView) {
        this.categoryCheckboxes = categoryCheckboxes;
        this.allCards = allCards;
        this.cardImageView = cardImageView;


        for (CheckBox checkbox : categoryCheckboxes) {
            checkbox.setOnAction(event -> applyFilter());
        }
    }

    /**
     * Applies the selected filters and updates the display of card thumbnails.
     */
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

    /**
     * Clears all filters and displays all cards.
     */
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

    /**
     * Generates a card thumbnail for the specified card.
     *
     * @param card The card for which to generate the thumbnail.
     * @return A VBox containing the UI representation of the card thumbnail.
     */
    private VBox generateCardThumbnail(Card card) {
        return new CardUI(card);
    }

}