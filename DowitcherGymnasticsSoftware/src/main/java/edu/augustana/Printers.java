package edu.augustana;

import javafx.print.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.transform.Scale;

public class Printers {
    /**
     *
     * @param scrollpane
     * @param printer
     */
    public static void print(ScrollPane scrollpane, Printer printer, int eventCount) {
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, Printer.MarginType.HARDWARE_MINIMUM);
        printerJob.setPrinter(printer);

        boolean success = printerJob.showPrintDialog(App.stage);

        Scale scale = scale(scrollpane, printerJob, pageLayout);
        scrollpane.getTransforms().add(scale);
        double contentHeight = scrollpane.getContent().getLayoutBounds().getHeight();

        double startY = 0;
        if (eventCount <= 4){
            printerJob.getJobSettings().setPageLayout(pageLayout);
            printerJob.printPage(scrollpane);
        }else{
            while (startY < contentHeight) {
                double endY = Math.min(startY + 1600, contentHeight);
                scrollpane.setVvalue(startY / contentHeight);
                printerJob.getJobSettings().setPageLayout(pageLayout);
                if (printerJob.printPage(scrollpane)) {
                    startY = endY;
                } else {
                    break;
                }
            }
        }
        printerJob.endJob();
        scrollpane.getTransforms().remove(scale);
    }

    /**
     *
     * @param scrollpane
     * @param printerJob
     * @param pageLayout
     * @return
     */
    private static Scale scale(ScrollPane scrollpane, PrinterJob printerJob, PageLayout pageLayout){
        double width = scrollpane.getPrefWidth();
        double height = scrollpane.getPrefHeight();

        PrintResolution resolution = printerJob.getJobSettings().getPrintResolution();

        width /= resolution.getFeedResolution();
        height /= resolution.getCrossFeedResolution();

        double scaleX = pageLayout.getPrintableWidth() / width / resolution.getFeedResolution();
        double scaleY = pageLayout.getPrintableHeight() / height / resolution.getCrossFeedResolution();

        return new Scale(scaleX, scaleY);
    }
}
