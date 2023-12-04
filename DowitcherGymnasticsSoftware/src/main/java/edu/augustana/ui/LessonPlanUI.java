package edu.augustana.ui;

import edu.augustana.Card;
import edu.augustana.CardLibrary;
import edu.augustana.EventContainer;
import edu.augustana.LessonPlan;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class LessonPlanUI extends ScrollPane {
    private LessonPlan lessonPlan;

    private VBox lessonPlanVBox;

    private Button addEventButton;

    private ComboBox<String> eventComboBox;

    private List<EventContainerUI> eventContainerUIList;

    private Label lessonPlanTitleLabel;

    public LessonPlanUI(LessonPlan lessonPlan) {
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
        lessonPlanVBox.getChildren().addAll(lessonPlanTitleLabel, eventComboBox, addEventButton);
        this.setContent(lessonPlanVBox);
    }

    public LessonPlan getLessonPlan() {
        return lessonPlan;
    }

    public String getTitle() {
        return lessonPlan.getTitle();
    }

    public void addEventContainer(EventContainerUI eventContainerUI) {
        if (!lessonPlan.getEventMap().containsKey(eventContainerUI.getEvent())) {
            lessonPlan.addEventContainer(eventContainerUI.getEventContainer());
            eventContainerUIList.add(eventContainerUI);
            lessonPlanVBox.getChildren().add(eventContainerUI);
        }
    }

    public void removeEventContainer(EventContainerUI eventContainerUI) {
        eventContainerUIList.remove(eventContainerUI);
        lessonPlanVBox.getChildren().remove(eventContainerUI);
    }

    public List<EventContainerUI> getEventContainerUIList() {
        return eventContainerUIList;
    }

    public VBox getLessonPlanVBox() {
        return lessonPlanVBox;
    }

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

    public void giveEventButtonFunctionality() {
        addEventButton.setOnAction(e -> {
            String selectedEvent = eventComboBox.getValue();
            if (selectedEvent != null) {
                EventContainerUI eventContainerUI = new EventContainerUI(new EventContainer(selectedEvent));
                addEventContainer(eventContainerUI);
            }
        });
    }

    public String renameLessonPlan() {
        lessonPlanVBox.getChildren().remove(0);
        HBox renameHBox = new HBox();
        TextField renameTextField = new TextField();
        Button renameButton = new Button("Rename");
        renameHBox.getChildren().addAll(renameTextField, renameButton);
        lessonPlanVBox.getChildren().add(0, renameHBox);
        renameButton.setOnAction(e -> {
            String newTitle = renameTextField.getText();
            if (newTitle != null) {
                lessonPlan.renamePlan(newTitle);
                lessonPlanTitleLabel.setText(newTitle);

            }
            lessonPlanVBox.getChildren().remove(0);
            lessonPlanVBox.getChildren().add(0, lessonPlanTitleLabel);

        });
        return renameTextField.getText();
    }

    private void renameLessonPlanOnDoubleClick() {
        lessonPlanTitleLabel.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                renameLessonPlan();
            }
        });
    }

    public void drawEventContainerinLessonPlanUI(EventContainerUI eventContainerUI) {
        eventContainerUIList.add(eventContainerUI);
        lessonPlanVBox.getChildren().add(eventContainerUI);

    }
}
