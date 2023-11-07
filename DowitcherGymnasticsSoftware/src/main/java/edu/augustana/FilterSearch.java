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
                    if (card.getCategory().equalsIgnoreCase(category)) {
                        HBox cardThumbnail = generateCardThumbnail(card);
                        filteredCards.add(cardThumbnail);
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
        HBox cardHBox = new HBox();

        ImageView imageView = new ImageView(new Image(card.getPath()));
        imageView.setFitHeight(200);
        imageView.setFitWidth(270);

        Label titleLabel = new Label(card.getTitle());

        cardHBox.getChildren().addAll(imageView, titleLabel);

        return cardHBox;
    } 


    public void updateCardImageView(List<Card> searchResults) {
        cardImageView.getItems().clear();

        for (Card card : searchResults) {
            HBox thumbnail = generateCardThumbnail(card);
            cardImageView.getItems().add(thumbnail);
        }
    }


}