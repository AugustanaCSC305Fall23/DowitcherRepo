package edu.augustana;

import java.io.IOException;
import javafx.fxml.FXML;

public class EditingPageController {
    @FXML
    private void switchToEditingPage() throws IOException {
        App.setRoot("editingPage");
    }
}
