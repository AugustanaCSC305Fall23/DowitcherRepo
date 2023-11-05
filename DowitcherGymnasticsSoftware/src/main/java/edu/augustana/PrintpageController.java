package edu.augustana;

import java.io.IOException;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.print.Printer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import static edu.augustana.App.currentLessonPlan;
import static edu.augustana.App.stage;

public class PrintpageController {
    @FXML
    private ScrollPane scrollpane;
    @FXML
    private ComboBox<Printer> printerChooser;
    @FXML
    private GridPane gridpane;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Label lessonPlanLabel;

    @FXML
    public void initialize(){
        stage.setMaximized(true);
        ObservableList<Printer> printerNames = FXCollections.observableArrayList();
        ObservableSet<Printer> printers = Printer.getAllPrinters();
        for (Printer printer : printers){
            printerNames.add(printer);
        }
        printerChooser.setItems(printerNames);

        lessonPlanLabel.setText(currentLessonPlan.getTitle());

    //drawLessonPlan(EditingPageController.currentLessonPlan);
    }

    @FXML
    public void drawLessonPlan(LessonPlan lessonPlan){

    }
    @FXML
    private void switchToEditing() throws IOException {
        App.setRoot("EditingPage");
    }

    @FXML
    private void printScrollPane(){
        Printer pickedPrinter = selectPrinter();
        if (pickedPrinter== null){
            System.out.println("Printer is still null");

        }else{
            Printers.print(scrollpane, pickedPrinter);
        }
    }
    @FXML
    private Printer selectPrinter(){
        return printerChooser.getValue();
    }
}