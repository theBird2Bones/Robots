package localizer.english;

import localizer.ConfigLocalizer;
import localizer.GameWindowLocalizer;
import localizer.Localizer;

public class EnglishLocalizer implements Localizer {
    @Override
    public ConfigLocalizer getConfigLocalizer() {
        return new EnglishConfigMenuLocalizer();
    }

    @Override
    public GameWindowLocalizer getGameWindowsLocalizer() {
        return new EnglishGameWindowLocalizer();
    }

    @Override
    public String getTestMenuName() {
        return "Tests";
    }
}
