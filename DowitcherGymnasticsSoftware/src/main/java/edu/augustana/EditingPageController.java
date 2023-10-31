package edu.augustana;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;



public class EditingPageController {
    static LessonPlan currentLessonPlan;

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
    @FXML
    public void initialize() {
        System.out.println(currentLessonPlan.toString());
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
        addEventChoices();
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
            HBox thumbnail = CardLibrary.cardList.get(cardNum).generateThumbnail();
            cardImageView.getItems().add(thumbnail);
        }
    }



    private void addCardToEvent() {

    }

    @FXML
    private void addEvent() {
        EventContainer container = new EventContainer(eventChoiceButton.getValue().toString());
        currentLessonPlan.addEventContainer(container);
        lessonPlanVBox.getChildren().add(3, container.getVbox());
    }

    private void addEventChoices() {
        eventChoiceButton.getItems().addAll("Beam", "Floor", "Horizontal Bar",
                "Parallel Bars","Pommel Horse","Still Rings","Uneven Bars","Vault");
    }


}


