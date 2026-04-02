package is.vidmot.switcher;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 *
 * viðbætur fyrir Ludo verkefni
 */
public enum View {
    ADAL("/is/vidmot/upphaf-view.fxml"),
    LUDO("/is/vidmot/ludo-view.fxml");


    private String fileName;

    View(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
