package is.vinnsla;

import javafx.beans.property.SimpleStringProperty;

/******************************************************************************
 *  Nafn    : Árdís Eyja Kjartansdóttir og Kristín Jónsdóttir
 *  T-póstur: aek25@hi.is og krj63@hi.is
 *
 *  Lýsing  : Eiginleikar stigatöflu
 *  Geymir stig fyrir tvo leikmenn, notenda og tölvu.
 *****************************************************************************/
public class Stigatafla {
    private SimpleStringProperty stigLeikmanns = new SimpleStringProperty("0");
    private SimpleStringProperty stigTolvu = new SimpleStringProperty("0");
    private int stigL = 0;
    private int stigT = 0;

    /**
     * Skilar property fyrir stig notendans
     * @return SimpleStringproperty
     */
    public SimpleStringProperty getStigLeikmanns() {
        return stigLeikmanns;
    }

    /**
     * Skilar property fyrir stig tölvu
     * @return SimpleStringproperty
     */
    public SimpleStringProperty getStigTolvu() {
        return stigTolvu;
    }

    /**
     * Uppfærir stigatöflu
     * Bætir við einu stigi fyrir leikmanninn sem vann seinasta leik
     * @param leikmadur nafn sigurvegara
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
