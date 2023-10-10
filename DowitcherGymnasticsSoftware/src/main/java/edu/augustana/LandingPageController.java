package edu.augustana;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class LandingPageController {
    @FXML
    private Button createImportButton;

    @FXML
    public void initialize(){

    }
    @FXML
    private void switchToCreatePopup() throws IOException {
        App.switchToCreateImportPage();

    }
}
