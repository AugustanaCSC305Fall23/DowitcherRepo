package edu.augustana;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CreateImportPageController {
    @FXML private TextField LessonPlanTextField;
    @FXML private Button Cancel;
    @FXML private Button Import;
    @FXML private Button Create;

    @FXML
    private void switchToEditingPage() throws IOException {
        App.currentLessonPlan = createLessonPlan();
        App.setRoot("EditingPage");
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
