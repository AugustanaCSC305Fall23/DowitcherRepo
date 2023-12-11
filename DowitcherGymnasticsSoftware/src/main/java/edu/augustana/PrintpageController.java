package edu.augustana;

import java.io.IOException;
import java.util.List;
import edu.augustana.ui.CardUI;
import edu.augustana.ui.EventContainerUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.print.Printer;
import javafx.scene.Node;
import javafx.scene.control.*;
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
        //Hide the scroll bars on the ScrollPane
        scrollpane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollpane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        scrollpane.setMinWidth(1000);
        scrollpane.setPrefWidth(270*4);
        //Get the available printers and add them to the printer ComboBox
        ObservableList<Printer> printerNames = FXCollections.observableArrayList();
        ObservableSet<Printer> printers = Printer.getAllPrinters();
        printerNames.addAll(printers);
        printerChooser.setItems(printerNames);
    }
    /**This method clears the Lesson Plan VBox and displays
     *the visual Lesson Plan in the ScrollPane
     */
    public void setImageView(){
        printLessonPlanVBox.getChildren().clear();
        scrollpane.setContent(drawLessonPlan());
    }
    /** This method Draws the Title and Lesson Plan onto the ScrollPane
     *
     * @returns -- VBox containing the Title and Lesson Plan visuals
     */
    @FXML
    public Node drawLessonPlan() {
        eventCount = 0;
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
        if(eventCount > 4) {
            scrollpane.setPrefHeight(925);
        }else{
            scrollpane.setPrefHeight(250*eventCount);
        }
        return printLessonPlanVBox;
    }

    /**This method clears the Lesson Plan VBox and
     *draws the text view of the Lesson Plan in the ScrollPane
     */
    public void setTextView() {
        printLessonPlanVBox.getChildren().clear();
        scrollpane.setContent(typeLessonPlan());
    }

    /**This method types the Lesson Plan onto the ScrollPane
     *
     * @returns -- VBox that has the typed format of the Lesson Plan
     */
    @FXML
    public Node typeLessonPlan() {
        //Sets title
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
                //Prevents text from getting to long across by starting new line
                if(cardIndex == 4){
                    eventText = eventText + "\n";
                }
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

    /**Gets the selected printer and passes it along with the ScrollPane and Event Count
     *Also gives a warning if user tries to print without selecting a printer
     */
    @FXML
    private void printScrollPane(){
        Printer pickedPrinter = selectPrinter();
        if (pickedPrinter== null){
            new Alert(Alert.AlertType.WARNING, "Select a printer first!").show();

        }else{
            Printers.print(scrollpane,pickedPrinter, eventCount);
        }
    }

    /** Returns selected printer
     * @returns -- Selected printer
     */
    @FXML
    private Printer selectPrinter(){
        return printerChooser.getValue();
    }
}