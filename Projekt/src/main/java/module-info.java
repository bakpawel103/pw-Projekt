module com.example.projekt {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;


    opens com.example.projekt to javafx.fxml;
    opens com.example.projekt.entities to javafx.fxml;

    exports com.example.projekt;
    exports com.example.projekt.entities;
}