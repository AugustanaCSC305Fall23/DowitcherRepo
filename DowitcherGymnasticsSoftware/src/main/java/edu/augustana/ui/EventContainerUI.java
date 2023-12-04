package edu.augustana.ui;

import edu.augustana.App;
import edu.augustana.Card;
import edu.augustana.CardLibrary;
import edu.augustana.EventContainer;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;



public class EventContainerUI extends VBox {

    private EventContainer eventContainer;

    private TilePane cardTilePane;

    private List<CardUI> cardUIList;

    private Button removeEventContainerUIButton;

    private Label eventLabel;

    public EventContainerUI(EventContainer eventContainer) {
        this.eventContainer = eventContainer;
        cardUIList = new ArrayList<>();
        this.setId(String.format("%s", eventContainer.getType()));
        this.cardTilePane = new TilePane();
//        this.cardTilePane.setMaxWidth(CardUI.CARD_THUMBNAIL_WIDTH * 4 + CardUI.CARD_THUMBNAIL_WIDTH / 4);
        this.cardTilePane.setMaxWidth(USE_COMPUTED_SIZE);
        this.setMaxWidth(USE_COMPUTED_SIZE);
        HBox eventHBox = new HBox();
        eventLabel = new Label(eventContainer.getType());
        eventLabel.setStyle("-fx-font-size: 24;" + "-fx-font-weight: bold;");
        removeEventContainerUIButton = new Button("Remove Event");
        removeEventContainerFunctionality();
        eventHBox.getChildren().addAll(eventLabel, removeEventContainerUIButton);
        this.getChildren().addAll(eventHBox, cardTilePane);
        this.setStyle("-fx-border-width: 2;" + "-fx-border-color: red;");
        this.addOnDragOver();
        this.addOnDragDropped();
        addBlankCard();
    }

    public EventContainer getEventContainer() {
        return eventContainer;
    }

    public String getEvent() {
        return eventContainer.getType();
    }

    public void addCard(CardUI cardUI) {
        if (cardUIList.size() < 8 && !eventContainer.getCards().contains(cardUI.getCard().getCode()) && !cardUI.getCard().getTitle().equalsIgnoreCase("blankcard")) {
            cardUI.removeOnDoubleClick();
            cardUI.addCardZoomAndOutline();
            cardUI.removeDragDrop();
            cardUIList.add(0, cardUI);
            cardTilePane.getChildren().add(0, cardUI);
            eventContainer.addCard(cardUI.getCard().getCode());
            cardUI.setInEventContainerUI(this);
            if (cardTilePane.getChildren().contains(cardTilePane.lookup("#blankcard"))) {
                cardTilePane.getChildren().remove(cardTilePane.lookup("#blankcard"));
            }
        }
//        System.out.println(String.format("\nCurrent Lesson Plan: %s", App.getCurrentLessonPlan().getTitle()));
//        for (Object eventContainerKey : App.getCurrentLessonPlan().getEventMap().keySet()) {
//            EventContainer printableEventContainer = (EventContainer) App.getCurrentLessonPlan().getEventMap().get(eventContainerKey);
//            System.out.println(String.format("Event Container: %s", printableEventContainer.getType()));
//            for (String cardCode : printableEventContainer.getCards()) {
//                System.out.println(String.format("Card Code: %s", cardCode));
//            }
//        }

    }

    public void removeCard(CardUI cardUI) {
        eventContainer.removeCard(cardUI.getCard().getCode());
        cardUIList.remove(cardUI);
        cardTilePane.getChildren().remove(cardUI);
        if (cardTilePane.getChildren().isEmpty()) {
            addBlankCard();
        }
    }

    public List<CardUI> getCardUIList() {
        return cardUIList;
    }

    public TilePane getCardTilePane() {
        return cardTilePane;
    }

    public TilePane addOnDragOver() {
        cardTilePane.setOnDragOver(e -> {
            if (e.getGestureSource() != cardTilePane && e.getDragboard().hasString()) {
                e.acceptTransferModes(javafx.scene.input.TransferMode.ANY);
            }
            e.consume();
        });
        return cardTilePane;
    }

    public TilePane addOnDragDropped() {
        cardTilePane.setOnDragDropped(e -> {
            System.out.println("Drag dropped");
            if (e.getGestureSource() != cardTilePane && e.getDragboard().hasString()) {
                CardUI cardUI = new CardUI((Card) CardLibrary.cardMap.get(e.getDragboard().getString()));
                addCard(cardUI);
            }
            e.consume();
        });
        return cardTilePane;
    }

    public void addBlankCard() {
        if (cardTilePane.getChildren().isEmpty()) {
            Card blankCard = new Card();
            blankCard.setPackFolder("Blank Card");
            blankCard.setPath("Blank Card.png");
            CardUI blankCardUI = new CardUI(blankCard);
            blankCardUI.setId("blankcard");
            cardTilePane.getChildren().add(blankCardUI);
        }
    }

    public void removeEventContainerFunctionality() {
        removeEventContainerUIButton.setMinHeight(eventLabel.getHeight());
        removeEventContainerUIButton.setOnAction(e -> {
            App.getCurrentLessonPlanUI().removeEventContainer(this);
            App.getCurrentLessonPlan().getEventMap().remove(this.getEvent());
        });
    }

    public void drawCardInEventContainerUI() {
        cardTilePane.getChildren().clear();
        for (String cardID : eventContainer.getCards()) {
            CardUI cardUI = new CardUI((Card) CardLibrary.cardMap.get(cardID));
            cardUI.addCardZoomAndOutline();
            cardUI.removeOnDoubleClick();
            cardUI.removeDragDrop();
            cardTilePane.getChildren().add(cardUI);
        }
    }

}
