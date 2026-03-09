package is.vinnsla;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/******************************************************************************
 *  Nafn    : Árdís Eyja Kjartansdóttir
 *  T-póstur: aek25@hi.is
 *  Lýsing  : Verkefni 2, Viðmótsforritn, Lúdó
 *****************************************************************************/

/**
 * Klasi sem geymir eiginleika leikmanns
 */
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
     * Skilar nafni leikmanns sem streng
     * @return nafn
     */
    public String getNafn() { return nafn.get(); }

    /**
     * Skilar heiltölu sem er
     * index inn í lista sem geymir leiðina
     * @return reitur, index í lista
     */
    public int getReitur() { return reitur.get(); }

    /**
     * Skilar property fyrir reitinn sem peð leikmanns er á
     * @return property gildi
     */
    public SimpleIntegerProperty getReiturProp() { return reitur; }

    /**
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


    //Aðferðir fyrir prófun:
    @Override
    public String toString() {
        return "Leikmadur{" +
                "nafn=" + nafn +
                ", reitur=" + reitur +
                '}';
    }

    public static void main (String [] args) {
        Leikmadur l = new Leikmadur("Gulur");
        l.faera(2,6);
        System.out.print(l);
        l.faera(2,6);
        System.out.print(l);
    }
}
