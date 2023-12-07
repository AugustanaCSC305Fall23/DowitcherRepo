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

public class CardUI extends VBox{

    private Card card;

    private EventContainerUI inEventContainerUI;



    public static final int CARD_THUMBNAIL_WIDTH = 270;
    public static final int CARD_THUMBNAIL_HEIGHT = 200;

    public CardUI(Card card) {
        this.card = card;
        this.setId(String.format("%s-%s", card.getCode(), card.getEvent()));
        Image cardImage = new Image(card.getPath());
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

    public Card getCard() {
        return card;
    }

    public String getCode() {
        return card.getCode();
    }

    public CardUI addCardZoomAndOutline() {
        this.setOnMouseEntered(e -> {
            ImageView enlargedImageView = new ImageView(((ImageView) this.getChildren().get(0)).getImage());
            enlargedImageView.setFitHeight(CARD_THUMBNAIL_HEIGHT * 2);
            enlargedImageView.setFitWidth(CARD_THUMBNAIL_WIDTH * 2);
            Tooltip tooltip = new Tooltip();
            tooltip.setGraphic(enlargedImageView);
            Tooltip.install(this, tooltip);
            this.setStyle("-fx-border-width: 2;" + "-fx-border-color: black;");
        });
        this.setOnMouseExited(e -> {
            this.setStyle("-fx-border-width: 0;" + "-fx-border-color: black;");
        });
        return this;
    }


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
//                        App.getCurrentCourse().getLessonPlanMap().put(App.getCurrentLessonPlan().getTitle(), App.getCurrentLessonPlan());
                        return;
                    }
                }
                App.getCurrentLessonPlanUI().addEventContainer(new EventContainerUI(new EventContainer(card.getEvent())));
                for (EventContainerUI eventContainerUI : App.getCurrentLessonPlanUI().getEventContainerUIList()) {
                    if (eventContainerUI.getEvent().equalsIgnoreCase(card.getEvent())) {
                        eventContainerUI.addCard(newCardUI);
//                        App.getCurrentCourse().getLessonPlanMap().put(App.getCurrentLessonPlan().getTitle(), App.getCurrentLessonPlan());
                        return;
                    }
                }
            }
        });
    }

    public void removeOnDoubleClick() {
        this.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                inEventContainerUI.removeCard(this);
            }
        });
    }

    public void setInEventContainerUI(EventContainerUI inEventContainerUI) {
        this.inEventContainerUI = inEventContainerUI;
    }

}
