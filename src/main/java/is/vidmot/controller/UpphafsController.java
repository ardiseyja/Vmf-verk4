package is.vidmot.controller;

import is.vidmot.switcher.View;
import is.vidmot.switcher.ViewSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.io.IOException;

public class UpphafsController {

    private static final String[] litir = {"Gulur", "Rauður", "Grænn", "Blár", "Fjólublár", "Appelsínugulur"};

    @FXML
    private Button fxByrja;

    @FXML
    private ComboBox<String> fxVeljaLit;

    public String selectedString;

    public void initialize() {
        frumstillaLiti();
    }

    private void frumstillaLiti() {
        ObservableList<String> litirHeiti = FXCollections.observableArrayList(litir);
        fxVeljaLit.setItems(litirHeiti);
    }


    @FXML
    private void onByrja(ActionEvent event) throws IOException {
        selectedString = fxVeljaLit.getSelectionModel().getSelectedItem();

        if(selectedString == null) {
            System.out.println("Ekkert valið");
        }

        ViewSwitcher.switchTo(View.FERD, false, selectedString);
    }


    @FXML
    private void onHverByrjar(ActionEvent event){

    }

}
