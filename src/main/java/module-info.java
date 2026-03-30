
module LudoJavaFX {
    requires javafx.fxml;
    requires javafx.controls;
    opens is.vidmot to javafx.fxml;
    opens is.vidmot.controller to javafx.fxml;

    exports is.vidmot;
    exports is.vidmot.controller;
    exports is.vinnsla;
}