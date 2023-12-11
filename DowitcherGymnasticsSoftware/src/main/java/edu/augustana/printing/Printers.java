package edu.augustana.printing;

import edu.augustana.GymnasticsPlannerApp;
import javafx.print.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.transform.Scale;

public class Printers {
    /** Print Lesson Plan from a ScrollPane to a piece of paper
     *
     * @param scrollpane -- ScrollPane that contains Lesson Plan
     * @param printer -- Selected printer that will carry out print job
     * @param eventCount -- The number of events in the Lesson Plan
     */
    public static void print(ScrollPane scrollpane, Printer printer, int eventCount) {
        //Create Printer Job and set the Page Layout as well as the printer
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, Printer.MarginType.HARDWARE_MINIMUM);
        printerJob.setPrinter(printer);

        boolean success = printerJob.showPrintDialog(GymnasticsPlannerApp.stage);
        //Scale ScrollPane to fit on paper
        Scale scale = scale(scrollpane, printerJob, pageLayout);
        scrollpane.getTransforms().add(scale);

        double contentHeight = scrollpane.getContent().getLayoutBounds().getHeight();
        double startY = 0;
        //If the event count is greater than or equal it will display on one page
        if (eventCount <= 4){
            printerJob.getJobSettings().setPageLayout(pageLayout);
            printerJob.printPage(scrollpane);
            //If it is greater than 4 it will display onto multiple pages
        }else{
            while (startY < contentHeight) {
                    //Controls how far down the ScrollPane you move down after each page by setting Vvalue
                    double endY = Math.min(startY + 1600, contentHeight);
                    scrollpane.setVvalue(startY / contentHeight);
                    double visibleHeight = endY - startY;
                    scrollpane.setPrefViewportHeight(visibleHeight);

                    printerJob.getJobSettings().setPageLayout(pageLayout);
                    if (printerJob.printPage(scrollpane)) {
                        startY = endY;
                    } else {
                        break;
                    }

            }

        }
        printerJob.endJob();
        //Removes scale from ScrollPane display
        scrollpane.getTransforms().remove(scale);
    }

    /** Creates a scale to convert the Lesson Plan from the ScrollPane into the right size to be displayed
     * on a piece of paper
     * @param scrollpane -- the scrollpane that contains the Lesson Plan
     * @param printerJob -- the print job that is being executed
     * @param pageLayout -- the page layout of the printer job
     * @return  -- returns the appropriate scale to apply
     */
    private static Scale scale(ScrollPane scrollpane, PrinterJob printerJob, PageLayout pageLayout){
        //Gets the height and width of the ScrollPane
        double width = scrollpane.getPrefWidth();
        double height = scrollpane.getPrefHeight();

        PrintResolution resolution = printerJob.getJobSettings().getPrintResolution();
        //Ensure the content is not altered
        width /= resolution.getFeedResolution();
        height /= resolution.getCrossFeedResolution();

        //Create the scale values for the X and Y
        double scaleX = pageLayout.getPrintableWidth() / width / resolution.getFeedResolution();
        double scaleY = pageLayout.getPrintableHeight() / height / resolution.getCrossFeedResolution();

        return new Scale(scaleX, scaleY);
    }
}
