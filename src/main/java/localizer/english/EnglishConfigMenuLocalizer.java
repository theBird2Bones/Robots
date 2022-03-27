package localizer.english;

import localizer.ConfigMenuLocalizer;
import localizer.ViewModeMenuLocalizer;

public class EnglishConfigMenuLocalizer implements ConfigMenuLocalizer {
    @Override
    public String getConfigMenuName() {
        return "Settings";
    }

    @Override
    public ViewModeMenuLocalizer getViewModeMenuLocalizer() {
        return new EnglishViewModeMenuLocalizer();
    }

    @Override
    public String getExitButtonName() {
        return "Exit";
    }
}
