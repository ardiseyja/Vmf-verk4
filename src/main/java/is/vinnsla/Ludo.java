package is.vinnsla;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import java.util.ArrayList;


/**
 * Lúdó leikur, vinnsluklasinn sem viðmótið talar við
 */
public class Ludo {
    //Index inn í fylki, næsti leikmaður sem á að gera
    private int naesti = 0;

    //Hæsta gildi, index á leiðinni
    private final int MAX = 15;

    //Leikmenn
    private final Leikmadur[] leikmenn = {new Leikmadur("Rauður"), new Leikmadur("Blár")};

    //Lúdóborð
    private final ArrayList<Reitur> leid =  new ArrayList<>();

    //Teningur
    private final Teningur teningur = new Teningur();

    //Hvaða leikmaður á að gera næst
    private final SimpleStringProperty naestiLeikmadur = new SimpleStringProperty(leikmenn[0].getNafn());

    //Staða leiksins
    private final SimpleObjectProperty<Stada> stada = new SimpleObjectProperty<>(Stada.GANGI);

    enum Stada{
        GANGI,
        LOKID
    }


    /**
     * Smiður, býr til leið, þ.e.
     * býr til lúdó-leiðina
     */
    public Ludo(){
        for(int rod = 4; rod>=0; rod--) {
            leid.add(new Reitur(rod, 0));
        }
        for(int dalkur = 1; dalkur<5; dalkur++) {
            leid.add(new Reitur(0, dalkur));
        }
        for(int rod = 1; rod<5; rod++) {
            leid.add(new Reitur(rod, 4));
        }
        for(int dalkur = 3; dalkur>0; dalkur--) {
            leid.add(new Reitur(4, dalkur));
        }
    }


    //getters og setters:

    /**
     * Skilar næsta leikmanni
     * @return Leikmaður
     */
    public Leikmadur getLeikmadur(){
        return leikmenn[naesti];
    }

    /**
     * Skilar leikmanni úr fylki eftir gefnu gildi
     * @param inx index heiltala
     * @return Leikmaður
     */
    public Leikmadur getLeikmadur(int inx){
        return leikmenn[inx];
    }

    /**
     * Skilar leið leiksins
     * @return Leið
     */
    public ArrayList getLeid(){
        return leid;
    }

    /**
     * Skilar teningnum
     * @return teningurinn
     */
    public Teningur getTeningur() {
        return teningur;
    }

    /**
     * Skilar property gildi sem
     * segir til um hvort leikurinn sé í gangi
     * @return property
     */
    public BooleanBinding iGangi(){
        return stada.isEqualTo(Stada.GANGI);
    }

    /**
     * Skilar property gildi sem
     * segir til um hvort leikurinn sé lokinn
     * @return property
     */
    public BooleanBinding erLokid(){
        return stada.isEqualTo(Stada.LOKID);
    }

    /**
     * Skilar property fyrir næsta leikmann
     * @return næsti leikmaður
     */
    public SimpleStringProperty naestiLeikmadurProp(){
        return naestiLeikmadur;
    }

    /**
     * Skilar Reit á leiðinni eftir gefnu gildi
     * @param index inn í listann
     * @return Reitur
     */
    public Reitur getReitur(int index){
        return leid.get(index);
    }


    //Private hjálparaðferðir:

    /**
     * Athugar hvort leikmaður lenti á sama reit og fyrri leikmaður
     * @param reitur, index í lista
     * @return true ef er á sama reit
     */
    private boolean aSamaReit(int reitur){
        return leikmenn[(naesti+1)%leikmenn.length].getReitur() == reitur;
    }

    /**
     * Segir til um hvort leikmaður sé kominn í mark
     * @return boolean gildi
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
     * Skilar andstæðingi núverandi leikmanns
     * @return Leikmadur
     */
    private Leikmadur getAndstaedingur(){
        return leikmenn[(naesti+1)%leikmenn.length];
    }

    /**
     * færir leikmann eftir að teningi,
     * færir andstæðing á byrjunarreit ef leikmaður lendir á sama reit
     * Skilar gildi um hvort leikur sé í gangi
     * @return boolean
     */
    private boolean faeraLeikmann(){
        getLeikmadur().faera(teningur.getTala(), MAX);

        if(aSamaReit(getLeikmadur().getReitur())){
            getAndstaedingur().setReitur(0);
        }

        return kominIMark();
    }


    //Aðalaðferðir:

    /**
     * Kastar tening, færir leikmann, setur næsta leikmann
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
     * Hefur nýjann leik.
     * Leikmenn og leikur settur í upphafsstöðu
     */
    public void nyrLeikur(){
        stada.set(Stada.GANGI);
        leikmenn[0].setReitur(0);
        leikmenn[1].setReitur(0);
    }
}
