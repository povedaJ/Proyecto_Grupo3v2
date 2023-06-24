module ucr.proyecto {
    requires javafx.controls;
    requires javafx.fxml;
    requires mail;
    requires java.desktop;
    requires pdfbox.app;


    opens ucr.proyecto to javafx.fxml;
    exports ucr.proyecto;
    exports controller;
    opens controller to javafx.fxml;
}