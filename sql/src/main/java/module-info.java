module org.example.sql {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.sql to javafx.fxml;
    exports org.example.sql;
}