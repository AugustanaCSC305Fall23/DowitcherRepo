package edu.augustana;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import edu.augustana.ui.CardUI;
import edu.augustana.ui.EventContainerUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.print.Printer;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import static edu.augustana.App.currentLessonPlan;

public class PrintpageController {
    @FXML
    private ScrollPane scrollpane;
    @FXML
    private ComboBox<Printer> printerChooser;
    @FXML
    private VBox printLessonPlanVBox;

    private int eventCount = 0;
    /**
     *
     */
    @FXML
    public void initialize(){
        scrollpane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollpane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollpane.setMinWidth(1000);
        scrollpane.setPrefWidth(270*4);
        ObservableList<Printer> printerNames = FXCollections.observableArrayList();
        ObservableSet<Printer> printers = Printer.getAllPrinters();
        printerNames.addAll(printers);
        printerChooser.setItems(printerNames);
    }
    /**
     *
     */
    public void setImageView(){
        printLessonPlanVBox.getChildren().clear();
        scrollpane.setContent(drawLessonPlan());
    }
    /**
     *
     * @return
     */
    @FXML
    public Node drawLessonPlan() {
        Text titleLabel = new Text();
        titleLabel.setText(currentLessonPlan.getTitle());
        titleLabel.setFont(Font.font(24));
        printLessonPlanVBox.getChildren().add(titleLabel);

        List eventList = App.getCurrentLessonPlan().getEventList();
        System.out.println(eventList.toString());
        for (Object loopedEventContainer : eventList) {
            EventContainer eventContainer = (EventContainer) loopedEventContainer;

            EventContainerUI eventContainerUI = new EventContainerUI(eventContainer);
            eventCount++;

            eventContainerUI.setMinWidth(CardUI.CARD_THUMBNAIL_WIDTH*4+20);
            int cardCount = eventContainerUI.drawCardInEventContainerUI();
            if(cardCount > 4){
                eventCount++;
            }
            printLessonPlanVBox.getChildren().add(eventContainerUI);
        }
        System.out.println("Events: " + eventCount);
        if(eventCount > 4) {
            scrollpane.setPrefHeight(950);
        }else{
            scrollpane.setPrefHeight(250*eventCount);
        }
        return printLessonPlanVBox;
    }

    /**
     *
     */
    public void setTextView() {
        printLessonPlanVBox.getChildren().clear();
        scrollpane.setContent(typeLessonPlan());
    }

    /**
     *
     * @return
     */
    @FXML
    public Node typeLessonPlan() {
        Text titleText = new Text ();
        titleText.setText(currentLessonPlan.getTitle());
        titleText.setFont(Font.font(24));
        printLessonPlanVBox.getChildren().add(titleText);
        List eventList = App.getCurrentLessonPlan().getEventList();
        for (Object loopedEventContainer : eventList){
            String eventText = currentLessonPlan.getTitle();
            EventContainer eventContainer = (EventContainer) loopedEventContainer;
            eventText = (eventContainer.getType()+"\n");
            for (int cardIndex = 0; cardIndex < eventContainer.getCards().size(); cardIndex++){
                Card card = (Card) CardLibrary.cardMap.get(eventContainer.getCards().get(cardIndex));
                eventText = eventText + card.getCode() + " " + card.getTitle() + ", ";

            }

            Text text = new Text (eventText);
            text.setFont(Font.font(16));
            printLessonPlanVBox.getChildren().addAll(text);


        }
        scrollpane.setPrefHeight(500);
        scrollpane.setPrefWidth(270*4);
        return printLessonPlanVBox;
    }

    /**
     *
     * @throws IOException
     */
    @FXML
    private void switchToEditing() throws IOException {
        App.setRoot("EditingPage");
    }

    /**
     *
     */
    @FXML
    private void printScrollPane(){
        Printer pickedPrinter = selectPrinter();
        if (pickedPrinter== null){
            new Alert(Alert.AlertType.WARNING, "Select a printer first!").show();

        }else{
            Printers.print(scrollpane, pickedPrinter, eventCount);
        }
    }

    /**
     *
     * @return
     */
    @FXML
    private Printer selectPrinter(){
        return printerChooser.getValue();
    }
}