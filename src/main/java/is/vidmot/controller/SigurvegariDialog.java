package is.vidmot.controller;

import is.vinnsla.Ludo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Window;

import java.io.IOException;

public class SigurvegariDialog {
    private static final String  VANN = " vann!";

    @FXML
    private Label fxSigurvegari;

    public static void birtaSigurvegara(Window owner, Ludo vinnsla , String sigurvegari){
        FXMLLoader loader = new FXMLLoader(SigurvegariDialog.class.getResource("/is/vidmot/sigurvegari-dialog.fxml"));
        try {
            DialogPane pane = loader.load();
            SigurvegariDialog controller = loader.getController();
            Dialog<Void> dialog = new Dialog<>();
            dialog.setTitle("Leik lokið");
            dialog.setDialogPane(pane);
            dialog.initOwner(owner);
            controller.samstillaTexta(sigurvegari);
            Button nyrLeikur = (Button) pane.lookupButton(ButtonType.OK);
            nyrLeikur.setText("Nýr Leikur");
            nyrLeikur.setOnAction(e-> vinnsla.nyrLeikur());
            nyrLeikur.getStyleClass().add("takki");

            dialog.showAndWait();
        }
        catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void samstillaTexta(String sigurvegari){
        fxSigurvegari.setText(sigurvegari + VANN);
    }
}
