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
    private List<String> cards;
    private String type;

    public EventContainer(String type){
        this.type = type;
        this.title = String.format("%s", type);
        this.cards = new ArrayList<String>();
    }


    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public void addCard(String newCardCode){
        cards.add(newCardCode);
    }

    public void removeCard(String cardCode){
        for (int i = 0; i < cards.size(); i++){
            if (cards.get(i).equals(cardCode)){
                cards.remove(i);
            }
        }
    }

    public List<String> getCards() {
        return cards;
    }
    public void setTitle(String title) {
        this.title = title;
    }
}
