package localizer.russian;

import localizer.ConfigMenuLocalizer;
import localizer.LogMenuLocalizer;
import localizer.MenuBarLocalizer;

public class RussianMenuBarLocalizer implements MenuBarLocalizer {
    @Override
    public ConfigMenuLocalizer getConfigMenuLocalizer() {
        return new RussianConfigMenuLocalizer();
    }

    @Override
    public LogMenuLocalizer getLogMenuLocalizer() {
        return new RussianLogMenuLocalizer();
    }
}
