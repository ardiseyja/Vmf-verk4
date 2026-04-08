package is.vinnsla;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import java.util.Random;

/******************************************************************************
 *  Lýsing  : Eiginleikar Tenings.
 *  Geymir tölu tenings og hefur aðferð til að kasta tening.
 *****************************************************************************/
public class Teningur {
    private static final int MAX = 6;
    private final IntegerProperty tala = new SimpleIntegerProperty(MAX);
    private final Random randomNum = new Random();

    /**
     * Getter fyrir tölu á teningi.
     * Skilar heiltölu teningsins.
     * @return tölu gildi
     */
    public int getTala() { return tala.get(); }

    /**
     * Getter fyrir tölu á teningi.
     * Skilar property af heiltölutagi
     * @return IntegerProperty
     */
    public IntegerProperty getTeningurProp() { return tala; }

    /**
     * Kastar tening þ.a. fundinn sé tala
     * af handahófi á bilinu 1 til MAX +1
     */
    public void kasta() { tala.set(randomNum.nextInt(MAX)+1); }
}
