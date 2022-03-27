package localizer.russian;

import localizer.ConfigLocalizer;
import localizer.GameWindowLocalizer;
import localizer.Localizer;
import localizer.ViewModeMenuLocalizer;

public class RussianLocalizer implements Localizer {
    @Override
    public ConfigLocalizer getConfigLocalizer() {
        return new RussianConfigMenuLocalizer();
    }

    @Override
    public GameWindowLocalizer getGameWindowsLocalizer() {
        return new RussianGameWindowLocalizer();
    }

    @Override
    public String getTestMenuName() {
        return "Тесты";
    }
}
