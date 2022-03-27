package localizer.english;

import localizer.ConfigLocalizer;
import localizer.ViewModeMenuLocalizer;

public class EnglishConfigMenuLocalizer implements ConfigLocalizer {
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
