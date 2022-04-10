package utility;

import localizer.LocalizationManager;
import lombok.Getter;

import java.util.*;

public class ObservableLocalization {
    private static volatile ObservableLocalization instance = null;
    private static Object syncObj = new Object();
    private final List<ChangingLanguage> listeners = new LinkedList<>();
    @Getter
    private ResourceBundle bundle;

    private ObservableLocalization() {
    }

    public static ObservableLocalization instance() {
        if (instance == null) {
            synchronized (syncObj) {
                if (instance == null) {
                    instance = new ObservableLocalization();
                }
            }
        }
        return instance;
    }

    private void updateListeners(ResourceBundle bundle) {
        for (var listener : listeners) {
            listener.changeLanguageWith(bundle);
        }
    }

    public void addListener(ChangingLanguage inst) {
        listeners.add(inst);
    }

    public void addListeners(ChangingLanguage... insts) {
        listeners.addAll(Arrays.asList(insts));
    }

    public void changeLocale(Locale newLocale) {
        bundle = LocalizationManager.getResourceBundle(newLocale);
        updateListeners(bundle);
    }
}
