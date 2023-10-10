package edu.augustana;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class EditingPageController {
    @FXML
    private Button addEventButton;

    @FXML
    private Button applyFilterButton;

    @FXML
    private ListView<Image> cardImageView;


    @FXML
    private Button clearFilterButton;

    @FXML
    private Button equipmentFilterButton;

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
    public void initialize() {
    }
    @FXML
    private void switchToEditingPage() throws IOException {
        App.setRoot("editingPage.fxml");
    }


}
