package edu.augustana;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class FilterSearch {
    private final List<CheckBox> categoryCheckboxes;
    private final List<Card> allCards;
    private final ListView<HBox> cardImageView;

    public FilterSearch(List<CheckBox> categoryCheckboxes, List<Card> allCards, ListView<HBox> cardImageView) {
        this.categoryCheckboxes = categoryCheckboxes;
        this.allCards = allCards;
        this.cardImageView = cardImageView;
    }

    public List<HBox> applyFilter() {
        List<HBox> filteredCards = new ArrayList<>();
        for (CheckBox checkbox : categoryCheckboxes) {
            if (checkbox.isSelected()) {
                String category = checkbox.getText();
                for (Card card : allCards) {
                    for (String level : card.getLevel()) {
                        if (card.getCategory().equalsIgnoreCase(category) || card.getEvent().equalsIgnoreCase(category) ||
                                card.getGender().equalsIgnoreCase(category) || level.equalsIgnoreCase(category)) {
                            HBox cardThumbnail = generateCardThumbnail(card);
                            cardThumbnail.setId(card.getCode() + "-" + card.getEvent());
                            filteredCards.add(cardThumbnail);
                        }
                    }

                }
            }
        }
        return filteredCards;
    }

    public void clearFilter() {
        cardImageView.getItems().clear();
        for (Card card : allCards) {
            HBox cardThumbnail = generateCardThumbnail(card);
            cardImageView.getItems().add(cardThumbnail);
        }
    }

    private HBox generateCardThumbnail(Card card) {
        HBox cardHBox = CardGraphic.generateCardThumbnail(card);

        return cardHBox;
    }


    public void updateCardImageView(List<Card> searchResults) {
        cardImageView.getItems().clear();

        for (Card card : searchResults) {
            HBox thumbnail = generateCardThumbnail(card);
            cardImageView.getItems().add(thumbnail);
        }
    }


    public List<Card> performFilterSearch(String query) {
        List<Card> searchResults = new ArrayList<>();

        if (query.trim().isEmpty()) {
            return allCards;
        }

        for (Card card : allCards) {
            if (card.getCode().equalsIgnoreCase(query) ||
                    card.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                    card.getCategory().equalsIgnoreCase(query)) {
                searchResults.add(card);
            }
        }

        return searchResults;
    }

    public void applySearchFilter(String query) {
        List<Card> searchResults = performFilterSearch(query);
        updateCardImageView(searchResults);
    }


}