package edu.augustana;

import java.io.IOException;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
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
    @FXML
    private Label lessonPlanLabel;
    /**
     *
     */
    @FXML
    public void initialize(){
        scrollpane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollpane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollpane.setMinWidth(1000);

        //stage.setMaximized(true);
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
        int eventCount = 0;
        Map map = App.getCurrentLessonPlan().getEventMap();
        System.out.println(map.toString());
        for (Object key : map.keySet()) {
            EventContainer eventContainer = (EventContainer) map.get(key);
            VBox vbox = CardGraphic.generateEventContainerGraphic(eventContainer);
            eventCount++;
            for (int cardIndex = 0; cardIndex < eventContainer.getCards().size(); cardIndex++) {
                Card card = (Card) CardLibrary.cardMap.get(eventContainer.getCards().get(cardIndex));
                CardGraphic.addCardToEventContainerGraphic(vbox, card).setMinWidth(270*5);
            }
            printLessonPlanVBox.getChildren().add(vbox);
        }
        System.out.println("Events: " + eventCount);
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
        Map map = App.getCurrentLessonPlan().getEventMap();
        for (Object key : map.keySet()){
            String eventText = currentLessonPlan.getTitle();
            EventContainer eventContainer = (EventContainer) map.get(key);
            eventText = (eventContainer.getType()+"\n");
            for (int cardIndex = 0; cardIndex < eventContainer.getCards().size(); cardIndex++){
                Card card = (Card) CardLibrary.cardMap.get(eventContainer.getCards().get(cardIndex));
                eventText = eventText + card.getTitle() + ", ";

            }

            Text text = new Text (eventText);
            text.setFont(Font.font(16));
            printLessonPlanVBox.getChildren().addAll( text);

        }
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
            Printers.print(scrollpane, pickedPrinter);
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