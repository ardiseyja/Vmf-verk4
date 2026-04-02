package is.vidmot.controller;

import is.vinnsla.Ludo;
import is.vinnsla.Reitur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Controller fyrir leikborðið
 */
public class LudoController implements GognInterface {

    //tilviksbreytur:
    @FXML
    public GridPane fxLeikBord;

    @FXML
    public Button fxTeningur;

    @FXML
    public Label fxStada; //nafn leikmanns sem á að gera

    @FXML
    public VBox fxUpplysingar;  //Vbox í neðra hægra horni borðisns

    //Stigataflan:
    @FXML
    public Label fxTolvaStig;

    @FXML
    public Label fxLeikmadurStig;

    @FXML
    public Label fxLeikmadur;

    public String litur;

    private final Map<Reitur, StackPane> vidmotLeid = new HashMap<>();

    //Dialogar:
    private final Tilkynning tilkynning = new Tilkynning();
    private final SigurvegariDialog sigurvegariDialog = new SigurvegariDialog();

    //vinnslan:
    private Ludo ludo = new Ludo(0);

    /**
     * Setja gögn, loada binding, setja lit sem var í fellivalmynd og byrjunar leikmann. Frumstilling og byrja leikinn.
     * @param f
     * @throws IOException
     */
    public void setGogn(Object f, int i) throws IOException {
        ludo = new Ludo(i);
        this.litur = f.toString();
        fxLeikmadur.setText(litur);
        ludo.setLeikmadur1(litur);

        buaTilLeid();
        bindaTening();
        bindaReiti();
        bindaSkilabod();
        bindaStig();
        bindaLit();
    }


    //Handlerar:

    /**
     * Handler fyrir tening
     * Þegar teningi er kastað færist leikmaður skv teningi
     * Ef leikmaður lendir á sama reit
     * og andstæðingur er birtur upplýsinga dialog
     * Þegar leik er lokið birtist viðeigandi dialog
     * @param actionEvent
     */
    public void onTeningur(ActionEvent actionEvent) {
        ludo.leikaLeik();
        if(ludo.getSamiReitur().get()) {
            tilkynning.birtaTilkynningu(((Node) actionEvent.getSource()).getScene().getWindow(), ludo.getLeikmadur().getNafn(), ludo.getAndstaedingur().getNafn());
        }
        if(ludo.erLokid().get()){
            sigurvegariDialog.birtaSigurvegara(((Node) actionEvent.getSource()).getScene().getWindow(), ludo, ludo.getLeikmadur().getNafn());
            ludo.getStigatafla().uppfaeraStig(ludo.getLeikmadur());
        }
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

    /**
     * Bindir Label í stigatöflu við stig leikmanna frá vinnslunni
     */
    private void bindaStig(){
        fxTolvaStig.textProperty().bind(ludo.getStigatafla().getStigTolvu());
        fxLeikmadurStig.textProperty().bind(ludo.getStigatafla().getStigLeikmanns());
    }

    /**
     * Hlustar á hvaða leikmaður á næsta leik
     * uppfærir bakgrunnslit skilaboða
     */
    private void bindaLit(){
        ludo.naestiLeikmadurProp().addListener((obs, gamlaGildi, nyttGildi)->{
            fxUpplysingar.getStyleClass().remove(bakgrunnslitur(gamlaGildi));
            fxUpplysingar.getStyleClass().add(bakgrunnslitur(nyttGildi));
        });
    }

    /**
     * Tekur inn nafn næsta leikmanns og
     * skilar css klasa fyrir bakgrunnslit skilaboða
     * @param litur nafn leikmanns
     * @return css klasa nafn
     */
    private String bakgrunnslitur(String litur){
        return switch (litur) {
            case "Gulur" -> "gulur";
            case "Rauður" -> "raudur";
            case "Grænn" -> "graenn";
            case "Blár" -> "blar";
            case "Fjólublár" -> "fjolublar";
            case "Appelsínugulur" -> "appelsina";
            default -> "svartur";
        };
    }
}
