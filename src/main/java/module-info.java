module org.example.proyectofx.app {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.proyectofx.app to javafx.fxml;
    exports org.example.proyectofx.app;
}