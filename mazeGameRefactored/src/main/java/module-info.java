module com.example.mazegamerefactored {
    requires javafx.controls;
    requires javafx.fxml;

    requires javafx.graphics;
    requires javafx.base;
    requires java.sql;


    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.mazegamerefactored to javafx.fxml;
    exports com.example.mazegamerefactored;
}