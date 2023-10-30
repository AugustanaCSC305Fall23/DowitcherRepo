package edu.augustana;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.print.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import javafx.scene.control.*;



public class Printers {

    public Printers(ScrollPane scrollPane, Stage stage){

    }

    public static void printLessonPlan(ScrollPane scrollpane){
        Group root = new Group();
        Scene scene = new Scene(root, 450, 150, Color.WHITE);
        App.stage.setScene(scene);

        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(5));
        gridpane.setHgap(5);
        gridpane.setVgap(5);
        Label printerLabel = new Label("Printer: ");
        gridpane.add(printerLabel, 0, 1);
        final Printer selectedPrinter = Printer.getDefaultPrinter();
        // printer pick list
        ChoiceBox printerChooser = new ChoiceBox(FXCollections.observableArrayList(
                Printer.getAllPrinters())
        );
        // Select the first option by default
        printerChooser.getSelectionModel().selectFirst();

        gridpane.add(printerChooser, 1, 1);
        Button printButton = new Button("Print");
        printButton.setOnAction((ActionEvent event) -> {
            print(scrollpane, selectedPrinter);
        });
        gridpane.add(printButton, 0, 3);
        root.getChildren().add(gridpane);


    }

public static void print(ScrollPane scrollpane, Printer printer) {
    PrinterJob printerJob = PrinterJob.createPrinterJob();
    PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, Printer.MarginType.HARDWARE_MINIMUM);
    printerJob.setPrinter(printer);

    if (printerJob != null) {
        boolean success = printerJob.showPrintDialog(App.stage);

        double width = scrollpane.getWidth();
        double height = scrollpane.getHeight();

        PrintResolution resolution = printerJob.getJobSettings().getPrintResolution();

        width /= resolution.getFeedResolution();
        height /= resolution.getCrossFeedResolution();

        double scaleX = pageLayout.getPrintableWidth()/width/450;
        double scaleY = pageLayout.getPrintableHeight()/height/450;

        Scale scale = new Scale(scaleX, scaleY);
        scrollpane.getTransforms().add(scale);
        scrollpane.setFitToWidth(true);
        if (success) {
            printerJob.getJobSettings().setPageLayout(pageLayout);
            printerJob.printPage(scrollpane);
            printerJob.endJob();

        }
    }
}
}
