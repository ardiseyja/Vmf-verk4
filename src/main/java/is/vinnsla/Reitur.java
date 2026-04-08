package is.vinnsla;

import javafx.beans.property.SimpleIntegerProperty;

/******************************************************************************
 *  Lýsing  : Eiginleikar reits.
 *  Geymir röð og dálk reits á leikborði
 *****************************************************************************/
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
     * Getter fyrir röð reits
     * Skilar property fyrir röð reits
     * @return röð
     */
    public SimpleIntegerProperty getRodProp() { return rod; }

    /**
     * Getter fyrir dálk reits.
     * Skilar property fyrir dálk reitarins
     * @return dálkur
     */
    public SimpleIntegerProperty getDalkurProp() { return dalkur; }
}
