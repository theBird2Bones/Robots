package localizer;

import java.util.ListResourceBundle;
import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationManager {
    public static String getBundled(ResourceBundle bundle, LocalizationKey key){
        return bundle.getString(key.value());
    }
    public static ResourceBundle getResourceBundle(Locale locale){
        return ListResourceBundle.getBundle("localization.Localization", locale);
    }
}
