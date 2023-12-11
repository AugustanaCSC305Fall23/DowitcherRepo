package edu.augustana.ui;

import edu.augustana.GymnasticsPlannerApp;
import edu.augustana.datastructure.Card;
import edu.augustana.datastructure.CardLibrary;
import edu.augustana.datastructure.EventContainer;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;


/**
 * This class represents an EventContainerUI object, which is a UI representation of an EventContainer object
 */
public class EventContainerUI extends VBox {

    private EventContainer eventContainer;

    private TilePane cardTilePane;

    private List<CardUI> cardUIList;

    private Button removeEventContainerUIButton;

    private Label eventLabel;

    /**
     * Constructor for EventContainerUI object
     * @param eventContainer - EventContainer object associated with this EventContainerUI object
     */
    public EventContainerUI(EventContainer eventContainer) {
        this.eventContainer = eventContainer;
        cardUIList = new ArrayList<>();
        this.setId(String.format("%s", eventContainer.getType()));
        this.cardTilePane = new TilePane();
        this.fillWidthProperty().setValue(true);
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

    /**
     * Getter method for EventContainer object associated with this EventContainerUI object
     * @return - EventContainer object associated with this EventContainerUI object
     */
    public EventContainer getEventContainer() {
        return eventContainer;
    }

    /**
     * Getter method for the type of EventContainer object associated with this EventContainerUI object
     * @return - type of EventContainer object associated with this EventContainerUI object
     */
    public String getEvent() {
        return eventContainer.getType();
    }

    /**
     * Adds a CardUI object to this EventContainerUI object
     * @param cardUI - CardUI object to be added to this EventContainerUI object
     */
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

    }

    /**
     * Removes a CardUI object from this EventContainerUI object
     * @param cardUI - CardUI object to be removed from this EventContainerUI object
     */
    public void removeCard(CardUI cardUI) {
        eventContainer.removeCard(cardUI.getCard().getCode());
        cardUIList.remove(cardUI);
        cardTilePane.getChildren().remove(cardUI);
        if (cardTilePane.getChildren().isEmpty()) {
            addBlankCard();
        }
    }


    /**
     * Adds drag over functionality to this EventContainerUI object
     * @return - this EventContainerUI object's TilePane object
     */
    public TilePane addOnDragOver() {
        cardTilePane.setOnDragOver(e -> {
            if (e.getGestureSource() != cardTilePane && e.getDragboard().hasString()) {
                e.acceptTransferModes(javafx.scene.input.TransferMode.ANY);
            }
            e.consume();
        });
        return cardTilePane;
    }

    /**
     * Adds drag and drop functionality to this EventContainerUI object
     * @return - this EventContainerUI object's TilePane object
     */
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

    /**
     * Adds a blank card to this EventContainerUI object's TilePane object if it is empty
     */
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

    /**
     * Adds functionality to the removeEventContainerUIButton
     */
    public void removeEventContainerFunctionality() {
        removeEventContainerUIButton.setMinHeight(eventLabel.getHeight());
        removeEventContainerUIButton.setOnAction(e -> {
            GymnasticsPlannerApp.getCurrentLessonPlan().getEventList().remove(GymnasticsPlannerApp.getCurrentLessonPlan().getEventContainer(this.eventContainer.getTitle()));
            GymnasticsPlannerApp.getCurrentLessonPlanUI().removeEventContainer(this);
        });
    }

    /**
     * Draws the cards associated with this EventContainerUI object in its TilePane object
     */
    public int drawCardInEventContainerUI() {
        cardTilePane.getChildren().clear();
        int count = 0;
        for (String cardID : eventContainer.getCards()) {
            count++;
            CardUI cardUI = new CardUI((Card) CardLibrary.cardMap.get(cardID));
            cardUI.addCardZoomAndOutline();
            cardUI.removeOnDoubleClick();
            cardUI.removeDragDrop();
            cardTilePane.getChildren().add(cardUI);
        }
        return count;
    }

}
