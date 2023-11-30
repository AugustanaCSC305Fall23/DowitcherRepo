package edu.augustana;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * This class is used to create a card object that contains all the information about a Gymnastic card.
 */
public class Card {
    private String code;
    private String event;
    private String category;
    private String title;
    private String packFolder;
    private String image;
    private String gender;
    private String modelSex;
    private String[] level;
    private String[] equipment;
    private String[] keywords;
    public Card(String code, String event, String category, String title, String packFolder, String image, String gender, String modelSex, String[] level, String[] equipment, String[] keywords) {
        this.code = code;
        this.event = event;
        this.category = category;
        this.title = title;
        this.packFolder = packFolder;
        this.image = image;
        this.gender = gender;
        this.modelSex = modelSex;
        this.level = level;
        this.equipment = equipment;
        this.keywords = keywords;
    }

    public Card(){
        this("", "", "", "", "", "", "", "", null, null, null);
        this.image = "Blank Card.png";
        this.packFolder = "Misc Images";
    }

    public String getCode() {
        return code;
    }
    public String getEvent() {
        return event;
    }
    public String getCategory() {
        return category;
    }
    public String getTitle() {
        return title;
    }
    public String getPath() { //Returns the path to the image
        return String.format("%s/%s", packFolder, image);
    }
    public String getGender() {
        return gender;
    }
    public String getModelSex() {
        return modelSex;
    }
    public String[] getLevel() {
        return level;
    }
    public String[] getEquipment() {
        return equipment;
    }
    public String[] getKeywords() {
        return keywords;
    }

    public String toString() {
        return String.format("%s : %s", code, title);
    }
}
