module edu.augustana {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.opencsv;

    opens edu.augustana to javafx.fxml;
    exports edu.augustana;
}
