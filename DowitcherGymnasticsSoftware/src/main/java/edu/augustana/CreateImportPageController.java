package edu.augustana;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class CreateImportPageController {
    @FXML private TextField LessonPlanTextField;
    @FXML private Button Cancel;
    @FXML private Button Import;
    @FXML private Button Create;
    @FXML
    private void initialize() {
        LessonPlanTextField.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    this.switchToEditingPage();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @FXML
    private void titleRequiredWarning(String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void switchToEditingPage() throws IOException {
        if (LessonPlanTextField.getText().isEmpty()) {
            titleRequiredWarning("Create a title before making a lesson plan");
        } else {
            App.currentLessonPlanFile = null;
            App.currentLessonPlan = createLessonPlan();
            App.setRoot("EditingPage");
        }

    }
    @FXML
    private void handleCreateButtonClick() {
        try {
            switchToEditingPage();
        } catch (IOException e) {
            // Handle IOException, e.g., show an error message
            e.printStackTrace();
        }
    }

    private LessonPlan createLessonPlan() {
        LessonPlan currentLessonPlan = new LessonPlan(LessonPlanTextField.getText());
        return currentLessonPlan;
    }

}
