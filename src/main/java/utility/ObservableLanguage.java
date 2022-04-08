package utility;

import localizer.LocalizationManager;

import java.util.*;

public class ObservableLanguage  {
    private static volatile ObservableLanguage instance = null;
    private static Object syncObj = new Object();
    private final List<ChangingLanguage> listeners = new LinkedList<>();
    private ObservableLanguage(){}

    private void updateListeners(ResourceBundle bundle){
        for(var listener: listeners){
            listener.update(bundle);
        }
    }

    public static ObservableLanguage instance(){
        if(instance == null){
            synchronized (syncObj){
                if(instance == null){
                    instance = new ObservableLanguage();
                }
            }
        }
        return instance;
    }

    public void setListener(ChangingLanguage inst){
        listeners.add(inst);
    }

    public void setListeners(ChangingLanguage... insts){
        listeners.addAll(Arrays.asList(insts));
    }

    public void changeLocale(Locale newLocale){
        updateListeners(LocalizationManager.getResourceBundle(newLocale));
    }
}
