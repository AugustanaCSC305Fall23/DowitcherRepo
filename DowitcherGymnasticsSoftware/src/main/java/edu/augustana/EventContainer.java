package edu.augustana;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
public class EventContainer {
    private String title;
    private List<Card> cards;
    private String type;

    private VBox vbox;
    private HBox hbox;

    private HBox secondaryHbox;

    public EventContainer(String type){
        this.type = type;
        this.title = String.format("New %s Event", type);
        this.cards = new ArrayList<Card>();
        this.vbox = new VBox();
        Label titleLabel = new Label(title);
        Label typeLabel = new Label(String.format("(%s)", type));
        titleLabel.setStyle("-fx-font-size: 24;" + "-fx-font-weight: bold;");
        vbox.getChildren().add(titleLabel);
        vbox.getChildren().add(typeLabel);
        this.hbox = new HBox();
        vbox.getChildren().add(hbox);
        vbox.setStyle("-fx-border-width: 2;" + "-fx-border-color: black;");
        addCard(new Card());
    }

    public VBox getVbox(){
        return vbox;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public void addCard(Card newCard){
        cards.add(newCard);
        Image image = new Image(newCard.getPath());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(200);
        imageView.setFitWidth(270);
        if (hbox.getChildren().size() > 4 && secondaryHbox == null) {
            secondaryHbox = new HBox();
            vbox.getChildren().add(secondaryHbox);
            secondaryHbox.getChildren().add(0, imageView);
        } else if (hbox.getChildren().size() > 4) {
            secondaryHbox.getChildren().add(0, imageView);
        } else {
            hbox.getChildren().add(0, imageView);
        }
    }

    public void removeCard(){

    }

    public List<Card> getCards() {
        return cards;
    }
    public void setTitle(String title) {
        this.title = title;
    }
}
