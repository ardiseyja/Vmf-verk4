package is.vidmot.controller;

import is.vidmot.switcher.View;
import is.vidmot.switcher.ViewSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import java.io.IOException;
import java.util.Random;

/******************************************************************************
 *  Nafn    : Árdís Eyja Kjartansdóttir og Kristín Jónsdóttir
 *  T-póstur: aek25@hi.is og krj63@hi.is
 *
 *  Lýsing  : Controller fyrir upphafsskjá forritsins.
 *  Tekur við upplýsingum um leik og vinnur úr þeim áður en leikur er hafinn.
 *****************************************************************************/
public class UpphafsController {
    private static final String[] litir = {"Gulur", "Rauður", "Grænn", "Blár", "Fjólublár", "Appelsínugulur"};

    @FXML private Button fxByrja;
    @FXML private Button fxHverByrjar;
    @FXML private ComboBox<String> fxVeljaLit;
    @FXML private Pane fxUpphafsBakgrunnur;

    private String selectedString;
    private int byrja;
    private static final int MAX = 2;
    private final Random randomNum = new Random();

    /**
     * Frumstilling.
     * Ræsir upphafsskjáinn.
     */
    public void initialize() {
        frumstillaLiti();
        fxHverByrjar.disableProperty().bind(fxVeljaLit.getSelectionModel().selectedItemProperty().isNull());
        fxByrja.setDisable(true);
        fxVeljaLit.getSelectionModel().selectedItemProperty()
                .addListener((obs, gamla, nyja) -> {
                    fxUpphafsBakgrunnur.getStyleClass().remove(bakLitir(gamla));
                    fxUpphafsBakgrunnur.getStyleClass().add(bakLitir(nyja));
                });
    }

    /**
     * Setur liti inn í fellivalmynd.
     */
    private void frumstillaLiti() {
        ObservableList<String> litirHeiti = FXCollections.observableArrayList(litir);
        fxVeljaLit.setItems(litirHeiti);
    }

    /**
     * Tekur inn valinn lit úr fellivalmynd og
     * skilar css klasa fyrir bakgrunnslit
     * @param litur nafn leikmanns
     * @return Strengur, nafn css klasa
     */
    private String bakLitir(String litur) {
        if(litur == "Gulur") { return "uGulur"; }
        if(litur == "Rauður") { return "uRaudur"; }
        if(litur == "Grænn") { return "uGraenn"; }
        if(litur == "Blár") { return "uBlar"; }
        if(litur == "Fjólublár") { return "uFjolublar"; }
        if(litur == "Appelsínugulur") { return "uAppelsina"; }
        return null;
    }

    /**
     * Handler fyrir byrja hnapp.
     * Skiptir yfir á leikborð lúdó og sendir gögn yfir til ludo controller.
     * @param event
     * @throws IOException
     */
    @FXML
    private void onByrja(ActionEvent event) throws IOException {
        selectedString = fxVeljaLit.getSelectionModel().getSelectedItem();
        if(selectedString == null) { System.out.println("Enginn litur valinn"); }
        ViewSwitcher.switchTo(View.LUDO, false, selectedString, byrja);
    }

    /**
     * Handler fyrir hver byrjar hnapp.
     * Kastar upp á hvor leikmaðurinn byrjar.
     * Birtir lit þess leikmanns sem byrjar
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
        if(byrja == 0) {
            fxVeljaLit.getSelectionModel().selectedItemProperty()
                    .addListener((obs, gamla, nyja) -> {
                        fxHverByrjar.setText(nyja + " byrjar");
                    });
        }
    }
}
