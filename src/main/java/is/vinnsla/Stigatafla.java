package is.vinnsla;

import javafx.beans.property.SimpleStringProperty;

/**
 * Klasi sem heldur utan um eiginleika stigatöflu
 */
public class Stigatafla {
    private SimpleStringProperty stigLeikmanns = new SimpleStringProperty("0");
    private SimpleStringProperty stigTolvu = new SimpleStringProperty("0");
    private int stigL = 0;
    private int stigT = 0;

    /**
     * Skilar property fyrir stig notendans
     * @return property
     */
    public SimpleStringProperty getStigLeikmanns() {
        return stigLeikmanns;
    }

    /**
     * Skilar property fyrir stig tölvu
     * @return property
     */
    public SimpleStringProperty getStigTolvu() {
        return stigTolvu;
    }

    /**
     * Uppfærir stigatöflu
     * Bætir við einu stigi fyrir leikmanninn sem vann seinasta leik
     * @param leikmadur sigurvegari
     */
    public void uppfaeraStig(Leikmadur leikmadur){
        if(leikmadur.getNafn() == "Svartur"){
            stigT ++;
            stigTolvu.set(stigT+"");
        }
        else{
            stigL++;
            stigLeikmanns.set(stigL +"");
        }
    }
}
