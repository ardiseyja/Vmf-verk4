package is.vidmot.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Window;
import java.io.IOException;

/******************************************************************************
 *  Nafn    : Árdís Eyja Kjartansdóttir og Kristín Jónsdóttir
 *  T-póstur: aek25@hi.is og krj63@hi.is
 *
 *  Lýsing  : Dialog sem tilkynnir notenda notenda
 *  að leikmaður hafi verið sendur aftur á byrjunarreit
 *****************************************************************************/
public class Tilkynning {
    private static final String TILBAKA = " fer aftur á byrjunarreit";
    private static final String REITUR = " lenti á reit andstæðings";
    private static final String BYRJUNARREITUR = "Aftur á byrjunarreit";
    private static final String ILAGI ="Í lagi";

    @FXML private Label fxSkilabod;
    @FXML private Label fxTilkynning;

    /**
     * Birtir upplýsinga dialog þegar leikmaður er sendur aftur á byrjunarreit
     * Tilkynnir hvaða leikmaður lenti á reit andstæðings og
     * hvaða leikmaður var sendur aftur á byrjunarreit
     * @param owner window
     * @param leikmadur nafn leikmanns
     * @param andstaedingur nafn andstæðings
     */
    public static void birtaTilkynningu(Window owner, String leikmadur, String andstaedingur){
        FXMLLoader loader = new FXMLLoader(Tilkynning.class.getResource("/is/vidmot/tilkynning.fxml"));
        try {
            DialogPane pane = loader.load();
            Tilkynning controller = loader.getController();
            Dialog<Void> dialog = new Dialog<>();
            dialog.setTitle(BYRJUNARREITUR);
            dialog.setDialogPane(pane);
            dialog.initOwner(owner);
            controller.samstillaTexta(leikmadur, andstaedingur);
            Button takki = (Button) pane.lookupButton(ButtonType.OK);
            takki.setText(ILAGI);
            takki.getStyleClass().add("takki");

            dialog.showAndWait();
        }
        catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Samstillir skilaboð við stöðu leiks
     * Birtir nöfn leikmanna og lýsingu um hver fer aftur á byrjunarreit
     * @param leikmadur nafn leikmanns sem er sendur tilbaka
     * @param andstaedingur nafn andstæðings
     */
    private void samstillaTexta(String leikmadur, String andstaedingur){
        fxSkilabod.setText(andstaedingur + REITUR);
        fxTilkynning.setText((leikmadur + TILBAKA));
    }
}
