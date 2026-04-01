package is.vidmot.controller;

import java.io.IOException;

/******************************************************************************
 *  Lýsing  : Interface sem leyfir að setja gögn
 *  Útfært af contollerum sem vilja hlaða inn gögnum í
 *  viðmótshluti
 *****************************************************************************/
public interface GognInterface<String> {

    public void setGogn(String data) throws IOException;
}

