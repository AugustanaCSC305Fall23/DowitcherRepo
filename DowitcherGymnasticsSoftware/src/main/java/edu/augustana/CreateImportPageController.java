package edu.augustana;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class CreateImportPageController {
    @FXML private Button Cancel;
    @FXML private Button Import;
    @FXML private Button Create;

    @FXML
    private void switchToEditingPage() throws IOException {
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
}
