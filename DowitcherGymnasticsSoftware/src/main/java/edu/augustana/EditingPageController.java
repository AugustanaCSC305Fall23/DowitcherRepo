package edu.augustana;

import java.io.File;
import java.util.*;
import java.io.IOException;
import com.google.gson.Gson;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;

import javafx.scene.image.Image;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Window;



public class EditingPageController {


    @FXML
    private ComboBox eventChoiceButton;

    private Button addEventButton;

    @FXML
    private Button applyFilterButton;

    @FXML
    private ListView<HBox> cardImageView;

    @FXML
    private Button clearFilterButton;

    @FXML
    private Button equipmentFilterButton;
    @FXML
    private Button printButton;
    @FXML
    private Button eventFilterButton;

    @FXML
    private TextField filterSearchField;

    @FXML
    private Button genderFilterButton;

    @FXML
    private Label lessonPlanTitle;

    @FXML
    private VBox lessonPlanVBox;

    @FXML
    private Button levelFilterButton;

    @FXML
    private ScrollPane planeScrollPane;

    @FXML
    private AnchorPane scrollAnchorPane;

    @FXML
    private Button subCategoryFilterButton;

    @FXML
    private Button tempButton;

    @FXML
    private MenuBar menuBar;
    @FXML
    private Menu fileMenu;

    private SearchFunction searchFunction;

    @FXML
    public void initialize() {
        searchFunction = new SearchFunction(CardLibrary.cardList);
//      cardImageView = new ListView<>();
        filterSearchField.setOnKeyPressed(evt -> {
            if (evt.getCode() == KeyCode.ENTER) {
                cardSearchFunction();
            }
        });
        System.out.println(App.currentLessonPlan.toString());
        MenuItem homeItem = new MenuItem("Home");
        MenuItem printItem = new MenuItem("Print");

        homeItem.setOnAction(evt -> {
            try {
                switchToHome();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        printItem.setOnAction(evt -> {App.switchToPrintPage();}); //Printers.printLessonPlan(planeScrollPane);});
        fileMenu.getItems().addAll(homeItem, printItem);

        loadCards();
        addEventChoices();
        lessonPlanTitle.setText(App.currentLessonPlan.getTitle());
        cardImageView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                System.out.println("Double clicked");
                addByDoubleClick();
            }
        });
    }
    @FXML
    public static void switchToEditingPage() throws IOException {
        App.setRoot("editingPage.fxml");
    }
    @FXML
    private void switchToHome() throws IOException{
        App.setRoot("LandingPage");
    }


    @FXML
    private void loadCards() {
        for (int cardNum = 0; cardNum < CardLibrary.cardList.size(); cardNum++) {
            HBox thumbnail = CardGraphic.generateCardThumbnail(CardLibrary.cardList.get(cardNum));
            cardImageView.getItems().add(thumbnail);
        }
    }

    @FXML
    private void cardSearchFunction() {
        String query = filterSearchField.getText();
        List<Card> searchResults = searchFunction.performSearch(query);
        System.out.println(searchResults);
        updateCardImageView(searchResults);
//        System.out.println("ENTER was pressed");
//        System.out.println(searchResults.toString());
    }

    @FXML
    private void updateCardImageView(List<Card> searchResults) {
        cardImageView.getItems().clear();

        for (Card card : searchResults) {
            System.out.println("Printing New Card");
            System.out.println(card.getCode());
            System.out.println(card.getTitle());
            HBox thumbnail = CardGraphic.generateCardThumbnail(card);
            cardImageView.getItems().add(thumbnail);
        }

        System.out.println(cardImageView);

    }

    private void addCardToEvent() {

    }

    @FXML
    private void addEventByButton() {
        addEvent(eventChoiceButton.getValue().toString());
    }
    @FXML
    private void addEvent(String event) {
        EventContainer container = new EventContainer(event);
        App.currentLessonPlan.addEventContainer(container);
        lessonPlanVBox.getChildren().add(3, CardGraphic.generateEventContainerGraphic(container));
    }

    private void addEventChoices() {
        eventChoiceButton.getItems().addAll("Beam", "Floor", "Horizontal Bar",
                "Parallel Bars","Pommel Horse","Still Rings", "Tramp", "Uneven Bars","Vault");
    }

    private void addByDoubleClick() {
        System.out.println("Double clicked");
        HBox selectedCard = cardImageView.getSelectionModel().getSelectedItem();
        String cardCode = selectedCard.getId().substring(0, selectedCard.getId().indexOf("-"));
        String cardEvent = selectedCard.getId().substring(selectedCard.getId().indexOf("-") + 1);
        //System.out.println(cardEvent + "+" + cardCode);
        //System.out.println(selectedCard.getId());
        boolean containerExists = false;
        boolean containerFull = false;
        System.out.println(CardLibrary.cardList.get(cardImageView.getItems().indexOf(selectedCard)));
        for (int cardNums = 0; cardNums < cardImageView.getItems().size(); cardNums++) {
            //System.out.println(cardImageView.getItems().get(cardNums).toString());
            //System.out.println("   - " + cardImageView.getItems().indexOf(selectedCard) + " - " + CardLibrary.cardList.get(cardImageView.getItems().indexOf(selectedCard)));
        }
        for (Object key : App.currentLessonPlan.getEventMap().keySet()) {
            EventContainer container = (EventContainer) App.currentLessonPlan.getEventMap().get(key);
            if (container.getCards().size() >= 8) {
                containerFull = true;
            }
            if (container.getType().equalsIgnoreCase(cardEvent) && !containerFull) {
                containerExists = true;
                container.addCard(CardLibrary.cardList.get(cardImageView.getItems().indexOf(selectedCard)).getCode());
                //CardGraphic.addCardToEventContainerGraphic(CardGraphic.getEventContainer(container.getType()), CardLibrary.cardList.get(cardImageView.getItems().indexOf(selectedCard)));
                CardGraphic.addCardToEventContainerGraphic(CardGraphic.getEventContainer(container.getType()), (Card) CardLibrary.cardMap.get(cardCode));
            }
        }
        if (!containerExists && !containerFull) {
            addEvent(cardEvent);
            EventContainer newContainer = (EventContainer) App.currentLessonPlan.getEventMap().get(cardEvent);
            newContainer.addCard(CardLibrary.cardList.get(cardImageView.getItems().indexOf(selectedCard)).getCode());
            //CardGraphic.addCardToEventContainerGraphic(CardGraphic.getEventContainer(newContainer.getType()), CardLibrary.cardList.get(cardImageView.getItems().indexOf(selectedCard)));
            CardGraphic.addCardToEventContainerGraphic(CardGraphic.getEventContainer(newContainer.getType()), (Card) CardLibrary.cardMap.get(cardCode));
        }
        App.currentLessonPlan.printTree();
    }

    @FXML
    private void openLessonPlan(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Lesson Plan File");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Lesson Plans (*.gymlessonplan", "*.gymlessonplan");
        fileChooser.getExtensionFilters().add(filter);
        Window mainWindow = cardImageView.getScene().getWindow();
        File chosenFile = fileChooser.showOpenDialog(mainWindow);
        if (chosenFile != null) {
            try {
                App.loadCurrentLessonPlanFromFile(chosenFile);
                for (int i = 3; i < lessonPlanVBox.getChildren().size(); i++) {
                    lessonPlanVBox.getChildren().remove(i);
                }
                LessonPlan loadedPlan = App.getCurrentLessonPlan();
                lessonPlanTitle.setText(loadedPlan.getTitle());
                Map map = App.getCurrentLessonPlan().getEventMap();
                System.out.println(map.toString());
                for (Object key : map.keySet()) {
                    EventContainer eventContainer = new Gson().fromJson(new Gson().toJson(map.get(key)), EventContainer.class);
                    VBox vbox = CardGraphic.generateEventContainerGraphic(eventContainer);
                    for (int cardIndex = 0; cardIndex < eventContainer.getCards().size(); cardIndex++) {
                        Card card = (Card) CardLibrary.cardMap.get(eventContainer.getCards().get(cardIndex));
                        CardGraphic.addCardToEventContainerGraphic(vbox, card);
                    }
                    lessonPlanVBox.getChildren().add(vbox);
                }
            } catch (IOException ex) {
                new Alert(Alert.AlertType.ERROR, "Error loading lesson plan file: " + chosenFile).show();
            }
        }
    }
    @FXML
    private void save(ActionEvent event) {
        if (App.getCurrentLessonPlanFile() == null) {
            saveAs(event);
        } else {
            saveCurrentLessonPlanToFile(App.getCurrentLessonPlanFile());
        }
     }

     @FXML
    private void saveAs(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Lesson Plan");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Lesson Plans (*.gymlessonplan", "*.gymlessonplan");
        fileChooser.getExtensionFilters().add(filter);
        Window mainWindow = cardImageView.getScene().getWindow();
        File chosenFile = fileChooser.showSaveDialog(mainWindow);
        saveCurrentLessonPlanToFile(chosenFile);
    }
    @FXML
    private void saveCurrentLessonPlanToFile(File chosenFile) {
        if (chosenFile != null) {
            try {
                App.saveCurrentLessonPlanToFile(chosenFile);
            } catch (IOException ex) {
                new Alert(Alert.AlertType.ERROR, "Error saving lesson plan file: " + chosenFile).show();
            }
        }
    }


    @FXML
    private void changeLessonPlanName() {
        String name = renameLabel(lessonPlanTitle, lessonPlanVBox);
        App.currentLessonPlan.renamePlan(name);
    }

    @FXML
    private void renameEventContainerName() {
//        renameLabel();
    }
    @FXML
    private String renameLabel(Label label, VBox vbox) {
        vbox.getChildren().remove(0);
        TextField renameField = new TextField();
        HBox renameBox = new HBox();
        renameBox.alignmentProperty().setValue(Pos.CENTER);
        renameField.setText(label.getText());
        Button renameButton = new Button("Rename");
        renameBox.getChildren().addAll(renameField, renameButton);
        renameBox.setSpacing(10);
        vbox.getChildren().add(0, renameBox);
        String newLabelName = "";
        renameButton.setOnAction(event -> {
            label.setText(renameField.getText());
            vbox.getChildren().remove(0);
            vbox.getChildren().add(0, label);
        });
        renameField.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                label.setText(renameField.getText());
                vbox.getChildren().remove(0);
                vbox.getChildren().add(0, label);
            }
        });
        if (renameField.getText().equalsIgnoreCase("")) {
            label.setText("Untitled");
            renameField.setText("Untitled");
        }
        return renameField.getText();
    }
}

