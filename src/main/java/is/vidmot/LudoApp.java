package is.vidmot;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/******************************************************************************
 *  Nafn    : Árdís Eyja Kjartansdóttir
 *  T-póstur: aek25@hi.is
 *  Lýsing  : Verkefni 2, Viðmótsforritun, Ludo
 *****************************************************************************/

public class LudoApp extends javafx.application.Application {
    /**
     * Ræsir appið
     * @param stage glugginn
     * @throws Exception undnantekning sem verður ef villla
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(LudoApp.class.getResource("ludo-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),537,576);
        stage.setTitle("Ludo");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Aðalforritið sem ræsir appið
     * @param args ónotað
     */
    public static void main(String[] args) {
        launch();
    }
}
