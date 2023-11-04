package edu.augustana;

import java.util.*;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
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

    private SearchFunction searchFunction;

    @FXML 
    public void initialize() {
        searchFunction = new SearchFunction(CardLibrary.cardList);
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
        printItem.setOnAction(evt -> {App.switchToPrintPage();}); //Printers.printLessonPlan(planeScrollPane);});
        fileMenu.getItems().addAll(homeItem, printItem);

        loadCards();
        addEventChoices();
        lessonPlanTitle.setText(currentLessonPlan.getTitle());
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
            HBox thumbnail = CardLibrary.cardList.get(cardNum).generateThumbnail();
            cardImageView.getItems().add(thumbnail);
        }
    }

    @FXML
    private void cardSearchFunction() {
        String query = filterSearchField.getText();
        List<Card> searchResults = searchFunction.performSearch(query);
        System.out.println(searchResults);
        updateCardImageView(searchResults);
    }


    private void updateCardImageView(List<Card> searchResults) {
        cardImageView.getItems().clear();

        for (Card card : searchResults) {
            HBox thumbnail = card.generateThumbnail();
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
                "Parallel Bars","Pommel Horse","Still Rings", "Tramp", "Uneven Bars","Vault");
    }

    private void addByDoubleClick() {
        System.out.println("Double clicked");
        HBox selectedCard = cardImageView.getSelectionModel().getSelectedItem();
        System.out.println(selectedCard.getId());
        for (Object key : currentLessonPlan.getEventMap().keySet()) {
            EventContainer container = (EventContainer) currentLessonPlan.getEventMap().get(key);
            if (container.getTitle().equalsIgnoreCase(selectedCard.getId())) {
                container.addCard(CardLibrary.cardList.get(cardImageView.getItems().indexOf(selectedCard)));
            }
        }
        currentLessonPlan.printTree();
    }


}


