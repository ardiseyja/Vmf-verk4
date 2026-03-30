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
    public final String url = "CSS/myndir/"; //ef notað verpur imageview fyrir leikmenn í stað stackpane

    //tilviksbreytur:
    @FXML
    public GridPane fxLeikBord;

    @FXML
    public Button fxTeningur;

    @FXML
    public Label fxStada; //nafn leikmanns sem á að gera

    //Nýtt:
    //stigatafla:
    @FXML
    public Label fxTolvaStig;

    @FXML
    public Label fxLeikmadurStig;

    @FXML
    public Label fxLeikmadur;

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
     * Frumstilling á viðmótshlutum og byrjar leikinn
     */
    public void initialize() throws IOException {
        buaTilLeid();
        bindaTening();
        bindaReiti();
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
        int i = 0;
        for(Reitur reitur: leid){
            StackPane vidmotsReitur = loadReitur();
            vidmotsReitur.getStyleClass().add("border");

            if(i%2==0 && i!=0){ //bæta við bakgrunnslit á annanhvern reit
                vidmotsReitur.getStyleClass().add("reitur");
            }

            byrjunEndir(vidmotsReitur, reitur);

            fxLeikBord.add(vidmotsReitur, reitur.getDalkurProp().intValue(), reitur.getRodProp().intValue());
            vidmotLeid.put(reitur, vidmotsReitur);
            i++;
        }
    }

    /**
     * Setur bakgrunnsmynd á byrjunar og endareit
     * @param vidmotsReitur StackPane
     * @param reitur úr vinnslu
     */
    private void byrjunEndir(StackPane vidmotsReitur, Reitur reitur){
        int dalkur = reitur.getDalkurProp().intValue();
        int rod = reitur.getRodProp().intValue();
        if(rod==3 && dalkur==0){
            vidmotsReitur.getStyleClass().add("byrjun");
        }
        if(rod==4 && dalkur==4){
            vidmotsReitur.getStyleClass().add("mark");
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

    private void bindaLeikmenn(){
        //finna gamla og nýja reit og uppfæra imageview eftir því
    }

    /**
     * Bindur Label við stöðu leiksins frá vinnslunni,
     * Skilaboð um hver á leik
     */
    private void bindaSkilabod(){
        fxStada.textProperty().bind(ludo.naestiLeikmadurProp());
    }
}
