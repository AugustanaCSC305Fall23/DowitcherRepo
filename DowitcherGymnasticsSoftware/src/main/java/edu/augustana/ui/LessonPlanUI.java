package edu.augustana.ui;

import edu.augustana.*;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.*;

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

    public LessonPlan getLessonPlan() {
        return lessonPlan;
    }

    public String getTitle() {
        return lessonPlan.getTitle();
    }

    public void addEventContainer(EventContainerUI eventContainerUI) {
        if (!lessonPlan.getEventList().contains(eventContainerUI.getEvent())) {
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

    public static Map getLessonPlanUIMap() {
        return lessonPlanMap;
    }

    public void setInLessonTab(LessonTab lessonTab) {
        this.inLessonTab = lessonTab;
    }

    private void deleteLessonPlanButtonFunctionality(Button deleteLessonPlanButton) {
        deleteLessonPlanButton.setOnAction(e -> {
            deleteLessonPlan();
        });
    }

    private void deleteLessonPlan() {
        lessonPlanMap.remove(lessonPlan.getTitle());
        inLessonTab.getTabPane().getTabs().remove(inLessonTab);
        LessonTab.getLessonTabMap().remove(inLessonTab.getTitle());
        LessonTab.getLessonTabList().remove(inLessonTab);
        App.getCurrentCourse().getLessonPlanList().remove(lessonPlan);
    }
}
