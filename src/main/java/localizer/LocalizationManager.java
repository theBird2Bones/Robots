package localizer;


import localizer.english.EnglishLocalizer;
import localizer.russian.RussianLocalizer;

import java.util.Locale;

public class LocalizationManager {
    public static Localizer getLocalizerFromLocale(Locale locale){
        return switch (locale.getLanguage()){
            case "en" -> new EnglishLocalizer();
            case "ru" -> new RussianLocalizer();
            default -> new EnglishLocalizer();
        };
    }
}
