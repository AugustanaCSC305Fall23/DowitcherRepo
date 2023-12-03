package edu.augustana;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;


public class LandingPageController {
    @FXML
    private TilePane coursesTilePane;

    @FXML
    public void initialize() throws IOException {
        loadSavedCourses();
    }
    @FXML
    private void switchToEditingPage() throws IOException {
        App.currentCourse = createCourse();
//        App.currentLessonPlan= createLessonPlan();
//        App.currentCourse.addLessonPlan(App.currentLessonPlan);
        App.switchToEditingPage();

    }

//    private LessonPlan createLessonPlan() {
//        LessonPlan currentLessonPlan = new LessonPlan("New Lesson Plan");
//        App.currentCourse.addLessonPlan(currentLessonPlan);
//        App.currentLessonPlan = currentLessonPlan;
//        System.out.println(String.format("%s HAS BEEN CREATED", App.currentLessonPlan.getTitle()));
//        return currentLessonPlan;
//    }

    @FXML
    private Course createCourse() {
        return new Course("New Course");

    }

    private void openSavedCourse() {

    }
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
}
