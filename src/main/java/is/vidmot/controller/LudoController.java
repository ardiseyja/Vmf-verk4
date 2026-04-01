package is.vidmot.controller;

import is.vinnsla.Ludo;
import is.vinnsla.Reitur;
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


public class LudoController implements GognInterface {

    //tilviksbreytur:
    @FXML
    public GridPane fxLeikBord;

    @FXML
    public Button fxTeningur;

    @FXML
    public Label fxStada; //nafn leikmanns sem á að gera

    //stigataflan
    @FXML
    public Label fxTolvaStig;

    @FXML
    public Label fxLeikmadurStig;

    @FXML
    public Label fxLeikmadur;

    public String litur;

    private final Map<Reitur, StackPane> vidmotLeid = new HashMap<>();

    //vinnslan:
    private final Ludo ludo = new Ludo();

    /**
     * Setja gögn, loada binding, setja lit sem var í fellivalmynd. Frumstilling og byrja leikinn.
     * @param f
     * @throws IOException
     */
    public void setGogn(Object f) throws IOException {
        this.litur = f.toString();
        System.out.println("set f " + f);
        System.out.println("set litur " + litur);

        buaTilLeid();
        bindaTening();
        bindaReiti();
        bindaSkilabod();
    }

    //Handlerar:
    /**
     * Handler fyrir að kasta tening
     * @param actionEvent ónotað
     */
    public void onTeningur(ActionEvent actionEvent) {
        ludo.leikaLeik();
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

            if(i%2==0 && i!=0){
                vidmotsReitur.getStyleClass().add("reitur");
            }

            byrjunEndir(vidmotsReitur, reitur);
            aukaBorder(vidmotsReitur,reitur);

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
     * Bætir þykkari útlínum á valda reiti til að gera leið skýrari
     * @param vidmotsReitur
     * @param reitur
     */
    private void aukaBorder(StackPane vidmotsReitur, Reitur reitur){
        int dalkur = reitur.getDalkurProp().intValue();
        int rod = reitur.getRodProp().intValue();

        if(rod==4 && dalkur==0){
            vidmotsReitur.getStyleClass().add("top");
        }
        if(rod==4 && (dalkur==1 || dalkur==2 || dalkur==3)){
            vidmotsReitur.getStyleClass().add("top_bottom");
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
     * Sæka lit sem var valinn í fellivalmynd og  skila css string til að binda við peð.
     * @return
     */
    private String litLeikmanns() {
        if(litur == "Gulur") {
            return "leikmadurGulur";
        }
        if(litur == "Rauður") {
            return "leikmadurRaudur";
        }
        if(litur == "Grænn") {
            return "leikmadurGraenn";
        }
        if(litur == "Blár") {
            return "leikmadurBlar";
        }
        if(litur == "Fjólublár") {
            return "leikmadurFjolublar";
        }
        if(litur == "Appelsínugulur") {
            return "leikmadurAppelsinu";
        }
        return null;
    }

    /**
     * Fyrir hvern leikmann, uppfærir mynd á viðmótsreit í samræmi við nýjan reit leikmanns sem
     * er vaktaður í gegnum index leikmanns
     */
    private void bindaReiti(){
        String[] leikmadurStill = {litLeikmanns(), "leikmadurSvartur"};

        //Peð leikmanns:
        ludo.getLeikmadur(0).getReiturProp().addListener((obs, gamlaGildi, nyttGildi) -> {
            vidmotLeid.get(ludo.getReitur(gamlaGildi.intValue())).getStyleClass().remove(leikmadurStill[0]);
            vidmotLeid.get(ludo.getReitur(nyttGildi.intValue())).getStyleClass().add(leikmadurStill[0]);
        });

        //Peð tölvu:
        ludo.getLeikmadur(1).getReiturProp().addListener((obs, gamlaGildi, nyttGildi) -> {
            vidmotLeid.get(ludo.getReitur(gamlaGildi.intValue())).getStyleClass().remove(leikmadurStill[1]);
            vidmotLeid.get(ludo.getReitur(nyttGildi.intValue())).getStyleClass().add(leikmadurStill[1]);
        });
    }

    /**
     * Bindur Label við stöðu leiksins frá vinnslunni,
     * Skilaboð um hver á leik
     */
    private void bindaSkilabod(){
        fxStada.textProperty().bind(ludo.naestiLeikmadurProp());
    }

}
