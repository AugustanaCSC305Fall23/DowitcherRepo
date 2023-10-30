package edu.augustana;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class EditingPageController {
    @FXML
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
    @FXML
    public void initialize() {

        MenuItem homeItem = new MenuItem("Home");
        MenuItem printItem = new MenuItem("Print");

        homeItem.setOnAction(evt -> {
            try {
                switchToHome();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        printItem.setOnAction(evt -> Printers.printLessonPlan(planeScrollPane));
        fileMenu.getItems().addAll(homeItem, printItem);

        loadCards();
    }
    @FXML
    private void switchToEditingPage() throws IOException {
        App.setRoot("editingPage.fxml");
    }
    @FXML
    private void switchToHome() throws IOException{
        App.setRoot("LandingPage");
    }

    @FXML
    private void loadCards() {
        for (int cardNum = 0; cardNum < CardLibrary.cardList.size(); cardNum++) {
            HBox thumbnail = CardLibrary.cardList.get(cardNum).generateThumbnail();
            cardImageView.getItems().add(thumbnail);
        }
    }



}


