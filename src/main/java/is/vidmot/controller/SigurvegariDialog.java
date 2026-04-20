package is.vidmot.controller;

import is.vinnsla.Ludo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Window;
import java.io.IOException;

/******************************************************************************
 *  Nafn    : Árdís Eyja Kjartansdóttir og Kristín Jónsdóttir
 *  T-póstur: aek25@hi.is og krj63@hi.is
 *
 *  Lýsing  : Dialog sem tilkynnir notenda hver vann leikinn.
 *  Inniheldur takka til að hefja nýjan leik.
 *****************************************************************************/
public class SigurvegariDialog {
    private static final String  VANN = " vann!";
    private static final String LEIKLOKID = "Leik lokið";
    private static final String NYRLEIKUR = "Nýr Leikur";

    @FXML private Label fxSigurvegari;

    /**
     * Birtir dialog sem tilkynnir sigurvegara seinasta leiks.
     * Ef ýtt er á "Nýr leikur" takka er leikur settur í upphafsstöðu.
     * @param owner Window
     * @param vinnsla vísun í vinnslu
     * @param sigurvegari nafn sigurvegara
     */
    public static void birtaSigurvegara(Window owner, Ludo vinnsla , String sigurvegari){
        FXMLLoader loader = new FXMLLoader(SigurvegariDialog.class.getResource("/is/vidmot/sigurvegari-dialog.fxml"));
        try {
            DialogPane pane = loader.load();
            SigurvegariDialog controller = loader.getController();
            Dialog<Void> dialog = new Dialog<>();
            dialog.setTitle(LEIKLOKID);
            dialog.setDialogPane(pane);
            dialog.initOwner(owner);
            controller.samstillaTexta(sigurvegari);
            Button nyrLeikur = (Button) pane.lookupButton(ButtonType.OK);
            nyrLeikur.setText(NYRLEIKUR);
            nyrLeikur.setOnAction(e-> vinnsla.nyrLeikur());
            nyrLeikur.getStyleClass().add("takki");

            dialog.showAndWait();
        }
        catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Uppfærir skilaboð, tilkynnir sigurvegara
     * @param sigurvegari nafn leikmanns
     */
    private void samstillaTexta(String sigurvegari){
        fxSigurvegari.setText(sigurvegari + VANN);
    }
}
