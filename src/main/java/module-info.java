module com.example.project.Logic.MainController {
    requires javafx.controls;
    requires javafx.fxml;
            
            requires com.dlsc.formsfx;
    requires hibernate.core;
    requires hibernate.jpa;

    opens com.example.project.Logic.MainController to javafx.fxml;
    exports com.example.project.Logic.MainController;
    exports com.example.project.Controllers;
    opens com.example.project.Controllers to javafx.fxml;
}