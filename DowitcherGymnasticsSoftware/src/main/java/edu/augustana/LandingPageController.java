package edu.augustana;

import java.io.IOException;

import atlantafx.base.theme.CupertinoDark;
import atlantafx.base.theme.CupertinoLight;
import atlantafx.base.theme.PrimerLight;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;

/**
 * This class is the controller for the landing page of the application.
 * It is responsible for loading saved courses, creating new courses, and opening saved courses.
 * It also contains the methods for switching between light and dark mode.
 */
public class LandingPageController {
    @FXML
    private TilePane coursesTilePane;
    @FXML
    private Button openCourseButton;

    /**
     * This method is called when the application is first opened.
     * It loads all saved courses and displays them on the landing page.
     * @throws IOException
     */
    @FXML
    public void initialize() throws IOException {
        loadSavedCourses();
    }
    /**
     * This method is called when the user clicks the "New Course" button.
     * It creates a new course and switches to the editing page.
     * @throws IOException
     */
    @FXML
    private void switchToEditingPage() throws IOException {
        App.currentCourse = createCourse();
        App.currentCourseFile = null;
        App.switchToEditingPage();

    }


    /**
     * This method creates a new course with the name "New Course".
     * @return the new course
     */
    @FXML
    private Course createCourse() {
        return new Course("New Course");

    }

    /**
     * This method is called when the user clicks the "Open Course" button.
     * It opens a file chooser and allows the user to select a saved course to open.
     * @throws IOException
     */
    @FXML
    private void openSavedCourse() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Course File");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Courses (*.gymcourse)", "*.gymcourse");
        fileChooser.getExtensionFilters().add(filter);
        Window mainWindow = coursesTilePane.getScene().getWindow();
        File chosenFile = fileChooser.showOpenDialog(mainWindow);
        App.loadCurrentCourseFromFile(chosenFile);
        App.switchToEditingPage();
    }
    /**
     * This method is called when the user clicks the "Load Saved Courses" button.
     * It loads all saved courses and displays them on the landing page.
     * @throws IOException
     */
    @FXML
    private void loadSavedCourses() throws IOException {
        coursesTilePane.setHgap(10);
        File folder = new File("src/main/resources/Saved Courses");
        System.out.println(folder.listFiles());
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles != null) {

            for (File file : listOfFiles) {
                if (file.isFile()) {
                    VBox savedCourseVBox = new VBox();
                    Label savedCourseLabel = new Label(file.getName().substring(0, file.getName().length() - 10));
                    savedCourseLabel.setStyle("-fx-font-size: 18;" + "-fx-font-weight: bold;");
                    savedCourseVBox.getChildren().add(savedCourseLabel);
                    BasicFileAttributes dateCreated = Files.readAttributes(Paths.get(file.getPath()), BasicFileAttributes.class); //Made using https://stackoverflow.com/questions/3154488/how-do-i-iterate-through-the-files-in-a-directory-and-its-sub-directories-in-ja
                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                    String formattedDate = formatter.format(dateCreated.creationTime().toMillis());
                    Label dateCreatedLabel = new Label(formattedDate);
                    savedCourseVBox.getChildren().add(dateCreatedLabel);
                    savedCourseVBox.setStyle("-fx-border-width: 2;" + "-fx-border-color: black;" + "-fx-border-radius: 5;" + "-fx-background-radius: 5; -fx-padding: 10;");
                    savedCourseVBox.setOnMouseEntered(e -> { //Autofilled with GitHub Co-Pilot
                        savedCourseVBox.setStyle("-fx-border-width: 2;" + "-fx-border-color: black;" + "-fx-border-radius: 5;" + "-fx-background-radius: 5; -fx-padding: 10;" + "-fx-background-color: #d3d3d3;");
                    });
                    savedCourseVBox.setOnMouseExited(e -> { //Autofilled with GitHub Co-Pilot
                        savedCourseVBox.setStyle("-fx-border-width: 2;" + "-fx-border-color: black;" + "-fx-border-radius: 5;" + "-fx-background-radius: 5; -fx-padding: 10;");
                    });
                    savedCourseVBox.setOnMouseClicked(e -> {
                        try {
                            App.loadCurrentCourseFromFile(file);
                            App.switchToEditingPage();
                            //EditingPageController.openLessonPlanWithFile(file);
                        } catch (Exception exception) {
                            exception.printStackTrace();

                        }
                    });
                    System.out.println(file.getName());
                    coursesTilePane.getChildren().add(savedCourseVBox);
                }
            }
        }
    }

    /**
     * This method is called when the user clicks the "About" button.
     * It opens a popup with information about the application.
     */
    @FXML
    public void openAboutPopup() {
        Alert aboutAlert = new Alert(Alert.AlertType.INFORMATION);
        aboutAlert.setTitle("About");
        aboutAlert.setHeaderText("Dowitcher Gymnastics Software");
        aboutAlert.setContentText("Developed by:\n" +
                "Luke Heinrichs\n" +
                "Sean Abracia-Wendel\n" +
                "Patrick Mayer\n" +
                "Moises Salinas\n" +"" +
                "\n" +
                "Project Supervisor:\n" +
                "Dr. Forrest Stonedahl\n"  +
                "\n" +
                "Product Designer: \n" +
                "Samantha Keehn\n" +
                "\n" +
                "Special Thanks to:\n" +
                "AtlantaFX for their free CSS themes: https://github.com/mkpaz/atlantafx");
        aboutAlert.showAndWait();
    }

    /**
     * This method is called when the user clicks the "Dark Mode" button.
     * It switches the application to dark mode.
     */
    @FXML
    private void toggleDarkMode() {
        Application.setUserAgentStylesheet(new CupertinoDark().getUserAgentStylesheet());
    }

    /**
     * This method is called when the user clicks the "Light Mode" button.
     * It switches the application to light mode.
     */
    @FXML
    private void toggleLightMode() {
        Application.setUserAgentStylesheet(new CupertinoLight().getUserAgentStylesheet());
    }
}
