package edu.augustana;

import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CardGraphic {

    public static final int CARD_THUMBNAIL_WIDTH = 270;
    public static final int CARD_THUMBNAIL_HEIGHT = 200;

    public static final int MAX_CARDS_PER_EVENT = 8;
    static Map map = new HashMap();

    public static VBox generateCardThumbnail(Card card) { //Creates a thumbnail of the card within an HBox
        VBox cardVBox = new VBox();
        cardVBox.setId(card.getCode() + "-" + card.getEvent());
        Image image = new Image(card.getPath());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(CARD_THUMBNAIL_HEIGHT);
        imageView.setFitWidth(CARD_THUMBNAIL_WIDTH);
        cardVBox.getChildren().add(imageView);
        return cardVBox;
    }

    public static VBox addCardZoom(VBox cardVBox) {//Adds a zoom feature to the card when the mouse hovers over it
        cardVBox.setOnMouseEntered(e -> {
            ImageView enlargedImageView = new ImageView(((ImageView) cardVBox.getChildren().get(0)).getImage());
            enlargedImageView.setFitHeight(CARD_THUMBNAIL_HEIGHT * 2);
            enlargedImageView.setFitWidth(CARD_THUMBNAIL_WIDTH * 2);
            Tooltip tooltip = new Tooltip();
            tooltip.setGraphic(enlargedImageView);
            Tooltip.install(cardVBox, tooltip);

        });
        return cardVBox;
    }

    public static VBox addCardOutline(VBox cardVBox) {//Adds an outline to the card when the mouse hovers over it
        cardVBox.setOnMouseEntered(e -> {
            cardVBox.setStyle("-fx-border-width: 2;" + "-fx-border-color: black;");
        });
        cardVBox.setOnMouseExited(e -> {
            cardVBox.setStyle("-fx-border-width: 0;" + "-fx-border-color: black;");
        });
        return cardVBox;
    }
    public static VBox addCardDragDrop(VBox cardVBox) {//Adds drag and drop functionality to the card
        cardVBox.setOnDragDetected(e -> {
            Card currentCard = (Card) CardLibrary.cardMap.get(cardVBox.getId().split("-")[0]);
            cardVBox.startFullDrag();
            Dragboard dragboard = cardVBox.startDragAndDrop(TransferMode.ANY);
            dragboard.setDragView(new Image(currentCard.getPath(), CARD_THUMBNAIL_WIDTH, CARD_THUMBNAIL_HEIGHT, false, false));
            ClipboardContent clipboardContent = new ClipboardContent();
            clipboardContent.putString(currentCard.getCode());
            dragboard.setContent(clipboardContent);

            e.consume();
        });
        return cardVBox;
    }

    public static VBox createCardWithAllFeatures(Card card) {//Creates a card with all features
        return addCardDragDrop(addCardOutline(addCardZoom(generateCardThumbnail(card))));
    }

    public static VBox addEquipmentText(VBox cardVBox, Card card) {
        Label cardEquipment = new Label();
        if (!card.getEquipment()[0].equalsIgnoreCase("none")) {
            cardEquipment.setText("Equipment: ");
            for (String equipment : card.getEquipment()) {
                cardEquipment.setText(cardEquipment.getText() + "\n" + equipment + " ");
            }
        }
        cardVBox.getChildren().add(cardEquipment);
        return cardVBox;
    }

    public static VBox addDoubleClickToRemove(VBox eventContainerGraphic, VBox cardVBox, TilePane tilePane, Card card) {
        cardVBox.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                tilePane.getChildren().remove(cardVBox);
                if (tilePane.getChildren().size() == 1) {
                    System.out.println("Cant remove");
                }
                EventContainer eventContainer = (EventContainer) App.currentLessonPlan.getEventMap().get(eventContainerGraphic.getId());
                eventContainer.removeCard(card.getCode());
                System.out.println(eventContainer.getCards());
                if (!tilePane.getChildren().get(tilePane.getChildren().size()-1).getId().equalsIgnoreCase("blankcard")) {
                    VBox blankCard = generateCardThumbnail(new Card());
                    blankCard.setId("blankcard");
                    tilePane.getChildren().add(blankCard);
                }
            }
        });
        return cardVBox;
    }

    public static VBox generateEventContainerGraphic(EventContainer eventContainer) {
        VBox eventContainerGraphicVBox = new VBox();
        TilePane tilePane = new TilePane();
        eventContainerGraphicVBox.setId(eventContainer.getType());
        Label typeLabel = new Label(String.format("%s", eventContainer.getType()));
        typeLabel.setStyle("-fx-font-size: 24;" + "-fx-font-weight: bold;");
        eventContainerGraphicVBox.getChildren().add(typeLabel);
        VBox blankCard = generateCardThumbnail(new Card());
        blankCard.setId("blankcard");
        tilePane.getChildren().addAll(blankCard);
        eventContainerGraphicVBox.getChildren().add(tilePane);
        eventContainerGraphicVBox.setStyle("-fx-border-width: 2;" + "-fx-border-color: black;");
        eventContainerGraphicVBox.setOnDragOver(e -> {
            if (e.getGestureSource() != eventContainerGraphicVBox && e.getDragboard().hasString()) {
                e.acceptTransferModes(javafx.scene.input.TransferMode.ANY);
            }
            e.consume();
        });
        tilePane.setOnDragDropped(e -> {
            System.out.println("Drag dropped");
            if (e.getGestureSource() != eventContainerGraphicVBox && e.getDragboard().hasString()) {
                addCardToEventContainerGraphic(eventContainerGraphicVBox, (Card) CardLibrary.cardMap.get(e.getDragboard().getString()));
                eventContainer.addCard(e.getDragboard().getString());
            }
            e.consume();
        });
        map.put(eventContainer.getType(), eventContainerGraphicVBox);
        return eventContainerGraphicVBox;
    }

    public static VBox addCardToEventContainerGraphic(VBox eventContainerGraphic, Card card) {
        TilePane tilePane = (TilePane) eventContainerGraphic.getChildren().get(1);
        tilePane.setMinSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        tilePane.setMaxWidth(CARD_THUMBNAIL_WIDTH*5);
        VBox cardContainer = createCardWithAllFeatures(card);
        cardContainer.setId(card.getCode());
        addEquipmentText(cardContainer, card);
        addDoubleClickToRemove(eventContainerGraphic,cardContainer, tilePane, card);
        if (tilePane.getChildren().size() < MAX_CARDS_PER_EVENT) {
            tilePane.getChildren().add(tilePane.getChildren().size() - 1, cardContainer);
        } else if (tilePane.getChildren().size() == MAX_CARDS_PER_EVENT) {
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
