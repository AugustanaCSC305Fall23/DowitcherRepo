module edu.augustana {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.opencsv;
    requires com.google.gson;

    opens edu.augustana to javafx.fxml, com.google.gson;
    exports edu.augustana;
}
