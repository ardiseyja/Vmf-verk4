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
import java.util.Random;

public class UpphafsController {

    //Array fyrir liti í fellivalmynd, litir á peðum.
    private static final String[] litir = {"Gulur", "Rauður", "Grænn", "Blár", "Fjólublár", "Appelsínugulur"};

    @FXML
    private Button fxByrja;

    @FXML Button fxHverByrjar;

    @FXML
    private ComboBox<String> fxVeljaLit;

    private String selectedString;

    /**
     * Breytur til að kasta upp á hver á að byrja.
     */
    private int byrja;
    private static final int MAX = 2;
    private final Random randomNum = new Random();

    /**
     * Ræsa upphafsskjáinn.
     */
    public void initialize() {
        frumstillaLiti();
        fxHverByrjar.disableProperty().bind(fxVeljaLit.getSelectionModel().selectedItemProperty().isNull());
        fxByrja.setDisable(true);
    }

    /**
     * Setja liti í fellivalmynd.
     */
    private void frumstillaLiti() {
        ObservableList<String> litirHeiti = FXCollections.observableArrayList(litir);
        fxVeljaLit.setItems(litirHeiti);
    }

    /**
     * Ýta á Byrja hnapp á upphafsskjá, skipta yfir í ludo skjá og senda gögn yfir í ludo controller.
     * @param event
     * @throws IOException
     */
    @FXML
    private void onByrja(ActionEvent event) throws IOException {
        selectedString = fxVeljaLit.getSelectionModel().getSelectedItem();

        if(selectedString == null) {
            System.out.println("Enginn litur valinn");
        }

        ViewSwitcher.switchTo(View.LUDO, false, selectedString, byrja);
    }

    /**
     * Ýta á Hver Byrjar hnapp til að kasta upp á hver á að byrjar.
     * @param event
     */
    @FXML
    private void onHverByrjar(ActionEvent event){
        this.byrja = randomNum.nextInt(2);
        String[] hverByrjarTexti = {fxVeljaLit.getSelectionModel().getSelectedItem() + " byrjar", "Svartur byrjar"};
        fxHverByrjar.setText(hverByrjarTexti[byrja]);
        fxHverByrjar.disableProperty().unbind();
        fxHverByrjar.setDisable(true);
        fxByrja.setDisable(false);
    }

}
