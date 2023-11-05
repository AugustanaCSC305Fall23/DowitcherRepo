package edu.augustana;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CardGraphic {
    static Map map = new HashMap();
    public static HBox generateCardThumbnail(Card card) {
        HBox cardHBox = new HBox();
        cardHBox.setId(card.getCode() + "-" + card.getEvent());
        Image image = new Image(card.getPath());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(200);
        imageView.setFitWidth(270);
        cardHBox.getChildren().add(imageView);
        cardHBox.setOnDragDetected(e -> {
            System.out.println("Drag detected");
            cardHBox.startFullDrag();
            Dragboard dragboard = cardHBox.startDragAndDrop(javafx.scene.input.TransferMode.ANY);
            ClipboardContent clipboardContent = new ClipboardContent();
            clipboardContent.putString(card.getCode());
            dragboard.setContent(clipboardContent);

            e.consume();
        });
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
        vbox.setOnDragOver(e -> {
            if (e.getGestureSource() != vbox && e.getDragboard().hasString()) {
                e.acceptTransferModes(javafx.scene.input.TransferMode.ANY);
                //addCardToEventContainerGraphic(vbox, (Card) CardLibrary.cardMap.get(e.getDragboard().getString()));

            }
            e.consume();
        });
        hbox.setOnDragDropped(e -> {
            System.out.println("Drag dropped");
            if (e.getGestureSource() != vbox && e.getDragboard().hasString()) {
                addCardToEventContainerGraphic(vbox, (Card) CardLibrary.cardMap.get(e.getDragboard().getString()));
                eventContainer.addCard(e.getDragboard().getString());
            }
            e.consume();
        });
        map.put(eventContainer.getType(), vbox);
        return vbox;
    }

    public static void addCardToEventContainerGraphic(VBox eventContainerGraphic, Card card) {
        HBox hbox = (HBox) eventContainerGraphic.getChildren().get(2);
        HBox secondaryHbox;
        Image image = new Image(card.getPath());
        ImageView imageView = new ImageView(image);
        imageView.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                hbox.getChildren().remove(imageView);
                if (hbox.getChildren().size() == 1) {
                    System.out.println("Cant remove");
                }
                System.out.println("LOOK AT ME I AM HERE" + eventContainerGraphic.getId());
                EventContainer eventContainer = (EventContainer) App.currentLessonPlan.getEventMap().get(eventContainerGraphic.getId());
                eventContainer.removeCard(card.getCode());
                System.out.println(eventContainer.getCards());
            }
        });
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
