package localizer;

import lombok.Getter;

import java.util.*;

public class ObservableLocalization {
  private static final Object syncObj = new Object();
  private static volatile ObservableLocalization instance = null;
  private final List<ChangingLanguage> listeners = new LinkedList<>();
  @Getter private ResourceBundle bundle;
  @Getter private Locale locale;

  private ObservableLocalization() {}

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
    locale = newLocale;
    bundle = LocalizationManager.getResourceBundle(newLocale);
    updateListeners(bundle);
  }
}
