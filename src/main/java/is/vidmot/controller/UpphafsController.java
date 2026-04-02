package is.vidmot.controller;

import is.vidmot.switcher.View;
import is.vidmot.switcher.ViewSwitcher;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.io.IOException;
import java.util.Random;

public class UpphafsController {

    private static final String[] litir = {"Gulur", "Rauður", "Grænn", "Blár", "Fjólublár", "Appelsínugulur"};

    @FXML
    private Button fxByrja;

    @FXML Button fxHverByrjar;

    @FXML
    private ComboBox<String> fxVeljaLit;

    public String selectedString;

    private static final int MAX = 2;
    private final IntegerProperty byrjaLeikmadur = new SimpleIntegerProperty(MAX);
    private final Random randomNum = new Random();

    public void initialize() {
        frumstillaLiti();
        fxHverByrjar.disableProperty().bind(fxVeljaLit.getSelectionModel().selectedItemProperty().isNull());
        fxByrja.setDisable(true);
    }

    private void frumstillaLiti() {
        ObservableList<String> litirHeiti = FXCollections.observableArrayList(litir);
        fxVeljaLit.setItems(litirHeiti);
    }


    @FXML
    private void onByrja(ActionEvent event) throws IOException {
        selectedString = fxVeljaLit.getSelectionModel().getSelectedItem();

        if(selectedString == null) {
            System.out.println("Enginn litur valinn");
        }

        ViewSwitcher.switchTo(View.LUDO, false, selectedString);
    }


    @FXML
    private void onHverByrjar(ActionEvent event){
        int byrja = randomNum.nextInt(2);
        String[] hverByrjarTexti = {fxVeljaLit.getSelectionModel().getSelectedItem() + " byrjar", "Svartur byrjar"};
        fxHverByrjar.setText(hverByrjarTexti[byrja]);
        fxHverByrjar.disableProperty().unbind();
        fxHverByrjar.setDisable(true);
        fxByrja.setDisable(false);
    }

}
