package localizer.russian;

import localizer.ViewModeMenuLocalizer;

public class RussianViewModeMenuLocalizer implements ViewModeMenuLocalizer {
    @Override
    public String getViewModeMenuName() {
        return "Режимы отображения";
    }

    @Override
    public String getUniversalSchemeName() {
        return "Универсальная схема";
    }

    @Override
    public String getSystemSchemeName() {
        return "Системная схема";
    }
}
