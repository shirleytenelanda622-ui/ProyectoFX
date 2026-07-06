module org.example.proyectofx.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    opens org.example.proyectofx.model to javafx.base;
    opens org.example.proyectofx.app to javafx.graphics;
    opens org.example.proyectofx.controller to javafx.fxml;

}