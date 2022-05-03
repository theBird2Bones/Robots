package utility.storage;

import gui.JInternalFrameExtended;
import org.ini4j.Wini;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ConfigIO {
    private static final String CONFIG_FOLDER_PATH = "data/";
    private static final String CONFIG_PATH = "data/config.ini";

    private static final String LOCALIZATION_SECTION = "Localization";

    private static final String LOCAL_FIELD = "local";
    private static final String X_FIELD = "x";
    private static final String Y_FIELD = "y";
    private static final String VISIBLE_FIELD = "visible";

    private Wini wini;

    public void writeConfig(Locale local, List<StorableData> data) {
        try {
            var file = new File(CONFIG_PATH);
            if (!file.exists()) {
                new File(CONFIG_FOLDER_PATH).mkdirs();
                file.createNewFile();
            }

            Wini ini = new Wini(file);
            ini.put(LOCALIZATION_SECTION, LOCAL_FIELD, local);
            for (var frameData : data) {
                ini.put(frameData.clazz.toString(), X_FIELD, frameData.position.x);
                ini.put(frameData.clazz.toString(), Y_FIELD, frameData.position.y);
                ini.put(frameData.clazz.toString(), VISIBLE_FIELD, frameData.isVisible);
            }
            ini.store();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Locale loadLocale() {
        return isConfigExist() ?
                new Locale(wini.get(LOCALIZATION_SECTION, LOCAL_FIELD))
                : Locale.getDefault();
    }

    public StorableData loadFrameState(Class<? extends JInternalFrameExtended> frame) {
        return new StorableData(
                frame,
                new Point(
                        wini.get(frame.toString(), X_FIELD, int.class),
                        wini.get(frame.toString(), Y_FIELD, int.class)
                ),
                wini.get(frame.toString(), VISIBLE_FIELD, boolean.class)
        );
    }

    public void openFile(){
        try {
            wini = new Wini(new File(CONFIG_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close(){
        wini = null;
    }

    public boolean isConfigExist(){
        return wini != null ? wini.getFile().exists() : new File(CONFIG_PATH).exists();
    }

    /*public boolean isConfigExist(){
        return isConfigExist(new File(CONFIG_PATH));
    }

    public boolean isConfigExist(File file){
        return file.exists();
    }*/
}
