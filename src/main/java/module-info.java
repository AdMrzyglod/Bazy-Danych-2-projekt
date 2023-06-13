module com.example.project.Logic.MainController {
    requires javafx.controls;
    requires javafx.fxml;
            
            requires com.dlsc.formsfx;
    requires java.naming;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires java.persistence;

    opens com.example.project.Logic.MainController to javafx.fxml;
    exports com.example.project.Logic.MainController;
    exports com.example.project.Controllers;
    opens com.example.project.Controllers to javafx.fxml;
    opens com.example.project to org.hibernate.orm.core;
    opens com.example.project.Providers to org.hibernate.orm.core;
    opens com.example.project.Logic to org.hibernate.orm.core;
    opens com.example.project.Logic.DatabaseClasses to org.hibernate.orm.core;
}