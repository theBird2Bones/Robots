package localizer.english;

import localizer.ConfigMenuLocalizer;
import localizer.LogMenuLocalizer;
import localizer.MenuBarLocalizer;

public class EnglishMenuBarLocalizer implements MenuBarLocalizer {

    @Override
    public ConfigMenuLocalizer getConfigMenuLocalizer() {
        return new EnglishConfigMenuLocalizer();
    }

    @Override
    public LogMenuLocalizer getLogMenuLocalizer() {
        return new EnglishLogMenuLocalizer();
    }
}
