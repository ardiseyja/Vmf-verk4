package is.vinnsla;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import java.util.ArrayList;


/******************************************************************************
 *  Lýsing  : Eiginleikar Lúdó leiks.
 *  Vinnsluklasi fyrir viðmótið
 *  Geymir upplýsingar um leið leiksins, hver á leik, leikmenn, stöðu o.fl.
 *****************************************************************************/
public class Ludo {
    private int naesti; //Index inn í fylki, næsti leikmaður sem á að gera:
    private int byrja;  //Hver byrjaði seinasta leik
    private final int MAX = 35; //Hæsta gildi, index á leiðinni, lengd leiðar -1
    private final Leikmadur[] leikmenn; //Listi yfir leikmenn
    private final ArrayList<Reitur> leid =  new ArrayList<>();   //Lúdóborð, leið leiksins
    private final Teningur teningur = new Teningur();
    private final Stigatafla stigatafla = new Stigatafla();
    private SimpleBooleanProperty samiReitur = new SimpleBooleanProperty(false);    //Heldur utan um hvort leikmaður lenti á sama reit og andstæðingurinn
    private final SimpleStringProperty naestiLeikmadur;  //Hvaða leikmaður á að gera næst
    private final SimpleObjectProperty<Stada> stada = new SimpleObjectProperty<>(Stada.GANGI);  //Staða leiksins
    enum Stada{
        GANGI,
        LOKID
    }

    /**
     * Smiður
     * Býr til leið
     * Skýrir leikmann notenda eftir litavali
     * Stillir hvaða leikmaður byrjar
     * @param i index inn í fylki, hver byrjar
     * @param litur litur notenda
     */
    public Ludo(int i, String litur){
        for(int dalkur = 0; dalkur<4;dalkur++){ leid.add(new Reitur(3,dalkur)); }
        for(int rod=2; rod>=0;rod--){ leid.add(new Reitur(rod,3)); }
        leid.add(new Reitur(0,4));
        for(int rod = 0; rod<4; rod++){ leid.add(new Reitur(rod, 5)); }
        for(int dalkur = 6; dalkur<9; dalkur++){ leid.add(new Reitur(3,dalkur)); }
        leid.add(new Reitur(4,8));
        for(int dalkur = 8;dalkur>4;dalkur--){ leid.add(new Reitur(5,dalkur)); }
        for(int rod = 6; rod<9;rod++){ leid.add(new Reitur(rod, 5)); }
        leid.add(new Reitur(8,4));
        for(int rod=8;rod>4;rod--){ leid.add(new Reitur(rod, 3)); }
        for(int dalkur=2; dalkur>=0;dalkur--){ leid.add(new Reitur(5, dalkur)); }
        for(int dalkur = 0; dalkur<5; dalkur++){ leid.add(new Reitur(4,dalkur)); }

        leikmenn = new Leikmadur[] {new Leikmadur(litur), new Leikmadur("Svartur")};
        this.naesti = i;
        this.byrja = i;
        this.naestiLeikmadur = new SimpleStringProperty(leikmenn[i].getNafn());
    }

    /**
     * Getter fyrir leikmann
     * Skilar næsta leikmanni
     * @return Leikmaður
     */
    public Leikmadur getLeikmadur(){
        return leikmenn[naesti];
    }

    /**
     * Getter fyrir leikmann
     * Skilar leikmanni úr fylki eftir gefnu gildi
     * @param inx index heiltala
     * @return Leikmaður
     */
    public Leikmadur getLeikmadur(int inx){
        return leikmenn[inx];
    }

    /**
     * Getter fyrir leið leiksins
     * @return Leið
     */
    public ArrayList getLeid(){
        return leid;
    }

    /**
     * Getter fyrir tening
     * @return teningurinn
     */
    public Teningur getTeningur() {
        return teningur;
    }

    /**
     * Getter fyrir stigatöflu
     * @return stigatafla
     */
    public Stigatafla getStigatafla(){
        return stigatafla;
    }

    /**
     * Getter fyrir reit
     * Skilar Reit á leiðinni eftir gefnu gildi
     * @param index inn í lista
     * @return Reitur
     */
    public Reitur getReitur(int index){
        return leid.get(index);
    }

    /**
     * Getter fyrir samaReit
     * Skilar boolean property um hvort leikmaður lenti á sama reit
     * true ef leikmaður lenti á sama reit og andstæðingurinn
     * @return SimpleBooleanproperty
     */
    public SimpleBooleanProperty getSamiReitur(){ return samiReitur; }

    /**
     * Getter fyrir andstæðing
     * Skilar andstæðingi núverandi leikmanns
     * @return Leikmadur
     */
    public Leikmadur getAndstaedingur(){
        return leikmenn[(naesti+1)%leikmenn.length];
    }

    //Public hjálparaðferðir
    /**
     * Skilar gildi sem
     * segir til um hvort leikur sé í gangi
     * @return BooleanBinding
     */
    public BooleanBinding iGangi(){
        return stada.isEqualTo(Stada.GANGI);
    }

    /**
     * Skilar gildi sem
     * segir til um hvort leik sé lokinn
     * @return BooleanBinding
     */
    public BooleanBinding erLokid(){
        return stada.isEqualTo(Stada.LOKID);
    }

    /**
     * Aðferð sem athugar hvort leikmaður sé tölva
     * Skilar true er leikmaðurinn er tölva
     * @return boolean
     */
    public boolean erTolva(){
        return naestiLeikmadur.getValue().equals("Svartur");
    }

    /**
     * Skilar property fyrir næsta leikmann
     * @return SimpleStringProperty
     */
    public SimpleStringProperty naestiLeikmadurProp(){
        return naestiLeikmadur;
    }

    //Private hjálparaðferðir:
    /**
     * Athugar hvort leikmaður lenti á sama reit og fyrri leikmaður
     * Skilar true ef leikmaður er á sama reit
     * @param reitur, index í lista
     * @return boolean
     */
    private boolean aSamaReit(int reitur){
        return leikmenn[(naesti+1)%leikmenn.length].getReitur() == reitur;
    }

    /**
     * Segir til um hvort leikmaður sé kominn í mark
     * Skilar true ef leikmaður er kominn í mark
     * @return boolean
     */
    private boolean kominIMark(){
        return getLeikmadur().getReitur() == MAX;
    }

    /**
     * Færir yfir á næsta leikmann
     */
    private void setNaesti(){
        naesti = (naesti+1)%leikmenn.length;
        naestiLeikmadur.set(leikmenn[naesti].getNafn());
    }

    /**
     * Færir leikmann eftir að teningi,
     * færir andstæðing á byrjunarreit ef leikmaður lendir á sama reit
     * Skilar gildi um hvort leikur sé í gangi, true ef í gangi
     * @return boolean
     */
    private boolean faeraLeikmann(){
        getLeikmadur().faera(teningur.getTala(), MAX);
        samiReitur.set(false);
        if(aSamaReit(getLeikmadur().getReitur())){
            samiReitur.set(true);
            getAndstaedingur().setReitur(0);
        }
        return kominIMark();
    }

    //Aðalaðferðir:

    /**
     * Kastar tening, færir peð notenda, setur næsta leikmann
     * @return skilar true ef leik er lokið
     */
    public boolean leikaLeik() {
        teningur.kasta();

        if(faeraLeikmann()){
            stada.set(Stada.LOKID);
            return true;
        }

        setNaesti();
        return false;
    }

    /**
     * Kastar tening, færir peð tölvunnar, setur næsta leikmann
     * @return skilar true ef leik er lokið
     */
    public boolean tolvaGerir(){
        teningur.kasta();

        if(faeraLeikmann()){
            stada.set(Stada.LOKID);
            return true;
        }

        setNaesti();
        return false;
    }

    /**
     * Hefur nýjann leik.
     * Leikmenn og leikur settur í upphafsstöðu
     */
    public void nyrLeikur(){
        this.naesti = (byrja+1)%leikmenn.length;
        this.byrja = naesti;
        naestiLeikmadur.set(leikmenn[naesti].getNafn());
        stada.set(Stada.GANGI);
        leikmenn[0].setReitur(0);
        leikmenn[1].setReitur(0);
    }
}
