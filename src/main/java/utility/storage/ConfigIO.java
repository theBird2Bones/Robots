package utility.storage;

import gui.JInternalFrameExtended;
import org.ini4j.Wini;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static utility.GlobalConst.CONFIG_FOLDER_PATH;
import static utility.GlobalConst.CONFIG_PATH;

public class ConfigIO {
    private static final String LOCALIZATION_SECTION = "Localization";

    private static final String LOCAL_FIELD = "local";
    private static final String X_FIELD = "x";
    private static final String Y_FIELD = "y";
    private static final String VISIBLE_FIELD = "visible";

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
        try {
            return new Locale(new Wini(new File(CONFIG_PATH)).get(LOCALIZATION_SECTION, LOCAL_FIELD));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Locale.getDefault();
    }

    public StorableData loadFrameState(Class<? extends JInternalFrameExtended> frame) {
        try {
            Wini ini = new Wini(new File(CONFIG_PATH));
            return new StorableData(
                    frame,
                    new Point(
                            ini.get(frame.toString(), X_FIELD, int.class),
                            ini.get(frame.toString(), Y_FIELD, int.class)
                    ),
                    ini.get(frame.toString(), VISIBLE_FIELD, boolean.class)
            );

        } catch (IOException e) {
            e.printStackTrace();
        }
        return StorableData.defaultValue(frame);
    }
}
