module edu.augustana {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.opencsv;
    requires com.google.gson;
    requires atlantafx.base;

    opens edu.augustana to javafx.fxml, com.google.gson;
    exports edu.augustana;
    exports edu.augustana.datastructure;
    opens edu.augustana.datastructure to com.google.gson, javafx.fxml;
    exports edu.augustana.controllers;
    opens edu.augustana.controllers to com.google.gson, javafx.fxml;
    exports edu.augustana.search;
    opens edu.augustana.search to com.google.gson, javafx.fxml;
    exports edu.augustana.printing;
    opens edu.augustana.printing to com.google.gson, javafx.fxml;
}
