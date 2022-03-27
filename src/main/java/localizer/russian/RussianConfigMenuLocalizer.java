package localizer.russian;

import localizer.ConfigLocalizer;
import localizer.ViewModeMenuLocalizer;

public class RussianConfigMenuLocalizer implements ConfigLocalizer {
    @Override
    public String getConfigMenuName() {
        return "Настройки";
    }

    @Override
    public ViewModeMenuLocalizer getViewModeMenuLocalizer() {
        return new RussianViewModeMenuLocalizer();
    }

    @Override
    public String getExitButtonName() {
        return "Выход";
    }
}
