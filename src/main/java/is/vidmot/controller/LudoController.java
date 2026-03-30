package is.vidmot.controller;

import is.vinnsla.Ludo;
import is.vinnsla.Reitur;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LudoController {
    //fastar:
    public static final String LEIK_LOKID = "Leik lokið, Leikmaður ";
    public static final String VANN = " vann!";
    public static final String LEIKUR_I_GANGI= "Leikur í gangi ";
    public static final String GERIR = " gerir";
    public static final String KASTA = "Kastaðu teningnum til að gera";
    public static final String NYR_LEIKUR = "Ýttu á 'Nýr leikur' til að spila aftur";

    //tilviksbreytur:
    @FXML
    public GridPane fxLeikBord;

    @FXML
    public Button fxTeningur;

    @FXML
    public Label fxStada;

    @FXML
    public Label fxUpplysingar;

    @FXML
    public Button fxNyrLeikur;

    private final Map<Reitur, StackPane> vidmotLeid = new HashMap<>();

    //vinnslan:
    private final Ludo ludo = new Ludo();


    //Handlerar:
    /**
     * Handler fyrir að kasta tening
     * @param actionEvent ónotað
     */
    public void onTeningur(ActionEvent actionEvent) {
        ludo.leikaLeik();
    }

    /**
     * Handler nýr leikur takki
     * @param actionEvent ónotað
     */
    public void onNyrLeikur(ActionEvent actionEvent) {
        ludo.nyrLeikur();
    }

    /**
     * Frumstilling á viðmótshlutum og byrjar leikinn
     */
    public void initialize() throws IOException {
        buaTilLeid();
        bindaTening();
        bindaReiti();
        bindaHnappa();
        bindaSkilabod();
    }


    //Hjálparaðferðir:

    /**
     * Býr til leiðina á lúdó borðinu
     * Leiðin er fengin úr vinnslunni (módelinu). Viðmótsreitir (s) eru búnir til fyrir
     * hvern reit (r) og settur á borðið í viðmótinu.
     * (r, s) er bætt í HashMap vidmotLeid
     * @throws IOException ef viðmótsreiturinn er lesinn (load) úr .fxml skrá, annars óþarfi
     */
    private void buaTilLeid() throws IOException{
        List<Reitur> leid = ludo.getLeid();

        for(Reitur reitur: leid){
            StackPane vidmotsReitur = loadReitur();
            vidmotsReitur.getStyleClass().add("border"); //útlínur settar á reiti
            fxLeikBord.add(vidmotsReitur, reitur.getDalkurProp().intValue(), reitur.getRodProp().intValue());
            vidmotLeid.put(reitur, vidmotsReitur);
        }
    }

    /**
     * Hjálparaðferð sem býr til nýtt StackPane
     * @return StackPane viðmótsreit
     * @throws IOException
     */
    private StackPane loadReitur() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(LudoController.class.getResource("/is/vidmot/reitur-view.fxml"));
        StackPane vidmotsReitur = fxmlLoader.load();
        return vidmotsReitur;
    }

    /**
     * Bindir teningamyndir við teninginn
     */
    private void bindaTening(){
        String[] teningaMyndir = {"one", "two", "three", "four", "five", "six"};
        ludo.getTeningur().getTeningurProp().addListener((obs, gamlaGildi, nyttGildi) ->{
            fxTeningur.getStyleClass().remove(teningaMyndir[gamlaGildi.intValue() - 1]);
            fxTeningur.getStyleClass().add(teningaMyndir[nyttGildi.intValue() - 1]);
        });
    }

    /**
     * Fyrir hvern leikmann, uppfærir mynd á viðmótsreit í samræmi við nýjan reit leikmanns sem
     * er vaktaður í gegnum index leikmanns
     */
    private void bindaReiti(){
        String[] leikmadurStill = {"leikmadurRaudur", "leikmadurBlar"};

        //Rautt peð:
        ludo.getLeikmadur(0).getReiturProp().addListener((obs, gamlaGildi, nyttGildi) -> {
            vidmotLeid.get(ludo.getReitur(gamlaGildi.intValue())).getStyleClass().remove(leikmadurStill[0]);
            vidmotLeid.get(ludo.getReitur(nyttGildi.intValue())).getStyleClass().add(leikmadurStill[0]);
        });

        //Blátt peð:
        ludo.getLeikmadur(1).getReiturProp().addListener((obs, gamlaGildi, nyttGildi) -> {
            vidmotLeid.get(ludo.getReitur(gamlaGildi.intValue())).getStyleClass().remove(leikmadurStill[1]);
            vidmotLeid.get(ludo.getReitur(nyttGildi.intValue())).getStyleClass().add(leikmadurStill[1]);
        });
    }

    /**
     * Bindur hnappana við ástandið á leiknum,
     * ef leikur er í gangi er nýr leikur takkinn óvirkur,
     * ef leik er lokið er teningurinn óvirkur
     */
    private void bindaHnappa(){
        fxNyrLeikur.disableProperty().bind(ludo.iGangi());
        fxTeningur.disableProperty().bind(ludo.erLokid());
    }

    /**
     * Bindur Label við stöðu leiksins frá vinnslunni,
     * birtir skilaboð í samræmi við leikinn
     * Skilaboð um hver á leik, sigurvegari,
     * Leiðbeiningar: kasta tening eða ýta á nýr leikur
     */
    private void bindaSkilabod(){
        fxStada.textProperty().bind(
                Bindings.when(ludo.erLokid())
                        .then(Bindings.concat(LEIK_LOKID, ludo.naestiLeikmadurProp(), VANN))
                        .otherwise(Bindings.concat(LEIKUR_I_GANGI, ludo.naestiLeikmadurProp(), GERIR)));

        fxUpplysingar.textProperty().bind(
                Bindings.when(ludo.iGangi())
                        .then(KASTA)
                        .otherwise(NYR_LEIKUR));
    }
}
