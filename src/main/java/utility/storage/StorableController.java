package utility.storage;

import gui.JInternalFrameExtended;
import gui.StateLoadingFrame;
import lombok.Getter;
import utility.ObservableLocalization;

import java.io.File;
import java.util.*;

import static utility.GlobalConst.CONFIG_PATH;

public class StorableController {
    private static volatile StorableController instance = null;
    private static final Object syncObj = new Object();

    private final ConfigIO configIO = new ConfigIO();
    private final List<JInternalFrameExtended> listeners = new LinkedList<>();

    @Getter
    private volatile Boolean isLoading = null;

    private StorableController() {
    }

    public static StorableController instance() {
        if (instance == null) {
            synchronized (syncObj) {
                if (instance == null) {
                    instance = new StorableController();
                }
            }
        }
        return instance;
    }

    public void saveListeners() {
        configIO.writeConfig(
                ObservableLocalization.instance().getLocale(),
                listeners.stream().map(StorableData::of).toList()
        );
    }

    public Locale loadLocale(){
        return new File(CONFIG_PATH).exists() ? configIO.loadLocale() : Locale.getDefault();
    }

    public void setUpFrame(JInternalFrameExtended frame) {
        askAboutLoading();

        StorableData data = isLoading ? configIO.loadFrameState(frame.getClass()) : StorableData.defaultValue(frame);
        frame.setLocation(data.position);
        frame.setVisible(data.isVisible);
    }

    public void addListener(JInternalFrameExtended inst) {
        listeners.add(inst);
    }

    public void askAboutLoading(){
        if (isLoading == null) {
            synchronized (syncObj) {
                if (isLoading == null) {
                    isLoading = new File(CONFIG_PATH).exists() && StateLoadingFrame.showDialogBox() == 0;
                }
            }
        }
    }
}
