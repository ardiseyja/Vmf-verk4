package is.vinnsla;

import javafx.beans.property.SimpleIntegerProperty;

/******************************************************************************
 *  Nafn    : Árdís Eyja Kjartansdóttir
 *  T-póstur: aek25@hi.is
 *  Lýsing  : Verkefni 2, Viðmótsforritun, Lúdó
 *****************************************************************************/

/**
 * Klasi sem geymir eiginleika reits, röð og dálk
 */
public class Reitur {
    private SimpleIntegerProperty rod = new SimpleIntegerProperty();
    private SimpleIntegerProperty dalkur = new SimpleIntegerProperty();

    /**
     * Smiður, býr til reit
     * @param rod röð
     * @param dalkur dálkur
     */
    public Reitur(int rod, int dalkur){
        this.rod.set(rod);
        this.dalkur.set(dalkur);
    }


    /**
     * Skilar röð reitarins
     * @return röð
     */
    public SimpleIntegerProperty getRodProp() {return rod;}

    /**
     * skilar dálk reitarins
     * @return dálkur
     */
    public SimpleIntegerProperty getDalkurProp() {return dalkur;}


    //Aðferðir fyrir prófun:
    @Override
    public String toString() {
        return "Reitur{" +
                "rod=" + rod +
                ", dalkur=" + dalkur +
                '}';
    }

    public static void main(String[] args){
        Reitur r = new Reitur(1,3);
        System.out.print(r);
    }
}
