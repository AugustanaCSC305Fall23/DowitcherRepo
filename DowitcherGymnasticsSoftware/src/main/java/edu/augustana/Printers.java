package edu.augustana;

import javafx.print.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.transform.Scale;

public class Printers {

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

            double scaleX = pageLayout.getPrintableWidth() / width / resolution.getFeedResolution();
            double scaleY = pageLayout.getPrintableHeight() / height / resolution.getCrossFeedResolution();

            Scale scale = new Scale(scaleX, scaleY);

            scrollpane.getTransforms().add(scale);


            if (success) {
                printerJob.getJobSettings().setPageLayout(pageLayout);
                
                printerJob.printPage(scrollpane);
                printerJob.endJob();

            }
            scrollpane.getTransforms().remove(scale);
        }
    }
}
