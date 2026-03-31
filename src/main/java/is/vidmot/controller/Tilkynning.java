package is.vidmot.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Window;

/**
 * Klasi fyrir alert sem tilkynnir
 * notenda að leikmaður hafi verið sendur aftur á byrjunarreit
 */
public class Tilkynning {
    public static final String TILBAKA = " fer aftur á byrjunarreit";
    public static final String BYRJUNARREITUR = "Aftur á byrjunarreit";
    private static final String ILAGI ="Í lagi";

    /**
     * Birtir Alert dialog þegar leikmaður er sendur aftur á byrjunarreit
     * @param owner window
     * @param leikmadur nafn leikmanns
     */
    public static void birtaTilkynningu(Window owner, String leikmadur){
        System.out.println("kallað á dialog");
        ButtonType ok = new ButtonType(ILAGI, ButtonBar.ButtonData.OK_DONE);
        Alert tilkynning = new Alert(Alert.AlertType.NONE, leikmadur + TILBAKA, ok);
        tilkynning.setTitle(BYRJUNARREITUR);
        tilkynning.initOwner(owner);
        tilkynning.showAndWait();
    }
}
