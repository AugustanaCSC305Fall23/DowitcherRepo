package edu.augustana.ui;

import edu.augustana.App;
import edu.augustana.Card;
import edu.augustana.EventContainer;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;

/**
 * This class represents a CardUI object, which is a UI representation of a Card object
 */
public class CardUI extends VBox{

    private Card card; // Card object associated with this CardUI object

    private EventContainerUI inEventContainerUI; // EventContainerUI object this CardUI object is in



    public static final int CARD_THUMBNAIL_WIDTH = 270; // Width of a CardUI object visual representation
    public static final int CARD_THUMBNAIL_HEIGHT = 200; // Height of a CardUI object visual representation

    /**
     * Constructor for CardUI object
     * @param card - Card object associated with this CardUI object
     */
    public CardUI(Card card) {
        this(card, false);
    }

    public CardUI(Card card, Boolean useThumbnail) {
        this.card = card;
        this.setId(String.format("%s-%s", card.getCode(), card.getEvent()));
        Image cardImage;
        if (useThumbnail) {
            cardImage = new Image(card.getThumbnailPath());
        } else {
            cardImage = new Image(card.getPath());
        }
        ImageView cardImageView = new ImageView(cardImage);
        cardImageView.setFitHeight(CARD_THUMBNAIL_HEIGHT);
        cardImageView.setFitWidth(CARD_THUMBNAIL_WIDTH);
        this.getChildren().add(cardImageView);
        if (!card.getTitle().equalsIgnoreCase("blankcard")) {
            this.addCardZoomAndOutline();
            this.addCardDragDrop();
            this.addOnDoubleClicked();
        }
    }

    /**
     * Getter method for Card object associated with this CardUI object
     * @return - Card object associated with this CardUI object
     */
    public Card getCard() {
        return card;
    }

    /**
     * Adds an outline and a tooltip to this CardUI object that displays a zoomed in version of the card when the mouse hovers over it
     * @return - this CardUI object
     */
    public CardUI addCardZoomAndOutline() {
        this.setOnMouseEntered(e -> {
            ImageView enlargedImageView = new ImageView(((ImageView) this.getChildren().get(0)).getImage());
            enlargedImageView.setFitHeight(CARD_THUMBNAIL_HEIGHT * 2);
            enlargedImageView.setFitWidth(CARD_THUMBNAIL_WIDTH * 2); //Sets the imageview of the tooltip to be twice as large as the original
            Tooltip tooltip = new Tooltip();
            tooltip.setGraphic(enlargedImageView); //Sets the imageview of the tooltip to the enlarged imageview
            Tooltip.install(this, tooltip);
            this.setStyle("-fx-border-width: 2;" + "-fx-border-color: black;"); //Adds a black border to the card when the mouse hovers over it
        });
        this.setOnMouseExited(e -> {
            this.setStyle("-fx-border-width: 0;" + "-fx-border-color: black;"); //Removes the black border from the card when the mouse leaves
        });
        return this;
    }

    /**
     * Adds drag and drop functionality to this CardUI object
     * @return - this CardUI object
     */
    public CardUI addCardDragDrop() {
        this.setOnDragDetected(e -> {
            Dragboard db = this.startDragAndDrop(TransferMode.ANY);
            db.setDragView(new Image(card.getPath(), CARD_THUMBNAIL_WIDTH, CARD_THUMBNAIL_HEIGHT, false, false));
            ClipboardContent content = new ClipboardContent();
            content.putString(card.getCode());
            db.setContent(content);
            e.consume();
        });
        return this;
    }

    /**
     * Removes drag and drop functionality from this CardUI object
     * @return - this CardUI object
     */
    public CardUI removeDragDrop() {
        this.setOnDragDetected(e -> {
            Dragboard db = this.startDragAndDrop(TransferMode.ANY);
            db.setDragView(new Image(card.getPath(), CARD_THUMBNAIL_WIDTH, CARD_THUMBNAIL_HEIGHT, false, false));
            ClipboardContent content = new ClipboardContent();
            content.putString(card.getCode());
            db.setContent(content);
            e.consume();
        });
        this.setOnDragDone(e -> {
            if (inEventContainerUI != null) {
                inEventContainerUI.removeCard(this);
            }
            e.consume();
        });
        return this;
    }

    /**
     * Adds a list of equipment under this CardUI object
     * @return - this CardUI object
     */
    public CardUI addEquipmentText() {
        Label cardEquipment = new Label();
        if (!card.getEquipment()[0].equalsIgnoreCase("none")) {
            cardEquipment.setText("Equipment: ");
            for (String equipment : card.getEquipment()) {
                cardEquipment.setText(cardEquipment.getText() + "\n" + equipment);
            }
        }
        this.getChildren().add(cardEquipment);
        return this;
    }

    /**
     * Adds this CardUI object to an EventContainerUI object of the same event type when this CardUI object is double-clicked
     */
    public void addOnDoubleClicked() {
        this.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                CardUI newCardUI = new CardUI(card);
                newCardUI.addCardZoomAndOutline();
                newCardUI.removeOnDoubleClick();
                newCardUI.removeDragDrop();
                for (EventContainerUI eventContainerUI : App.getCurrentLessonPlanUI().getEventContainerUIList()) {
                    if (eventContainerUI.getEvent().equalsIgnoreCase(card.getEvent())) {
                        eventContainerUI.addCard(newCardUI);
                        App.getCurrentLessonPlan().getEquipmentList().addEquipment(card.getEquipment());
                        return;
                    }
                }
                App.getCurrentLessonPlanUI().addEventContainer(new EventContainerUI(new EventContainer(card.getEvent())));
                for (EventContainerUI eventContainerUI : App.getCurrentLessonPlanUI().getEventContainerUIList()) {
                    if (eventContainerUI.getEvent().equalsIgnoreCase(card.getEvent())) {
                        eventContainerUI.addCard(newCardUI);
                        App.getCurrentLessonPlan().getEquipmentList().addEquipment(card.getEquipment());
                        return;
                    }
                }

            }
        });
    }

    /**
     * Removes this CardUI object from its EventContainerUI object when this CardUI object is double-clicked
     */
    public void removeOnDoubleClick() {
        this.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                inEventContainerUI.removeCard(this);
                App.getCurrentLessonPlan().getEquipmentList().removeEquipment(card.getEquipment());
                System.out.println("Equipment List:");
            }
        });
    }

    /**
     * Setter method for EventContainerUI object associated with this CardUI object
     * @param inEventContainerUI - EventContainerUI object associated with this CardUI object
     */
    public void setInEventContainerUI(EventContainerUI inEventContainerUI) {
        this.inEventContainerUI = inEventContainerUI;
    }
}
