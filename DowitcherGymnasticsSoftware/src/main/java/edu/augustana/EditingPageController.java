package edu.augustana;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

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
    private Button levelFilterButton;

    @FXML
    private Button subCategoryFilterButton;
    @FXML
    private void switchToEditingPage() throws IOException {
        App.setRoot("editingPage");
    }
}
