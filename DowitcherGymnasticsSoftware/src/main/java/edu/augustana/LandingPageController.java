package edu.augustana;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class LandingPageController {
    @FXML
    private Button createImportButton;

    @FXML
    private void switchToCreatePopup() throws IOException {
        App.setRoot("editing page");
    }


}
