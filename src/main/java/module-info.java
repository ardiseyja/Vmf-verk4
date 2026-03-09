/******************************************************************************
 *  Nafn    : Árdís Eyja Kjartansdóttir
 *  T-póstur: aek25@hi.is
 *  Lýsing  : Verkefni 2, Viðmótsforritun, Ludo
 *****************************************************************************/

module LudoJavaFX {
    requires javafx.fxml;
    requires javafx.controls;
    opens is.vidmot to javafx.fxml;

    exports is.vidmot;
    exports is.vinnsla;
}