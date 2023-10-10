package edu.augustana;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class CreateImportPageController {
    @FXML private Button Cancel;
    @FXML private Button Import;
    @FXML private Button Create;

    @FXML
    private void switchToCreatePopup() throws IOException {
        App.setRoot("EditingPage"); 
    }
}
