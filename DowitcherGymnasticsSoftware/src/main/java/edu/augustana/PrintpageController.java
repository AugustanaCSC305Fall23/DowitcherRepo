package edu.augustana;

import java.io.IOException;
import java.util.Map;

import com.google.gson.Gson;
import edu.augustana.ui.CardUI;
import edu.augustana.ui.EventContainerUI;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import static edu.augustana.App.currentLessonPlan;
import static edu.augustana.App.stage;

public class PrintpageController {
    @FXML
    private ScrollPane scrollpane;
    @FXML
    private ComboBox<Printer> printerChooser;
    @FXML
    private VBox printLessonPlanVBox;
    @FXML
    private GridPane gridpane;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Label lessonPlanLabel;

    @FXML
    public void initialize(){
        scrollpane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollpane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollpane.setMinWidth(1000);
        //stage.setMaximized(true);
        ObservableList<Printer> printerNames = FXCollections.observableArrayList();
        ObservableSet<Printer> printers = Printer.getAllPrinters();
        for (Printer printer : printers){
            printerNames.add(printer);
        }
        printerChooser.setItems(printerNames);

        lessonPlanLabel.setText(currentLessonPlan.getTitle());

    drawLessonPlan(App.currentLessonPlan);
    }

    @FXML
    public void drawLessonPlan(LessonPlan lessonPlan){
        Map map = App.getCurrentLessonPlan().getEventMap();
        System.out.println(map.toString());
        for (Object key : map.keySet()) {
            EventContainer eventContainer = (EventContainer) map.get(key);
            EventContainerUI eventContainerUI = new EventContainerUI(eventContainer);
            for (int cardIndex = 0; cardIndex < eventContainer.getCards().size(); cardIndex++) {
                Card card = (Card) CardLibrary.cardMap.get(eventContainer.getCards().get(cardIndex));
//                CardGraphic.addCardToEventContainerGraphic(vbox, card).setMinWidth(270*5);
                eventContainerUI.addCard(new CardUI(card));
                eventContainerUI.setMaxWidth(CardUI.CARD_THUMBNAIL_WIDTH*5);
            }
            printLessonPlanVBox.getChildren().add(eventContainerUI);
        }
    }
    @FXML
    private void switchToEditing() throws IOException {
        App.setRoot("EditingPage");
    }

    @FXML
    private void printScrollPane(){
        Printer pickedPrinter = selectPrinter();
        if (pickedPrinter== null){
            new Alert(Alert.AlertType.WARNING, "Select a printer first!").show();

        }else{
            Printers.print(scrollpane, pickedPrinter);
        }
    }
    @FXML
    private Printer selectPrinter(){
        return printerChooser.getValue();
    }
}