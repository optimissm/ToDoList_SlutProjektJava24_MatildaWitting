module org.example.demolist {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;

    requires com.dlsc.formsfx;

    opens org.example.demolist to javafx.fxml;
    exports org.example.demolist;
}