package is.vinnsla;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import java.util.Random;

/******************************************************************************
 *  Nafn    : Árdís Eyja Kjartansdóttir
 *  T-póstur: aek25@hi.is
 *  Lýsing  : Verkefni 2, Viðmótsforritun, Lúdó
 *****************************************************************************/

/**
 * Klasi sem geymir eiginleika tenings
 */
public class Teningur {
    private static final int MAX = 6;
    private final IntegerProperty tala = new SimpleIntegerProperty(MAX);
    private final Random randomNum = new Random();


    /**
     * skilar int tölu teningsins
     * @return tölu gildi
     */
    public int getTala() {return tala.get();}

    /**
     * skilar property fyrir tening
     * @return IntegerProperty
     */
    public IntegerProperty getTeningurProp() {return tala;}


    /**
     * Kastar tening þ.a. fundinn sé tala
     * af handahófi á bilinu 1 til MAX +1
     */
    public void kasta() {tala.set(randomNum.nextInt(MAX)+1);}


    //Aðferðir fyrir prófun á klasanum:
    @Override
    public String toString() {
        return "" + tala;
    }

    public static void main (String [] args) {
        Teningur t = new Teningur();
        t.kasta();
        System.out.println (t);
    }
}
