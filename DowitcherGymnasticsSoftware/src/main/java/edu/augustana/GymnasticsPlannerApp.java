package edu.augustana;

import atlantafx.base.theme.*;
import edu.augustana.datastructure.CardLibrary;
import edu.augustana.datastructure.Course;
import edu.augustana.datastructure.LessonPlan;
import edu.augustana.ui.LessonPlanUI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * JavaFX GymnasticsPlannerApp
 */
public class GymnasticsPlannerApp extends Application {

    public static Stage stage;

    static LessonPlan currentLessonPlan;

    static LessonPlanUI currentLessonPlanUI;

    static Course currentCourse;

    static File currentCourseFile;
    static CardLibrary cardLibrary;


    private static Scene scene;

    /**
     * This method is called when the application is first opened.
     * It loads all cards from the Card Packs folder and puts them into the cardLibrary.
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        stage.setMaximized(true);
        scene = new Scene(loadFXML("LandingPage"), 640, 480);
        stage.setScene(scene);
        stage.show();
        CardLibrary cardLibrary = new CardLibrary();
        cardLibrary.readInCards("src/main/resources/Card Packs");
        Application.setUserAgentStylesheet(new CupertinoLight().getUserAgentStylesheet());

    }

    /**
     * This method loads an FXML file and returns the Parent object of the FXML file.
     * @param fxml - name of the FXML file to be loaded
     * @return - the Parent object of the FXML file
     * @throws IOException - if the FXML file cannot be found
     */

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GymnasticsPlannerApp.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * This method switches the scene to the FXML file with the given name.
     * @param fxmlFileName - name of the FXML file to be switched to
     */

    private static void switchToView(String fxmlFileName) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(GymnasticsPlannerApp.class.getResource(fxmlFileName));
            scene.setRoot(fxmlLoader.load());
        } catch (IOException ex) {
            System.err.println("Can't find FXML file " + fxmlFileName);
            ex.printStackTrace();
        }

    }

    /**
     * This method switches the scene to the landing page.
     */
    public static void switchToLandingPage() {
        switchToView("LandingPage.fxml");
    }

    /**
     * This method switches the scene to the editing page.
     */
    public static void switchToEditingPage() {
        switchToView("EditingPage.fxml");
    }

    /**
     * This method loads a course from a file.
     * @param courseFile - file to load the course from
     * @throws IOException - if the file cannot be loaded
     */
    public static void loadCurrentCourseFromFile(File courseFile) throws IOException {
        currentCourse = Course.loadFromFile(courseFile);
        currentCourseFile = courseFile;
    }

    /**
     * This method saves the current course to a file.
     * @param chosenFile - file to save the current course to
     * @throws IOException - if the file cannot be saved
     */
    public static void saveCurrentCourseToFile(File chosenFile) throws IOException {
        currentCourse.saveToFile(chosenFile);
        currentCourseFile = chosenFile;
    }

    /**
     * This method returns the current course.
     * @return - the current course
     */
    public static Course getCurrentCourse() {
        return currentCourse;
    }

    /**
     * This method returns the current course file.
     * @return
     */
    public static File getCurrentCourseFile() {
        return currentCourseFile;
    }

    /**
     * This method returns the current lesson plan.
     * @return - the current lesson plan
     */
    public static LessonPlan getCurrentLessonPlan() {
        return currentLessonPlan;
    }

    /**
     * This method returns the current lesson plan UI.
     * @return - the current lesson plan UI
     */
    public static LessonPlanUI getCurrentLessonPlanUI() {
        return currentLessonPlanUI;
    }

    /**
     * This method sets the current lesson plan.
     * @param lessonPlan - the lesson plan to be set as the current lesson plan
     */
    public static void setCurrentLessonPlan(LessonPlan lessonPlan) {
        currentLessonPlan = lessonPlan;
    }

    /**
     * This method sets the current lesson plan UI.
     * @param lessonPlanUI - the lesson plan UI to be set as the current lesson plan UI
     */
    public static void setCurrentLessonPlanUI(LessonPlanUI lessonPlanUI) {
        currentLessonPlanUI = lessonPlanUI;
    }

    /**
     * This method sets the current course.
     * @param course - the course to be set as the current course
     */
    public static void setCurrentCourse(Course course) {
        currentCourse = course;
    }

    /**
     * This method sets the current course file.
     * @param courseFile - the course file to be set as the current course file
     */
    public static void setCurrentCourseFile(File courseFile) {
        currentCourseFile = courseFile;
    }

    /**
     * This method switches the scene to the print page.
     */
    public static void switchToPrintPage(){switchToView("PrintPage.fxml");}

    public static void main(String[] args) {
        launch();
    }

}