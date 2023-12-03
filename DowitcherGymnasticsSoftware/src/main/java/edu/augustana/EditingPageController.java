package edu.augustana;

import java.io.File;
import java.util.*;
import java.io.IOException;
import com.google.gson.Gson;

import edu.augustana.ui.CardUI;
import edu.augustana.ui.EventContainerUI;
import edu.augustana.ui.LessonPlanUI;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
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
    private TilePane cardImageView;

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
    private VBox lessonPlanVBox;

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
    @FXML
    private Button expandButton;
    @FXML
    private VBox filterSearchCardVBox;


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
        if (lessonPlanTabs.getTabs().size() == 1) {
            createNewLessonPlanTab();
        }
        if (App.currentLessonPlanFile != null) {
            openLessonPlanWithFile(App.currentLessonPlanFile);
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
            cardImageView.getChildren().add(cardUI);
        }
    }

    //this method will be used to expand the search bar scroll pane to show two columns of cards instead of one
    //when the user clicks on the expand button
    @FXML
    private void expandFilterSearchCardVBox() {
        // Calculate the new width for the filterSearchCardVBox
        double originalWidth = filterSearchCardVBox.getPrefWidth();
        double newWidth = originalWidth * 2;

        // Set the new width for the filterSearchCardVBox
        filterSearchCardVBox.setPrefWidth(newWidth);

        // Set constraints on the filterSearchCardVBox within its parent container
        VBox.setVgrow(filterSearchCardVBox, Priority.ALWAYS);

        // Calculate the new width for each column
        double columnWidth = newWidth / 2;

        // Set the cell factory to display items in two columns
        //cardImageView.setCellFactory(listView -> new ListCell<VBox>() {
            //@Override
//            protected void updateItem(VBox item, boolean empty) {
//                super.updateItem(item, empty);
//
//                if (empty || item == null) {
//                    setText(null);
//                    setGraphic(null);
//                } else {
//                    // Set the preferred and max width for each card
//                    item.setPrefWidth(columnWidth);
//                    item.setMaxWidth(columnWidth);
//
//                    // Set the graphic (card) for the cell
//                    setGraphic(item);
//                }
//            }
//        });
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
        cardImageView.getChildren().clear();

        for (Card card : searchResults) {
            VBox thumbnail = new CardUI(card);
            cardImageView.getChildren().add(thumbnail);
        }
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
        fileChooser.setTitle("Open Lesson Plan File");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Lesson Plans (*.gymlessonplan", "*.gymlessonplan");
        fileChooser.getExtensionFilters().add(filter);
        Window mainWindow = cardImageView.getScene().getWindow();
        File chosenFile = fileChooser.showOpenDialog(mainWindow);
        openLessonPlanWithFile(chosenFile);
    }

    @FXML
    private void openLessonPlanWithFile(File file) {
        if (file != null) {
            try {
                App.loadCurrentLessonPlanFromFile(file);
                for (int i = 3; i < lessonPlanVBox.getChildren().size(); i++) {
                    lessonPlanVBox.getChildren().remove(i);
                }
                LessonPlan loadedPlan = App.getCurrentLessonPlan();
                //lessonPlanTitle.setText(loadedPlan.getTitle());// FIX ME PLEASE
                Map map = App.getCurrentLessonPlan().getEventMap();
                System.out.println(map.toString());
                for (Object key : map.keySet()) {
                    EventContainer eventContainer = new Gson().fromJson(new Gson().toJson(map.get(key)), EventContainer.class);
                    EventContainerUI eventContainerUI = new EventContainerUI(eventContainer);
                    for (int cardIndex = 0; cardIndex < eventContainer.getCards().size(); cardIndex++) {
                        Card card = (Card) CardLibrary.cardMap.get(eventContainer.getCards().get(cardIndex));
                        eventContainerUI.addCard(new CardUI(card));
                    }
                    lessonPlanVBox.getChildren().add(eventContainerUI);
                }
            } catch (IOException ex) {
                new Alert(Alert.AlertType.ERROR, "Error loading lesson plan file: " + file).show();
            }
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
        fileChooser.setInitialDirectory(new File("src/main/resources/Saved Lesson Plans"));
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

