package localizer.english;

import localizer.ViewModeMenuLocalizer;

public class EnglishViewModeMenuLocalizer implements ViewModeMenuLocalizer {
    @Override
    public String getViewModeMenuName() {
        return "View Modes";
    }

    @Override
    public String getUniversalSchemeName() {
        return "Universal scheme";
    }

    @Override
    public String getSystemSchemeName() {
        return "System scheme";
    }
}
