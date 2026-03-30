package is.vidmot.controller;

import is.vidmot.switcher.View;
import is.vidmot.switcher.ViewSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class UpphafsController {

    @FXML
    private Button fxByrja;

    public void initialize() {

    }

    @FXML
    private void onByrja(ActionEvent event) {
        ViewSwitcher.switchTo(View.FERD, false, null);
    }
}
