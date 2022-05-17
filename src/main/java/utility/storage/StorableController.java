package utility.storage;

import gui.JInternalFrameExtended;
import gui.StateLoadingFrame;
import lombok.Getter;
import localizer.ObservableLocalization;

import java.beans.PropertyVetoException;
import java.util.*;


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

    public void openConfigIO() {
        configIO.openFile();
    }

    public void closeConfigIO() {
        configIO.close();
    }

    public void saveListeners() {
        configIO.writeConfig(
                ObservableLocalization.instance().getLocale(),
                listeners.stream().map(StorableData::of).toList()
        );
    }

    public Locale loadLocale() {
        return configIO.loadLocale();
    }

    public void setUpFrame(JInternalFrameExtended frame) {
        askAboutLoading();

        StorableData data = isLoading ? configIO.loadFrameState(frame.getClass()) : StorableData.defaultValue(frame);
        try {
            frame.setLocation(data.position);
            frame.setSize(data.size);
            frame.setVisible(data.isVisible);
            frame.setMaximum(data.isMaximum);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    public void addListener(JInternalFrameExtended inst) {
        listeners.add(inst);
    }

    public void askAboutLoading() {
        if (isLoading == null) {
            synchronized (syncObj) {
                if (isLoading == null) {
                    isLoading = configIO.isConfigExist() && StateLoadingFrame.showDialogBox() == 0;
                }
            }
        }
    }
}
