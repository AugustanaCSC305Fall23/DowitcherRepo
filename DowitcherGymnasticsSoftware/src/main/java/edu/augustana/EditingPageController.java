package edu.augustana;

import java.io.File;
import java.util.*;
import java.io.IOException;

import com.google.gson.Gson;
import edu.augustana.ui.CardUI;
import edu.augustana.ui.EventContainerUI;
import edu.augustana.ui.LessonPlanUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Window;



public class EditingPageController {

    @FXML
    private CheckBox beamCheckbox;

    @FXML
    private CheckBox floorCheckbox;
    @FXML
    private CheckBox horizontalBarCheckBox;
    @FXML
    private CheckBox parallelBarsCheckBox;
    @FXML
    private CheckBox pommelHorseCheckBox;
    @FXML
    private CheckBox stillRingsCheckBox;
    @FXML
    private CheckBox trampCheckBox;
    @FXML
    private CheckBox unevenBarsCheckBox;
    @FXML
    private CheckBox vaultCheckBox;

    @FXML
    private CheckBox levelBCheckBox;
    @FXML
    private CheckBox levelABCheckBox;
    @FXML
    private CheckBox levelICheckBox;
    @FXML
    private CheckBox levelACheckBox;
    @FXML
    private CheckBox levelAllCheckBox;
    @FXML
    private CheckBox maleCheckBox;
    @FXML
    private CheckBox femaleCheckBox;

    @FXML
    private TabPane lessonPlanTabs;

    @FXML
    private Tab newTabButton;

    @FXML
    private Tab currentTab;



    @FXML
    private ComboBox eventChoiceButton;

    private Button addEventButton;

    @FXML
    private Button applyFilterButton;

    @FXML
    private ListView<VBox> cardImageView;

    @FXML
    private Button clearFilterButton;

    @FXML
    private TitledPane equipmentFilterTitledPane;
    @FXML
    private Button printButton;
    @FXML
    private TitledPane eventFilterTitledPane;

    @FXML
    private TextField filterSearchField;

    @FXML
    private TitledPane genderFilterTitledPane;




    @FXML
    private TitledPane levelFilterTitledPane;

    @FXML
    private ScrollPane planeScrollPane;

    @FXML
    private AnchorPane scrollAnchorPane;

    @FXML
    private Button subCategoryFilterButton;

    @FXML
    private MenuBar menuBar;
    @FXML
    private Menu fileMenu;

    private SearchFunction searchFunction;


    private FilterSearch filterSearch;

    private boolean isLessonPlanSaved = false;


    @FXML
    public void initialize() {
        searchFunction = new SearchFunction(App.cardLibrary);
        filterSearchField.setOnKeyPressed(evt -> {
            if (evt.getCode() == KeyCode.ENTER) {
                cardSearchFunction();
            }
        });
        //System.out.println(App.currentLessonPlan.toString());
        MenuItem homeItem = new MenuItem("Home");
        MenuItem printItem = new MenuItem("Print");


        homeItem.setOnAction(evt -> {
            try {
                switchToHome();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        printItem.setOnAction(evt -> {
            if (isLessonPlanSaved) {
                App.switchToPrintPage();
            } else {
                showLessonPlanNotSavedWarning();
            }
        }); //Printers.printLessonPlan(planeScrollPane);});
        fileMenu.getItems().addAll(homeItem, printItem);

        loadCards();
        cardImageView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                System.out.println("Double clicked");
//                addByDoubleClick();
            }
        });
        if (App.currentCourseFile != null) {
            openCourseWithFile(App.currentCourseFile);
        }
        if (lessonPlanTabs.getTabs().size() == 1) {
            createNewLessonPlanTab();
        }

        //////////////////////////////////////////////////////////// ** FILTER FUNCTIONALITY
        filterSearch = new FilterSearch(List.of(
                beamCheckbox,
                floorCheckbox,
                horizontalBarCheckBox,
                parallelBarsCheckBox,
                pommelHorseCheckBox,
                stillRingsCheckBox,
                trampCheckBox,
                unevenBarsCheckBox,
                vaultCheckBox,
                levelBCheckBox,
                levelABCheckBox,
                levelICheckBox,
                levelACheckBox,
                levelAllCheckBox,
                maleCheckBox,
                femaleCheckBox
        ), CardLibrary.cardList, cardImageView);

      //  filterSearchField.setOnKeyPressed(this::handleSearchKeyPress);

        ///////////////////////////////////////////////////////////
        newTabButton.setOnSelectionChanged(event -> {
            createNewLessonPlanTab();
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
        for (Object cardKey : CardLibrary.cardMap.keySet()) {
            CardUI cardUI = new CardUI((Card) CardLibrary.cardMap.get(cardKey));
            cardImageView.getItems().add(cardUI);
        }
    }

    private void expandCardImageView() {

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
        // only displays cards of the new search
        for (Card card : searchResults) {
            //System.out.println("Printing New Card");
            System.out.println(card.getCode());
            System.out.println(card.getTitle());
            VBox thumbnail = new CardUI(card);
            cardImageView.getItems().add(thumbnail);
        }

        System.out.println(cardImageView);

    }

    ////////////////////////////////////////////////////////////////// ** FILTER FUNCTIONALITY


    @FXML
    private void applyFilter() {
        filterSearch.applyFilter();
    }

    @FXML
    private void clearFilter() {
        filterSearch.clearFilter();
    }

    private void handleSearchKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            cardSearchFunction();
        }
    }


    ////////////////////////////////////////////////////////////////////

    @FXML
    private void openLessonPlan() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Course File");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Courses (*.gymcourse", "*.gymcourse");
        fileChooser.getExtensionFilters().add(filter);
        Window mainWindow = cardImageView.getScene().getWindow();
        File chosenFile = fileChooser.showOpenDialog(mainWindow);
        openCourseWithFile(chosenFile);
    }

    @FXML
    private void openCourseWithFile(File file) {
        if (file != null) {
            //                App.loadCurrentCourseFromFile(file);
            for (LessonPlan lessonPlan : App.getCurrentCourse().getLessonPlanList()) {
                App.currentLessonPlan = lessonPlan;
                App.currentLessonPlanUI = new LessonPlanUI(lessonPlan);
                Tab lessonPlanTab = new Tab(lessonPlan.getTitle());
                lessonPlanTab.setContent(App.currentLessonPlanUI);
                lessonPlanTab.setOnSelectionChanged(event -> {
                    setCurrentLessonPlanTab();
                });
                lessonPlanTabs.getTabs().add(lessonPlanTabs.getTabs().size()-1, lessonPlanTab);
                for (Object eventContainerKey : lessonPlan.getEventMap().keySet()) {
                    EventContainer eventContainer = new Gson().fromJson(new Gson().toJson(lessonPlan.getEventMap().get(eventContainerKey)), EventContainer.class);
                    EventContainerUI eventContainerUI = new EventContainerUI(eventContainer);
                    App.currentLessonPlanUI.drawEventContainerinLessonPlanUI(eventContainerUI);
                        for (int cardIndex = eventContainer.getCards().size() -1; cardIndex >= 0; cardIndex--) {
                            Card card = (Card) CardLibrary.cardMap.get(eventContainer.getCards().get(cardIndex));
                            eventContainer.removeCard(eventContainer.getCards().get(cardIndex));
                            CardUI cardUI = new CardUI(card);
                            eventContainerUI.addCard(cardUI);
                        }
                }

            }
            lessonPlanTabs.getSelectionModel().select(0);
        }
    }
    @FXML
    private void showLessonPlanNotSavedWarning() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText("You must save the lesson plan before attempting to print.");
        alert.showAndWait();
    }
    @FXML
    private void save(ActionEvent event) {
        isLessonPlanSaved = true;
        if (App.getCurrentCourseFile() == null) {
            saveAs(event);
        } else {
            saveCurrentCourseToFile(App.getCurrentCourseFile());
        }
     }

     @FXML
    private void saveAs(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Course");
        fileChooser.setInitialDirectory(new File("src/main/resources/Saved Courses"));
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Courses (*.gymcourse", "*.gymcourse");
        fileChooser.getExtensionFilters().add(filter);
        Window mainWindow = cardImageView.getScene().getWindow();
        File chosenFile = fileChooser.showSaveDialog(mainWindow);
        saveCurrentCourseToFile(chosenFile);
    }
    @FXML
    private void saveCurrentCourseToFile(File chosenFile) {
        if (chosenFile != null) {
            try {
                App.saveCurrentCourseToFile(chosenFile);
            } catch (IOException ex) {
                new Alert(Alert.AlertType.ERROR, "Error saving course file: " + chosenFile).show();
            }
        }
    }


    @FXML
    private void setCurrentLessonPlanTab() {
        currentTab = lessonPlanTabs.getSelectionModel().getSelectedItem();
        App.currentLessonPlan = (LessonPlan) App.currentCourse.getLessonPlanMap().get(currentTab.getText());

        System.out.println(String.format("Current Tab = %s", currentTab.getText()));
        System.out.println(App.currentLessonPlan.toString());
    }

    private void createNewLessonPlanTab() {
        if (newTabButton.isSelected()) {
            String lessonPlanName = "New Lesson Plan";
            while (App.currentCourse.getLessonPlanMap().containsKey(lessonPlanName)) {
                lessonPlanName = lessonPlanName + "1";
            }
            LessonPlan newLessonPlan = new LessonPlan(lessonPlanName);
            App.currentCourse.addLessonPlan(newLessonPlan);
            LessonPlanUI newLessonPlanUI = new LessonPlanUI(newLessonPlan);
            App.currentLessonPlanUI = newLessonPlanUI;
            Tab newTab = new Tab(lessonPlanName);
            newTab.setOnSelectionChanged(event -> {
                setCurrentLessonPlanTab();
            });
            newTab.setContent(newLessonPlanUI);
            lessonPlanTabs.getTabs().add(lessonPlanTabs.getTabs().size() - 1, newTab);
            lessonPlanTabs.getSelectionModel().select(newTab);
        }
    }


}

