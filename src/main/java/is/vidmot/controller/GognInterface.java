package is.vidmot.controller;

import java.io.IOException;

/******************************************************************************
 *  Nafn    : Ebba Þóra Hvannberg
 *  T-póstur: ebba@hi.is
 *
 *  Uppfært af: Kristín Jónsdóttir
 *  T-póstur: krj63@hi.is
 *
 *  Lýsing  : Interface sem leyfir að setja gögn
 *  Útfært af contollerum sem vilja hlaða inn gögnum í
 *  viðmótshluti
 *****************************************************************************/
public interface GognInterface<String> {

    public void setGogn(String data, int i) throws IOException;
}

