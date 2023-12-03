package edu.augustana;

import edu.augustana.ui.LessonPlanUI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    public static Stage stage;

    static LessonPlan currentLessonPlan;

    static LessonPlanUI currentLessonPlanUI;

    static Course currentCourse;

    static File currentCourseFile;
    static CardLibrary cardLibrary;


    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        stage.setMaximized(true);
        scene = new Scene(loadFXML("LandingPage"), 640, 480);
        stage.setScene(scene);
        stage.show();
        CardLibrary cardLibrary = new CardLibrary();
        cardLibrary.readInCards("src/main/resources/Card Packs");
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    private static void switchToView(String fxmlFileName) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxmlFileName));
            scene.setRoot(fxmlLoader.load());
        } catch (IOException ex) {
            System.err.println("Can't find FXML file " + fxmlFileName);
            ex.printStackTrace();
        }

    }
    public static void switchToLandingPage() {
        switchToView("LandingPage.fxml");
    }
    public static void switchToEditingPage() {
        switchToView("EditingPage.fxml");
    }
    public static void switchToCreateImportPage(){
        switchToView("CreateImportPage.fxml");
    }
    public static void loadCurrentCourseFromFile(File courseFile) throws IOException {
        currentCourse = Course.loadFromFile(courseFile);
        currentCourseFile = courseFile;
    }
    public static void saveCurrentCourseToFile(File chosenFile) throws IOException {
        currentCourse.saveToFile(chosenFile);
        currentCourseFile = chosenFile;
    }

    public static Course getCurrentCourse() {
        return currentCourse;
    }

    public static File getCurrentCourseFile() {
        return currentCourseFile;
    }
    public static LessonPlan getCurrentLessonPlan() {
        return currentLessonPlan;
    }

    public static LessonPlanUI getCurrentLessonPlanUI() {
        return currentLessonPlanUI;
    }

    public static void switchToPrintPage(){switchToView("PrintPage.fxml");}

    public static void main(String[] args) {
        launch();
    }

}