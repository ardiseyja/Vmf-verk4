package is.vidmot.controller;

import is.vidmot.switcher.View;
import is.vidmot.switcher.ViewSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.util.Objects;

public class UpphafsController {

    private static final String[] litir = {"Gulur", "Rauður", "Grænn", "Blár", "Fjólublár", "Appelsínugulur"};

    @FXML
    private Button fxByrja;

    @FXML
    private ComboBox<String> fxVeljaLit;

    ComboBox<String> veljaLit = new ComboBox<>();

    String selectedString = "";

    public void initialize() {
        frumstillaLiti();
        //String litur = fxVeljaLit.getValue();

        //nota líkt þessu til að nota litinn sem er valinn til að setja lit á peð
        // hlustum á breytingu á valinu og uppfærum myndina af dýrinu
//        fxVeljaLit.getSelectionModel().selectedItemProperty()
//                .addListener((obs, gamla, nyja) -> {
//                    fxDyramynd.setImage(dyramyndir.get(nyja));
//                });
    }

    private void frumstillaLiti() {
        ObservableList<String> litirHeiti = FXCollections.observableArrayList(litir);
        fxVeljaLit.setItems(litirHeiti);
    }

    @FXML
    private String onByrja(ActionEvent event) {
        ViewSwitcher.switchTo(View.FERD, false, null);
        selectedString = fxVeljaLit.getSelectionModel().getSelectedItem();
        if (selectedString != null) {
            System.out.println("Selected string: " + selectedString);
        }
        return selectedString;
    }

    @FXML
    private void onHverByrjar(ActionEvent event){

    }

    public String valinnLitur() {
        //String litur = fxVeljaLit.getValue();

//        if(Objects.equals(litur, "Gulur")) {
//            return "leikmadurGulur";
//        }
//        String x = "";
//                fxVeljaLit.getSelectionModel().selectedItemProperty()
//                .addListener((obs, gamla, nyja) -> {
//                   return nyja;
//                });
//        String litur = veljaLit.getValue();
//        System.out.println(litur);
//        String selectedString = fxVeljaLit.getSelectionModel().getSelectedItem();
//        if (selectedString != null) {
//            System.out.println("Selected string: " + selectedString);
//        }
        selectedString = fxVeljaLit.getSelectionModel().getSelectedItem();
        
        System.out.println(selectedString);
        if(Objects.equals(selectedString, "Gulur")) {
            return "leikmadurGulur";
        }
        return "leikmadurRaudur";
    }
}
