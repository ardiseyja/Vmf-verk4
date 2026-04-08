package is.vidmot;

import is.vidmot.switcher.View;
import is.vidmot.switcher.ViewSwitcher;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/******************************************************************************
 *  Lýsing  : Ræsir appið
 *****************************************************************************/
public class LudoApp extends javafx.application.Application {
    /**
     * Ræsir appið
     * @param stage glugginn
     * @throws Exception undnantekning sem verður ef villla
     */
    @Override
    public void start(Stage stage) throws Exception {
        var scene = new Scene(new Pane());
        ViewSwitcher.setScene(scene);
        ViewSwitcher.switchTo(View.ADAL, true, null, 0);
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
