package edu.augustana;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CardGraphic {
    static Map map = new HashMap();
    public static HBox generateCardThumbnail(Card card) {
        HBox cardHBox = new HBox();
        cardHBox.setId(card.getEvent());
        Image image = new Image(card.getPath());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(200);
        imageView.setFitWidth(270);
        cardHBox.getChildren().add(imageView);
        return cardHBox;
    }

    public static VBox generateEventContainerGraphic(EventContainer eventContainer) {
        VBox vbox = new VBox();
        vbox.setId(eventContainer.getType());
        Label titleLabel = new Label(eventContainer.getTitle());
        Label typeLabel = new Label(String.format("(%s)", eventContainer.getType()));
        titleLabel.setStyle("-fx-font-size: 24;" + "-fx-font-weight: bold;");
        vbox.getChildren().add(titleLabel);
        vbox.getChildren().add(typeLabel);
        HBox hbox = new HBox();
        hbox.getChildren().add(generateCardThumbnail(new Card()));
        vbox.getChildren().add(hbox);
        vbox.setStyle("-fx-border-width: 2;" + "-fx-border-color: black;");
        map.put(eventContainer.getType(), vbox);
        return vbox;
    }

    public static void addCardToEventContainerGraphic(VBox eventContainerGraphic, Card card) {
        HBox hbox = (HBox) eventContainerGraphic.getChildren().get(2);
        HBox secondaryHbox;
        Image image = new Image(card.getPath());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(200);
        imageView.setFitWidth(270);
        if (hbox.getChildren().size() >= 4 && eventContainerGraphic.getChildren().size() == 3) {
            hbox.getChildren().remove(hbox.getChildren().size() - 1);
            hbox.getChildren().add(imageView);
            secondaryHbox = new HBox();
            eventContainerGraphic.getChildren().add(secondaryHbox);
            secondaryHbox.getChildren().add(0, generateCardThumbnail(new Card()));
        } else if (hbox.getChildren().size() >= 4 && eventContainerGraphic.getChildren().size() == 4) {
            secondaryHbox = (HBox) eventContainerGraphic.getChildren().get(3);
            secondaryHbox.getChildren().add(secondaryHbox.getChildren().size() -1, imageView);
            if (secondaryHbox.getChildren().size() > 4) {
                secondaryHbox.getChildren().remove(secondaryHbox.getChildren().size() - 1);
            }
        } else {
            hbox.getChildren().add(hbox.getChildren().size() - 1, imageView);
        }
    }

    public static VBox getEventContainer(String type){
        return (VBox) map.get(type);
    }
}
