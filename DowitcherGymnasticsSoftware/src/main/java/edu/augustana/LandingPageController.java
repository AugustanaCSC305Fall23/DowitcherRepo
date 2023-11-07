package edu.augustana;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;


public class LandingPageController {
    @FXML
    private Button createImportButton;
    @FXML
    private TilePane lessonPlansTilePane;

    @FXML
    public void initialize() throws IOException {

        loadSavedLessonPlans();
    }
    @FXML
    private void switchToCreatePopup() throws IOException {
        App.switchToCreateImportPage();

    }

    @FXML
    private void loadSavedLessonPlans() throws IOException {
        lessonPlansTilePane.setHgap(10);
        File folder = new File("src/main/resources/Saved Lesson Plans");
        System.out.println(folder.listFiles());
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles != null) {

            for (File file : listOfFiles) {
                if (file.isFile()) {
                    VBox savedPlanVBox = new VBox();
                    Label savedPlanLabel = new Label(file.getName().substring(0, file.getName().length() - 14));
                    savedPlanLabel.setStyle("-fx-font-size: 18;" + "-fx-font-weight: bold;");
                    savedPlanVBox.getChildren().add(savedPlanLabel);
                    BasicFileAttributes dateCreated = Files.readAttributes(Paths.get(file.getPath()), BasicFileAttributes.class); //Made using https://stackoverflow.com/questions/3154488/how-do-i-iterate-through-the-files-in-a-directory-and-its-sub-directories-in-ja
                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                    String formattedDate = formatter.format(dateCreated.creationTime().toMillis());
                    Label dateCreatedLabel = new Label(formattedDate);
                    savedPlanVBox.getChildren().add(dateCreatedLabel);
                    savedPlanVBox.setStyle("-fx-border-width: 2;" + "-fx-border-color: black;" + "-fx-border-radius: 5;" + "-fx-background-radius: 5; -fx-padding: 10;");
                    savedPlanVBox.setOnMouseEntered(e -> { //Autofilled with GitHub Co-Pilot
                        savedPlanVBox.setStyle("-fx-border-width: 2;" + "-fx-border-color: black;" + "-fx-border-radius: 5;" + "-fx-background-radius: 5; -fx-padding: 10;" + "-fx-background-color: #d3d3d3;");
                    });
                    savedPlanVBox.setOnMouseExited(e -> { //Autofilled with GitHub Co-Pilot
                        savedPlanVBox.setStyle("-fx-border-width: 2;" + "-fx-border-color: black;" + "-fx-border-radius: 5;" + "-fx-background-radius: 5; -fx-padding: 10;");
                    });
                    savedPlanVBox.setOnMouseClicked(e -> {
                        try {
                            App.loadCurrentLessonPlanFromFile(file);
                            App.switchToEditingPage();
                            //EditingPageController.openLessonPlanWithFile(file);
                        } catch (Exception exception) {
                            exception.printStackTrace();

                        }
                    });
                    System.out.println(file.getName());
                    lessonPlansTilePane.getChildren().add(savedPlanVBox);
                }
            }
        }
    }
}
