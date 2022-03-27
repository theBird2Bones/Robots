package localizer.russian;

import localizer.ConfigMenuLocalizer;
import localizer.ViewModeMenuLocalizer;

public class RussianConfigMenuLocalizer implements ConfigMenuLocalizer {
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
