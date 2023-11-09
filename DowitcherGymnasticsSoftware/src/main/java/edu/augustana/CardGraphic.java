package edu.augustana;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.layout.*;

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
            dragboard.setDragView(new Image(card.getPath(), 270, 200, false, false));
            ClipboardContent clipboardContent = new ClipboardContent();
            clipboardContent.putString(card.getCode());
            dragboard.setContent(clipboardContent);

            e.consume();
        });
        cardHBox.setOnMouseEntered(e -> {
            cardHBox.setStyle("-fx-border-width: 2;" + "-fx-border-color: black;");

        });
        cardHBox.setOnMouseExited(e -> {
            cardHBox.setStyle("-fx-border-width: 0;" + "-fx-border-color: black;");
        });
        return cardHBox;
    }

    public static VBox generateEventContainerGraphic(EventContainer eventContainer) {
        VBox vbox = new VBox();
        TilePane tilePane = new TilePane();
        vbox.setId(eventContainer.getType());
        Label titleLabel = new Label(eventContainer.getTitle());
        Label typeLabel = new Label(String.format("(%s)", eventContainer.getType()));
        titleLabel.setStyle("-fx-font-size: 24;" + "-fx-font-weight: bold;");
        vbox.getChildren().add(titleLabel);
        vbox.getChildren().add(typeLabel);
        HBox blankCard = generateCardThumbnail(new Card());
        blankCard.setId("blankcard");
        tilePane.getChildren().addAll(blankCard);
        vbox.getChildren().add(tilePane);
        vbox.setStyle("-fx-border-width: 2;" + "-fx-border-color: black;");
        vbox.setOnDragOver(e -> {
            if (e.getGestureSource() != vbox && e.getDragboard().hasString()) {
                e.acceptTransferModes(javafx.scene.input.TransferMode.ANY);
                //addCardToEventContainerGraphic(vbox, (Card) CardLibrary.cardMap.get(e.getDragboard().getString()));

            }
            e.consume();
        });
        tilePane.setOnDragDropped(e -> {
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

    public static VBox addCardToEventContainerGraphic(VBox eventContainerGraphic, Card card) {
        TilePane tilePane = (TilePane) eventContainerGraphic.getChildren().get(2);
        tilePane.setMinSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        tilePane.setMaxWidth(270*5);
        HBox secondaryHbox;
        VBox cardContainer = new VBox();
        cardContainer.setId(card.getCode());
        Image image = new Image(card.getPath());
        ImageView imageView = new ImageView(image);
        Label cardEquipment = new Label();
        if (!card.getEquipment()[0].equalsIgnoreCase("none")) {
            cardEquipment.setText("Equipment: ");
            for (String equipment : card.getEquipment()) {
                cardEquipment.setText(cardEquipment.getText() + "\n" + equipment + " ");
            }
        }
        cardContainer.getChildren().addAll(imageView, cardEquipment);
        imageView.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                tilePane.getChildren().remove(cardContainer);
                if (tilePane.getChildren().size() == 1) {
                    System.out.println("Cant remove");
                }
                EventContainer eventContainer = (EventContainer) App.currentLessonPlan.getEventMap().get(eventContainerGraphic.getId());
                eventContainer.removeCard(card.getCode());
                System.out.println(eventContainer.getCards());
                if (!tilePane.getChildren().get(tilePane.getChildren().size()-1).getId().equalsIgnoreCase("blankcard")) {
                    HBox blankCard = generateCardThumbnail(new Card());
                    blankCard.setId("blankcard");
                    tilePane.getChildren().add(blankCard);
                }
            }
        });
        imageView.setFitHeight(200);
        imageView.setFitWidth(270);
//        if (gridPane.getChildren().size() >= 4 && eventContainerGraphic.getChildren().size() == 3) {
//            gridPane.getChildren().remove(gridPane.getChildren().size() - 1);
//            gridPane.getChildren().add(cardContainer);
//            secondaryHbox = new HBox();
//            eventContainerGraphic.getChildren().add(secondaryHbox);
//            secondaryHbox.getChildren().add(0, generateCardThumbnail(new Card()));
//        } else if (gridPane.getChildren().size() >= 4 && eventContainerGraphic.getChildren().size() == 4) {
//            secondaryHbox = (HBox) eventContainerGraphic.getChildren().get(3);
//            secondaryHbox.getChildren().add(secondaryHbox.getChildren().size() -1, cardContainer);
//            if (secondaryHbox.getChildren().size() > 4) {
//                secondaryHbox.getChildren().remove(secondaryHbox.getChildren().size() - 1);
//            }
//        } else {
//            gridPane.getChildren().add(gridPane.getChildren().size() - 1, cardContainer);
//        }
        if (tilePane.getChildren().size() < 8) {
            tilePane.getChildren().add(tilePane.getChildren().size() - 1, cardContainer);
        } else if (tilePane.getChildren().size() == 8) {
            tilePane.getChildren().remove(tilePane.getChildren().size() - 1);
            tilePane.getChildren().add(tilePane.getChildren().size(), cardContainer);
        }
        System.out.println(tilePane.getChildren().size());
        return eventContainerGraphic;
    }

    public static VBox getEventContainer(String type){
        return (VBox) map.get(type);
    }
}
