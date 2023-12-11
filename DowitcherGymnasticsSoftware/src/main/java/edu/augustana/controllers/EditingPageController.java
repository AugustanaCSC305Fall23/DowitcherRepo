package edu.augustana.controllers;

import java.io.File;
import java.util.*;
import java.io.IOException;

import atlantafx.base.theme.CupertinoDark;
import atlantafx.base.theme.CupertinoLight;
import edu.augustana.search.FilterSearch;
import edu.augustana.GymnasticsPlannerApp;
import edu.augustana.search.SearchFunction;
import edu.augustana.datastructure.Card;
import edu.augustana.datastructure.CardLibrary;
import edu.augustana.datastructure.EventContainer;
import edu.augustana.datastructure.LessonPlan;
import edu.augustana.ui.CardUI;
import edu.augustana.ui.EventContainerUI;
import edu.augustana.ui.LessonPlanUI;
import edu.augustana.ui.LessonTab;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.control.*;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import static java.lang.Character.getNumericValue;

/**
 * The controller class for the editing page, responsible for handling user interactions and managing UI elements.
 */
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
    private CheckBox neutralCheckBox;

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


    /**
     * Initializes the controller. This method is automatically called by JavaFX after the FXML file is loaded.
     */
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


        //System.out.println(GymnasticsPlannerApp.currentLessonPlan.toString());
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
                GymnasticsPlannerApp.switchToPrintPage();
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
        if (GymnasticsPlannerApp.getCurrentCourseFile() != null) {
            openCourseWithFile(GymnasticsPlannerApp.getCurrentCourseFile());
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
                femaleCheckBox,
                neutralCheckBox
        ), CardLibrary.cardList, cardImageView);

      //  filterSearchField.setOnKeyPressed(this::handleSearchKeyPress);

        ///////////////////////////////////////////////////////////
        newTabButton.setOnSelectionChanged(event -> {
            createNewLessonPlanTab();
        });
        courseLabel.setText(GymnasticsPlannerApp.getCurrentCourse().getCourseName());
        courseLabel.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                renameCourseLabel();
            }
        });
        if (GymnasticsPlannerApp.getCurrentCourseFile() == null) {
            showInstructionsPopUp();
        }
    }

    /**
     * Performs a text-based search using the current query in the filter search field.
     * Retrieves the search results using the SearchFunction and updates the card image view accordingly.
     */
    @FXML
    private void performTextSearch() {
        String query = filterSearchField.getText();
        List<Card> searchResults = searchFunction.performSearch(query);
        updateCardImageView(searchResults);
    }

    /**
     * Switches the application's view to the home page.
     *
     * @throws IOException If an I/O exception occurs during the view transition.
     */
    @FXML
    private void switchToHome() throws IOException{
        GymnasticsPlannerApp.switchToLandingPage();
    }

    /**
     * Loads cards from the CardLibrary into the card image view.
     * Creates CardUI elements for each card and adds them to the card image view.
     */
    @FXML
    private void loadCards() {
        for (Object cardKey : CardLibrary.cardMap.keySet()) {
            CardUI cardUI = new CardUI((Card) CardLibrary.cardMap.get(cardKey), true);
            cardImageView.getChildren().add(cardUI);
        }
    }

    /**
     * Toggles the visibility of a second column in the card image view, effectively expanding or collapsing the search bar.
     * Updates button text and adjusts the layout properties of the card image view and associated containers accordingly.
     */
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


    /**
     * Handles the event when the Enter key is pressed in the filter search field for card search.
     */
    @FXML
    private void cardSearchFunction() {
        String query = filterSearchField.getText();
        List<Card> searchResults = searchFunction.performSearch(query);
        System.out.println(searchResults);
        updateCardImageView(searchResults);
//        System.out.println("ENTER was pressed");
//        System.out.println(searchResults.toString());
    }

    /**
     * Updates the card image view based on the search results.
     *
     * @param searchResults The list of cards matching the search criteria.
     */
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

    /**
     * Clears the applied filters and displays all available cards in the card image view.
     * Invokes the corresponding method in the FilterSearch instance to reset the filtering.
     */
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

    /**
     * Opens a course file and populates the application UI with the lesson plans and associated cards.
     *
     * @param file The file representing the course to be opened.
     */
    @FXML
    private void openCourseWithFile(File file) {
        if (file != null) {
            for (LessonPlan lessonPlan : GymnasticsPlannerApp.getCurrentCourse().getLessonPlanList()) {
                GymnasticsPlannerApp.setCurrentLessonPlan(lessonPlan);
                GymnasticsPlannerApp.setCurrentLessonPlanUI(new LessonPlanUI(lessonPlan));
                LessonTab lessonPlanTab = new LessonTab(lessonPlan);
                GymnasticsPlannerApp.getCurrentLessonPlanUI().setInLessonTab(lessonPlanTab);
                lessonPlanTab.setContent(GymnasticsPlannerApp.getCurrentLessonPlanUI());
                lessonPlanTab.setOnSelectionChanged(event -> {
                    setCurrentLessonPlanTab();
                });
                lessonPlanTabs.getTabs().add(lessonPlanTabs.getTabs().size()-1, lessonPlanTab);
                for (int index = 0; index < lessonPlan.getEventList().size(); index++) {
                    EventContainer eventContainer = (EventContainer) lessonPlan.getEventList().get(index);
                    EventContainerUI eventContainerUI = new EventContainerUI(eventContainer);
                    GymnasticsPlannerApp.getCurrentLessonPlanUI().drawEventContainerinLessonPlanUI(eventContainerUI);
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


    /**
     * Displays a warning alert informing the user that the lesson plan must be saved before attempting to print.
     * The alert provides information about the warning and waits for user acknowledgment.
     */
    @FXML
    private void showLessonPlanNotSavedWarning() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText("You must save the lesson plan before attempting to print.");
        alert.showAndWait();
    }

    /**
     * Displays an informational alert providing instructions on how to interact with the application.
     * The alert includes guidance on double-clicking to add and remove cards to events,
     * drag-and-drop functionality for moving cards between events, and renaming Lesson Plans or Course names with a double-click.
     * The alert waits for user acknowledgment.
     */
    @FXML
    private void showInstructionsPopUp() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help");
        alert.setHeaderText("Double click to add and remove cards to events\n"
                + "Drag-and-drop allows cards from a certain event to be added to a different event\n" +
                "Double click a Lesson Plan or Course name to rename it\n");
        alert.showAndWait();
    }

    /**
     * Saves the current lesson plan, marking it as saved, either by overwriting the existing course file
     * (if one is associated with the current course) or by prompting the user to choose a new file location.
     *
     * @param event The ActionEvent that triggers the save operation.
     */
    @FXML
    private void save(ActionEvent event) {
        isLessonPlanSaved = true;
        if (GymnasticsPlannerApp.getCurrentCourseFile() == null) {
            saveAs(event);
        } else {
            saveCurrentCourseToFile(GymnasticsPlannerApp.getCurrentCourseFile());
        }
     }

    /**
     * Opens a FileChooser dialog to prompt the user to choose a location for saving the current course.
     * The dialog is configured with a default initial directory and a filter for course files.
     * After the user selects a file location, the method proceeds to save the current course to the chosen file.
     *
     * @param event The ActionEvent that triggers the "Save As" operation.
     */
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

    /**
     * Saves the current course to the specified file. If the chosenFile parameter is not null,
     * it invokes the GymnasticsPlannerApp.saveCurrentCourseToFile method to perform the actual saving.
     * In case of an IOException during the saving process, an error alert is displayed to the user.
     *
     * @param chosenFile The File object representing the location where the current course should be saved.
     */
    @FXML
    private void saveCurrentCourseToFile(File chosenFile) {
        if (chosenFile != null) {
            try {
                GymnasticsPlannerApp.saveCurrentCourseToFile(chosenFile);
            } catch (IOException ex) {
                new Alert(Alert.AlertType.ERROR, "Error saving course file: " + chosenFile).show();
            }
        }
    }

    /**
     * Sets the current lesson plan and its corresponding UI based on the selected tab in the lessonPlanTabs.
     * It retrieves the selected tab, identifies the associated lesson plan, and sets the current lesson plan
     * and its UI representation (LessonPlanUI) for further interactions.
     */
    @FXML
    private void setCurrentLessonPlanTab() {
        Tab currentTab = lessonPlanTabs.getSelectionModel().getSelectedItem();
        for (Object lessonPlanUIKey : LessonPlanUI.getLessonPlanUIMap().keySet()) {
            System.out.println(LessonPlanUI.getLessonPlanUIMap().get(lessonPlanUIKey));
        }
        GymnasticsPlannerApp.setCurrentLessonPlan((LessonPlan) GymnasticsPlannerApp.getCurrentCourse().getLessonPlan(currentTab.getText()));
        GymnasticsPlannerApp.setCurrentLessonPlanUI((LessonPlanUI) LessonPlanUI.getLessonPlanUIMap().get(GymnasticsPlannerApp.getCurrentLessonPlan().getTitle()));
    }

    /**
     * Creates a new lesson plan tab when triggered by the "New Tab" button. It generates a unique name for the
     * lesson plan, ensures uniqueness among existing lesson plans, and adds the new lesson plan along with its
     * corresponding UI representation (LessonPlanUI) to the application. The newly created tab is then selected
     * in the lessonPlanTabs for immediate user interaction.
     */
    private void createNewLessonPlanTab() {
        if (newTabButton.isSelected()) {
            String lessonPlanName = "New Lesson Plan";
            Set<String> lessonPlanTitles = new HashSet<String>();
            for (LessonPlan lessonPlanComparable : GymnasticsPlannerApp.getCurrentCourse().getLessonPlanList()) {
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
            GymnasticsPlannerApp.getCurrentCourse().addLessonPlan(newLessonPlan);
            LessonPlanUI newLessonPlanUI = new LessonPlanUI(newLessonPlan);
            GymnasticsPlannerApp.setCurrentLessonPlanUI(newLessonPlanUI);
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

    /**
     * Opens a dialog prompting the user to enter a new name for the current course. The current course's name is
     * initially set as the default input value. If the user provides a valid and non-empty input, the current course's
     * name is updated with the new value, and the corresponding label in the user interface is also updated to reflect
     * the change.
     */
    @FXML
    private void renameCourseLabel() {
            TextInputDialog dialog = new TextInputDialog(GymnasticsPlannerApp.getCurrentCourse().getCourseName());
            dialog.setTitle("Course Name");
            dialog.setHeaderText("Enter a new course name");
            dialog.setContentText("Course Name:");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent() && !result.get().equalsIgnoreCase("")) {
                GymnasticsPlannerApp.getCurrentCourse().renameCourse(result.get());
                courseLabel.setText(GymnasticsPlannerApp.getCurrentCourse().getCourseName());
            }
    }

    @FXML
    private void toggleDarkMode() {
        Application.setUserAgentStylesheet(new CupertinoDark().getUserAgentStylesheet());
    }

    @FXML
    private void toggleLightMode() {
        Application.setUserAgentStylesheet(new CupertinoLight().getUserAgentStylesheet());
    }
}

