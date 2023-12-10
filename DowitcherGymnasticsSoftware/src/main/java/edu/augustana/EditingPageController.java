package edu.augustana;

import java.io.File;
import java.util.*;
import java.io.IOException;

import com.google.gson.Gson;
import edu.augustana.ui.CardUI;
import edu.augustana.ui.EventContainerUI;
import edu.augustana.ui.LessonPlanUI;
import edu.augustana.ui.LessonTab;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.control.*;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import static java.lang.Character.getNumericValue;


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
    private LessonTab currentTab;



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

    @FXML
    private Label courseLabel;

    private boolean isLessonPlanSaved = false;


    @FXML
    public void initialize() {
        /////////////////////////////////////////////////////////// ** SEARCH TEXT FUNCTIONALITY
        searchFunction = new SearchFunction();
        filterSearchField.setOnKeyPressed(evt -> {
            if (evt.getCode() == KeyCode.ENTER) {
                cardSearchFunction();
            }
        });

        ///////////////////////////////////////////////////////////// ** NEW LIVE SEARCH ***
        searchFunction = new SearchFunction();
        searchFunction.initializeSearchField(filterSearchField, cardImageView);

        filterSearch = new FilterSearch(List.of(
                // ... (Existing checkboxes)
        ), CardLibrary.cardList, cardImageView);
        filterSearchField.setOnKeyPressed(evt -> {
            if (evt.getCode() == KeyCode.ENTER) {
                performTextSearch();
            }
        });
        filterSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            performTextSearch();
        });


        //System.out.println(App.currentLessonPlan.toString());
        /////////////////////////////////////////////////////////////

        MenuItem homeItem = new MenuItem("Home");
        MenuItem printItem = new MenuItem("Print");


        homeItem.setOnAction(evt -> {
            try {
                LessonPlanUI.getLessonPlanUIMap().clear();
                switchToHome();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        printItem.setOnAction(evt -> {
            if (isLessonPlanSaved) {
                LessonPlanUI.getLessonPlanUIMap().clear();
                App.switchToPrintPage();
            } else {
                showLessonPlanNotSavedWarning();
                save(evt);
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
        expandFilterSearchCardVBox();

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
        courseLabel.setText(App.currentCourse.getCourseName());
        courseLabel.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                renameCourseLabel();
            }
        });
        if (App.getCurrentCourseFile() == null) {
            showInstructionsPopUp();
        }
    }

    @FXML
    private void performTextSearch() {
        String query = filterSearchField.getText();
        List<Card> searchResults = searchFunction.performSearch(query);
        updateCardImageView(searchResults);
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
            CardUI cardUI = new CardUI((Card) CardLibrary.cardMap.get(cardKey), true);
            cardImageView.getChildren().add(cardUI);
        }
    }

    //this method will be used to expand the search bar scroll pane to show two columns of cards instead of one
    //when the user clicks on the expand button
    @FXML 
    private void expandFilterSearchCardVBox() {
        int newColumnCount = (cardImageView.getPrefColumns() == 1) ? 2 : 1;
        if (newColumnCount == 1) {
            expandButton.setText("Expand");
        } else {
            expandButton.setText("Collapse");
        }
        cardImageView.setPrefColumns(newColumnCount);
        // Calculate the new width for the filterSearchCardVBox
        double originalWidth = filterSearchCardVBox.getPrefWidth();
        double newWidth = (originalWidth == CardUI.CARD_THUMBNAIL_WIDTH + 20) ? CardUI.CARD_THUMBNAIL_WIDTH * 2 + 25 : CardUI.CARD_THUMBNAIL_WIDTH + 20;

        // Set the new width for the filterSearchCardVBox
        filterSearchCardVBox.setPrefWidth(newWidth);
        filterSearchCardVBox.setMinWidth(newWidth);
        filterSearchCardVBox.setMaxWidth(newWidth);
        // Set constraints on the filterSearchCardVBox within its parent container
        //VBox.setVgrow(filterSearchCardVBox, Priority.ALWAYS);

        // Calculate the new width for each column
        double columnWidth = newWidth / 2 - 10;

        // Set the preferred and max width for each card in the TilePane
        for (Node node : cardImageView.getChildren()) {
            if (node instanceof VBox) {
                VBox cardUI = (VBox) node;
                cardUI.setPrefWidth(columnWidth);
                //cardUI.setMinWidth(columnWidth);
                cardUI.setMaxWidth(columnWidth);
            }
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
    private void openCourse() {//Method is used to open a course file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Course File");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Courses (*.gymcourse)", "*.gymcourse");
        fileChooser.getExtensionFilters().add(filter);
        Window mainWindow = cardImageView.getScene().getWindow();
        File chosenFile = fileChooser.showOpenDialog(mainWindow);
        for (int tabIndex = 0; tabIndex < lessonPlanTabs.getTabs().size() -1; tabIndex++) {
            lessonPlanTabs.getTabs().remove(tabIndex);
        }
        openCourseWithFile(chosenFile);
    }

    @FXML
    private void openCourseWithFile(File file) {
        if (file != null) {
            for (LessonPlan lessonPlan : App.getCurrentCourse().getLessonPlanList()) {
                App.currentLessonPlan = lessonPlan;
                App.currentLessonPlanUI = new LessonPlanUI(lessonPlan);
                LessonTab lessonPlanTab = new LessonTab(lessonPlan);
                App.currentLessonPlanUI.setInLessonTab(lessonPlanTab);
                lessonPlanTab.setContent(App.currentLessonPlanUI);
                lessonPlanTab.setOnSelectionChanged(event -> {
                    setCurrentLessonPlanTab();
                });
                lessonPlanTabs.getTabs().add(lessonPlanTabs.getTabs().size()-1, lessonPlanTab);
                for (int index = 0; index < lessonPlan.getEventList().size(); index++) {
                    EventContainer eventContainer = (EventContainer) lessonPlan.getEventList().get(index);
                    EventContainerUI eventContainerUI = new EventContainerUI(eventContainer);
                    App.currentLessonPlanUI.drawEventContainerinLessonPlanUI(eventContainerUI);
                    Stack<CardUI> cardUIStack = new Stack<>(); // Used to reverse the order of the cards back to the original order
                        for (int cardIndex = eventContainer.getCards().size() -1; cardIndex >= 0; cardIndex--) {
                            Card card = (Card) CardLibrary.cardMap.get(eventContainer.getCards().get(cardIndex));
                            eventContainer.removeCard(eventContainer.getCards().get(cardIndex));
                            CardUI cardUI = new CardUI(card);
                            cardUI.setInEventContainerUI(eventContainerUI);
                            cardUIStack.push(cardUI);
                        }
                        while (!cardUIStack.isEmpty()) {
                            eventContainerUI.addCard(cardUIStack.pop());
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
    private void showInstructionsPopUp() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help");
        alert.setHeaderText("Double click to add and remove cards to events\n"
                + "Drag-and-drop allows cards from a certain event to be added to a different event\n" +
                "Double click a Lesson Plan or Course name to rename it\n");
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
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Courses (*.gymcourse)", "*.gymcourse");
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
        Tab currentTab = lessonPlanTabs.getSelectionModel().getSelectedItem();
        for (Object lessonPlanUIKey : LessonPlanUI.getLessonPlanUIMap().keySet()) {
            System.out.println(LessonPlanUI.getLessonPlanUIMap().get(lessonPlanUIKey));
        }
        App.currentLessonPlan = (LessonPlan) App.getCurrentCourse().getLessonPlan(currentTab.getText());
        App.currentLessonPlanUI = (LessonPlanUI) LessonPlanUI.getLessonPlanUIMap().get(App.getCurrentLessonPlan().getTitle());
    }

    private void createNewLessonPlanTab() {
        if (newTabButton.isSelected()) {
            String lessonPlanName = "New Lesson Plan";
            Set<String> lessonPlanTitles = new HashSet<String>();
            for (LessonPlan lessonPlanComparable : App.currentCourse.getLessonPlanList()) {
                lessonPlanTitles.add(lessonPlanComparable.getTitle());
            }
            if (lessonPlanTitles.contains(lessonPlanName)) {
                lessonPlanName = lessonPlanName + "1";
                for (String lessonPlanTitle : lessonPlanTitles) {
                    if (lessonPlanTitle.equalsIgnoreCase(lessonPlanName)) {
                        char lastChar = lessonPlanName.charAt(lessonPlanName.length() - 1);
                        int charInt = getNumericValue(lastChar);
                        charInt++;
                        lessonPlanName = lessonPlanName.substring(0, lessonPlanName.length() - 1) + charInt;
                    }
                }
            }




            LessonPlan newLessonPlan = new LessonPlan(lessonPlanName);
            App.currentCourse.addLessonPlan(newLessonPlan);
            LessonPlanUI newLessonPlanUI = new LessonPlanUI(newLessonPlan);
            App.currentLessonPlanUI = newLessonPlanUI;
            LessonTab newTab = new LessonTab(newLessonPlan);
            newLessonPlanUI.setInLessonTab(newTab);
            newTab.setOnSelectionChanged(event -> {
                setCurrentLessonPlanTab();
            });
            newTab.setContent(newLessonPlanUI);
            lessonPlanTabs.getTabs().add(lessonPlanTabs.getTabs().size() - 1, newTab);
            lessonPlanTabs.getSelectionModel().select(newTab);
        }
    }

    @FXML
    private void renameCourseLabel() {
            TextInputDialog dialog = new TextInputDialog(App.currentCourse.getCourseName());
            dialog.setTitle("Course Name");
            dialog.setHeaderText("Enter a new course name");
            dialog.setContentText("Course Name:");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent() && !result.get().equalsIgnoreCase("")) {
                App.currentCourse.renameCourse(result.get());
                courseLabel.setText(App.currentCourse.getCourseName());
            }
    }

}

