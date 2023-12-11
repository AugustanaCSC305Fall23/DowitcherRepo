package edu.augustana.ui;

import edu.augustana.*;
import edu.augustana.datastructure.Card;
import edu.augustana.datastructure.CardLibrary;
import edu.augustana.datastructure.EventContainer;
import edu.augustana.datastructure.LessonPlan;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.*;

/**
 * This class represents a LessonPlanUI object, which is a UI representation of a LessonPlan object
 */
public class LessonPlanUI extends ScrollPane {

    private static Map<String, LessonPlanUI> lessonPlanMap = new HashMap<>();
    private LessonPlan lessonPlan;

    private VBox lessonPlanVBox;

    private Button addEventButton;

    private ComboBox<String> eventComboBox;

    private List<EventContainerUI> eventContainerUIList;

    private Label lessonPlanTitleLabel;

    private LessonTab inLessonTab;

    private HBox titleAndDeleteHBox;

    /**
     * Constructor for LessonPlanUI object
     * @param lessonPlan - LessonPlan object associated with this LessonPlanUI object
     */
    public LessonPlanUI(LessonPlan lessonPlan) {
        titleAndDeleteHBox = new HBox();
        Button deleteLessonPlanButton = new Button("Delete");
        deleteLessonPlanButtonFunctionality(deleteLessonPlanButton);
        this.lessonPlan = lessonPlan;
        eventContainerUIList = new ArrayList<>();
        eventComboBox = new ComboBox<>();
        eventComboBox.setPromptText("Select Event");
        addEventsToComboBox();
        addEventButton = new Button("Add Event");
        giveEventButtonFunctionality();
        this.setId(String.format("%s", lessonPlan.getTitle()));
        this.lessonPlanVBox = new VBox();
        lessonPlanTitleLabel = new Label(lessonPlan.getTitle());
        renameLessonPlanOnDoubleClick();
        lessonPlanTitleLabel.setStyle("-fx-font-size: 24;" + "-fx-font-weight: bold;");
        titleAndDeleteHBox.getChildren().addAll(lessonPlanTitleLabel, deleteLessonPlanButton);
        lessonPlanVBox.getChildren().addAll(titleAndDeleteHBox, eventComboBox, addEventButton);
        this.setContent(lessonPlanVBox);
        lessonPlanMap.put(lessonPlan.getTitle(), this);
        this.fitToWidthProperty().setValue(true);
    }

    /**
     * Getter method for LessonPlan object associated with this LessonPlanUI object
     * @return - LessonPlan object associated with this LessonPlanUI object
     */
    public LessonPlan getLessonPlan() {
        return lessonPlan;
    }

    /**
     * Getter method for the title of LessonPlan object associated with this LessonPlanUI object
     * @return - title of LessonPlan object associated with this LessonPlanUI object
     */
    public String getTitle() {
        return lessonPlan.getTitle();
    }

    /**
     * Adds an EventContainerUI object to this LessonPlanUI object
     * @param eventContainerUI - EventContainerUI object to be added to this LessonPlanUI object
     */
    public void addEventContainer(EventContainerUI eventContainerUI) {
        if (!lessonPlan.getEventList().contains(eventContainerUI.getEvent())) {
            lessonPlan.addEventContainer(eventContainerUI.getEventContainer());
            eventContainerUIList.add(eventContainerUI);
            lessonPlanVBox.getChildren().add(eventContainerUI);
        }
    }

    /**
     * Removes an EventContainerUI object from this LessonPlanUI object
     * @param eventContainerUI - EventContainerUI object to be removed from this LessonPlanUI object
     */
    public void removeEventContainer(EventContainerUI eventContainerUI) {
        eventContainerUIList.remove(eventContainerUI);
        lessonPlanVBox.getChildren().remove(eventContainerUI);
    }

    /**
     * Getter method for the list of EventContainerUI objects associated with this LessonPlanUI object
     * @return - list of EventContainerUI objects associated with this LessonPlanUI object
     */
    public List<EventContainerUI> getEventContainerUIList() {
        return eventContainerUIList;
    }

    /**
     * Getter method for the VBox associated with this LessonPlanUI object
     * @return - VBox associated with this LessonPlanUI object
     */
    public VBox getLessonPlanVBox() {
        return lessonPlanVBox;
    }

    /**
     * Adds all events in the CardLibrary to the eventComboBox
     */
    public void addEventsToComboBox() {
        Set<String> eventSet = new TreeSet<String>();
        for (Object cardKey : CardLibrary.cardMap.keySet()) {
            Card card = (Card) CardLibrary.cardMap.get(cardKey);
            String event = card.getEvent();
            eventSet.add(event);
        }
        for (String event : eventSet) {
            eventComboBox.getItems().add(event);
            System.out.println(String.format("Added %s to eventComboBox", event));
        }
    }

    /**
     * Gives the addEventButton functionality
     */
    public void giveEventButtonFunctionality() {
        addEventButton.setOnAction(e -> {
            String selectedEvent = eventComboBox.getValue();
            if (selectedEvent != null) {
                EventContainerUI eventContainerUI = new EventContainerUI(new EventContainer(selectedEvent));
                addEventContainer(eventContainerUI);
            }
        });
    }

    /**
     * Renames the LessonPlanUI object and its associated LessonPlan object and LessonTab object
     * @return - the new title of the LessonPlanUI object
     */
    public String renameLessonPlan() {
        lessonPlanVBox.getChildren().remove(0);
        HBox renameHBox = new HBox();
        TextField renameTextField = new TextField();
        Button renameButton = new Button("Rename");
        renameHBox.getChildren().addAll(renameTextField, renameButton);
        lessonPlanVBox.getChildren().add(0, renameHBox);
        renameButton.setOnAction(e -> {
            String newTitle = renameTextField.getText();
            if (newTitle != null && !newTitle.equals("") && !lessonPlanMap.containsKey(newTitle)) {
                lessonPlanMap.remove(lessonPlan.getTitle());
                lessonPlanMap.put(newTitle, this);
                lessonPlan.renamePlan(newTitle);
                inLessonTab.setTitle(newTitle);
                this.setId(newTitle);
                lessonPlanTitleLabel.setText(newTitle);


            }
            lessonPlanVBox.getChildren().remove(0);
            lessonPlanVBox.getChildren().add(0, titleAndDeleteHBox);
//            System.out.println("NEW TITLES:");
//            for (String lPKey : lessonPlanMap.keySet()) {
//                System.out.println(lessonPlanMap.get(lPKey).getTitle());
//            }
//            System.out.println("");

        });
        return renameTextField.getText();
    }

    /**
     * Gives the lessonPlanTitleLabel functionality to rename the LessonPlanUI object and its associated LessonPlan object and LessonTab object
     */
    private void renameLessonPlanOnDoubleClick() {
        lessonPlanTitleLabel.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                renameLessonPlan();
            }
        });
    }

    /**
     * Draws an EventContainerUI object in this LessonPlanUI object
     * @param eventContainerUI - EventContainerUI object to be drawn in this LessonPlanUI object
     */
    public void drawEventContainerinLessonPlanUI(EventContainerUI eventContainerUI) {
        eventContainerUIList.add(eventContainerUI);
        lessonPlanVBox.getChildren().add(eventContainerUI);

    }

    /**
     * Getter method for the LessonPlanUIMap
     * @return - LessonPlanUIMap
     */
    public static Map getLessonPlanUIMap() {
        return lessonPlanMap;
    }

    /**
     * Setter method for the LessonTab object this LessonPlanUI object is in
     * @param lessonTab - LessonTab object this LessonPlanUI object is in
     */
    public void setInLessonTab(LessonTab lessonTab) {
        this.inLessonTab = lessonTab;
    }

    /**
     * Gives the deleteLessonPlanButton functionality
     * @param deleteLessonPlanButton - deleteLessonPlanButton
     */
    private void deleteLessonPlanButtonFunctionality(Button deleteLessonPlanButton) {
        deleteLessonPlanButton.setOnAction(e -> {
            deleteLessonPlan();
        });
    }

    /**
     * Deletes this LessonPlanUI object and its associated LessonPlan object and LessonTab object
     */
    private void deleteLessonPlan() {
        lessonPlanMap.remove(lessonPlan.getTitle());
        inLessonTab.getTabPane().getTabs().remove(inLessonTab);
        LessonTab.getLessonTabMap().remove(inLessonTab.getTitle());
        LessonTab.getLessonTabList().remove(inLessonTab);
        GymnasticsPlannerApp.getCurrentCourse().getLessonPlanList().remove(lessonPlan);
    }
}
