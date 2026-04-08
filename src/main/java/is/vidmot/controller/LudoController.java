package is.vidmot.controller;

import is.vinnsla.Leikmadur;
import is.vinnsla.Ludo;
import is.vinnsla.Reitur;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/******************************************************************************
 *  Lýsing  : Controller fyrir leikborðið.
 *  Inniheldur handlera og aðferðir tengdum lúdó leiknum og leikborðinu.
 *  Útfærir GognInterface
 *****************************************************************************/
public class LudoController implements GognInterface {
    @FXML private GridPane fxLeikBord;
    @FXML private Button fxTeningur;
    @FXML private Label fxStada;
    @FXML private VBox fxUpplysingar;
    @FXML private Label fxTolvaStig;
    @FXML private Label fxLeikmadurStig;
    @FXML private Label fxLeikmadur;

    private String litur;
    private final Map<Reitur, StackPane> vidmotLeid = new HashMap<>();
    private final Tilkynning tilkynning = new Tilkynning();
    private final SigurvegariDialog sigurvegariDialog = new SigurvegariDialog();
    private Ludo ludo;
    private PauseTransition pause = new PauseTransition(new Duration(1000));

    /**
     * Setjur gögn,tengir bindingar,
     * fær lit sem valinn var í fellivalmynd og upplýsingar um hvaða leikmaður byrjar.
     * Frumstillir leikborðið og byrja leikinn.
     * @param f litur úr fellivalmynd
     * @throws IOException
     */
    public void setGogn(Object f, int i) throws IOException {
        this.litur = f.toString();
        ludo = new Ludo(i,litur);
        fxLeikmadur.setText(litur);
        buaTilLeid();
        bindaTening();
        bindaReiti();
        bindaSkilabod();
        bindaStig();
        bindaLit();
        if(ludo.erTolva()){ tolvaGerir(); }
    }

    /**
     * Handler fyrir tening
     * Þegar teningi er kastað færist leikmaður skv. teningi
     * Tölva og notandi gera til skiptis
     * @param actionEvent
     */
    @FXML
    public void onTeningur(ActionEvent actionEvent) {
        if(ludo.iGangi().getValue() && !ludo.erTolva() ){
            System.out.println("Notandi gerir");
            ludo.leikaLeik();
            samiReitur(actionEvent);
            sigurvegari(actionEvent);
        }
        if(ludo.iGangi().getValue() && ludo.erTolva()){
            System.out.println("Tolva gerir");
            tolvaGerir(actionEvent);
        }
    }

    //Hjálparaðferðir:

    /**
     * Birtir tilkynninga-dialog þegar leikmaður lendir á sama reit og andstæðingur.
     * @param actionEvent
     */
    private void samiReitur(ActionEvent actionEvent){
        if(ludo.getSamiReitur().getValue()) {
            tilkynning.birtaTilkynningu(((Node) actionEvent.getSource()).getScene().getWindow(), ludo.getLeikmadur().getNafn(), ludo.getAndstaedingur().getNafn());
        }
    }

    /**
     * Birtir sigurvegara-dialog ef leik er lokið.
     * @param actionEvent
     */
    private void sigurvegari(ActionEvent actionEvent){
        if(ludo.erLokid().getValue()){
            Leikmadur sigurvegari = ludo.getLeikmadur();
            sigurvegariDialog.birtaSigurvegara(((Node) actionEvent.getSource()).getScene().getWindow(), ludo, sigurvegari.getNafn());
            ludo.getStigatafla().uppfaeraStig(sigurvegari);
            if(ludo.erTolva()){ tolvaGerir(); }
        }
    }

    /**
     * Tölva gerir með pauseTransition
     * Peð tölvu bíður í smá tíma áður en hann gerir
     * til að auðveldlega sé hægt að fylgjast með framvindu leiksins.
     * @param actionEvent
     */
    private void tolvaGerir(ActionEvent actionEvent){
        pause.setOnFinished(e->{
            ludo.tolvaGerir();
            Platform.runLater(()->{
                samiReitur(actionEvent);
                sigurvegari(actionEvent);
            });
        });
        pause.play();
    }

    /**
     * Overload aðferð
     * Tölva gerir með pauseTransition
     * til að auðveldlega sé hægt að fylgjast með framvindu leiksins.
     * Þessi aðferð er aðeins notuð þegar leikborðið er ræst í fyrsta skiptið
     */
    private void tolvaGerir(){
        pause.setOnFinished(e->{ ludo.tolvaGerir(); });
        pause.play();
    }

    /**
     * Býr til leiðina á lúdó borðinu
     * Leiðin er fengin úr vinnslunni. Viðmótsreitir (s) eru búnir til fyrir
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
            if(i%2==0 && i!=0){ vidmotsReitur.getStyleClass().add("reitur"); }
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

        if(rod==3 && dalkur==0){ vidmotsReitur.getStyleClass().add("byrjun"); }
        if(rod==4 && dalkur==4){ vidmotsReitur.getStyleClass().add("mark"); }
    }

    /**
     * Bætir þykkari útlínum á valda reiti til að gera leið skýrari
     * @param vidmotsReitur
     * @param reitur
     */
    private void aukaBorder(StackPane vidmotsReitur, Reitur reitur){
        int dalkur = reitur.getDalkurProp().intValue();
        int rod = reitur.getRodProp().intValue();

        if(rod==4 && dalkur==0){ vidmotsReitur.getStyleClass().add("top"); }
        if(rod==4 && (dalkur==1 || dalkur==2 || dalkur==3)){ vidmotsReitur.getStyleClass().add("top_bottom"); }
    }

    /**
     * Býr til nýtt StackPane með fxmlLoader
     * @return StackPane viðmótsreit
     * @throws IOException
     */
    private StackPane loadReitur() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(LudoController.class.getResource("/is/vidmot/reitur-view.fxml"));
        StackPane vidmotsReitur = fxmlLoader.load();
        return vidmotsReitur;
    }

    /**
     * Bindir teningamyndir við teninginn og
     * Teningur óvirkur á meðan tölva gerir
     */
    private void bindaTening(){
        String[] teningaMyndir = {"one", "two", "three", "four", "five", "six"};
        ludo.getTeningur().getTeningurProp().addListener((obs, gamlaGildi, nyttGildi) ->{
            fxTeningur.getStyleClass().remove(teningaMyndir[gamlaGildi.intValue() - 1]);
            fxTeningur.getStyleClass().add(teningaMyndir[nyttGildi.intValue() - 1]);
        });
        fxTeningur.disableProperty().bind(ludo.naestiLeikmadurProp().isEqualTo("Svartur"));
    }

    /**
     * Sækir lit sem var valinn í fellivalmynd og
     * skilar css string til að binda við peð.
     * @return Strengur
     */
    private String litLeikmanns() {
        if(litur == "Gulur") { return "leikmadurGulur"; }
        if(litur == "Rauður") { return "leikmadurRaudur"; }
        if(litur == "Grænn") { return "leikmadurGraenn"; }
        if(litur == "Blár") { return "leikmadurBlar"; }
        if(litur == "Fjólublár") { return "leikmadurFjolublar"; }
        if(litur == "Appelsínugulur") { return "leikmadurAppelsinu"; }
        return null;
    }

    /**
     * Fyrir hvern leikmann: uppfærir mynd á viðmótsreit í samræmi við nýjan reit leikmanns sem
     * er vaktaður í gegnum index inn í fylki af css klösum leikmanna
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
     * Tekur inn nafn næsta leikmanns og
     * skilar css klasa fyrir bakgrunnslit skilaboða
     * @param litur nafn leikmanns
     * @return Strengur
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

    /**
     * Frumstillir bakgrunnslit
     * Hlustar á breytingar um hvaða leikmaður á næsta leik
     * uppfærir bakgrunnslit skilaboða
     */
    private void bindaLit(){
        if(fxUpplysingar.getStyleClass().size()==0){
            fxUpplysingar.getStyleClass().add(bakgrunnslitur(ludo.naestiLeikmadurProp().getValue()));
        }
        ludo.naestiLeikmadurProp().addListener((obs, gamlaGildi, nyttGildi)->{
            fxUpplysingar.getStyleClass().remove(bakgrunnslitur(gamlaGildi));
            fxUpplysingar.getStyleClass().add(bakgrunnslitur(nyttGildi));
        });
    }
}