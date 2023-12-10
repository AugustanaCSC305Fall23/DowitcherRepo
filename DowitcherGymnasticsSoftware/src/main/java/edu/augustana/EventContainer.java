package edu.augustana;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * This class is used to create an EventContainer object that contains multiple Card objects.
 */
public class EventContainer {
    private String title;
    private List<String> cards;
    private String type;

    /**
     * Constructor for EventContainer object
     * @param type - type/event of the EventContainer object
     */
    public EventContainer(String type){
        this.type = type;
        this.title = String.format("%s", type);
        this.cards = new ArrayList<String>();
    }


    /**
     * Getter method for the title of the EventContainer object
     * @return - title of the EventContainer object
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter method for the type/event of the EventContainer object
     * @return - type/event of the EventContainer object
     */
    public String getType() {
        return type;
    }

    /**
     * Adds a card to this EventContainer object
     * @param newCardCode - code of the card to be added
     */
    public void addCard(String newCardCode){
        cards.add(newCardCode);
    }

    /**
     * Removes a card from this EventContainer object
     * @param cardCode - code of the card to be removed
     */
    public void removeCard(String cardCode){
        for (int i = 0; i < cards.size(); i++){
            if (cards.get(i).equals(cardCode)){
                cards.remove(i);
            }
        }
    }

    /**
     * Getter method for the list of cards in this EventContainer object
     * @return - list of cards in this EventContainer object
     */
    public List<String> getCards() {
        return cards;
    }

    /**
     * Setter method for the title of this EventContainer object
     * @param title - new title for this EventContainer object
     */
    public void setTitle(String title) {
        this.title = title;
    }
}
