package is.vinnsla;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/******************************************************************************
 *  Nafn    : Árdís Eyja Kjartansdóttir og Kristín Jónsdóttir
 *  T-póstur: aek25@hi.is og krj63@hi.is
 *
 *  Lýsing  : Eiginleikar leikmanns, geymir nafn og reit leikmanns
 *****************************************************************************/
public class Leikmadur {
    private final SimpleStringProperty nafn = new SimpleStringProperty();
    private final SimpleIntegerProperty reitur = new SimpleIntegerProperty(0);

    /**
     * Smiður fyrir leikmann
     * @param nafn strengur
     */
    public Leikmadur(String nafn){
        this.nafn.set(nafn);
    }


    /**
     * Getter fyrir nafn leikmanns.
     * Skilar nafni leikmanns sem streng
     * @return nafn
     */
    public String getNafn() { return nafn.get(); }

    /**
     * Getter fyrir reit leikmanns.
     * Skilar heiltölu sem index inn í lista sem geymir leiðina
     * @return reitur, index í lista
     */
    public int getReitur() { return reitur.get(); }

    /**
     * Getter fyrir reit leikmanns.
     * Skilar property fyrir reit leikmanns, index inn í fylki
     * @return property gildi
     */
    public SimpleIntegerProperty getReiturProp() { return reitur; }

    /**
     * Setter fyrir reit leikmanns.
     * Breytir gildi reits
     * @param i index, heiltala
     */
    public void setReitur(int i){
        reitur.set(i);
    }

    /**
     * Færir peð leikmanns um i sæti en þó aldrei fram yfir max
     * @param i sæti sem á að færa peðið fram um
     * @param max hæsta sæti
     */
    public void faera(int i, int max) {;
        int nyrReitur = reitur.get() + i;
        if(nyrReitur<=max) setReitur(nyrReitur);
        if(nyrReitur>max) setReitur(max);
    }
}
