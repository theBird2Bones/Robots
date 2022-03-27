package localizer.english;

import localizer.ConfigLocalizer;
import localizer.GameWindowLocalizer;
import localizer.Localizer;
import localizer.CloseFrameLocalizer;

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
    public CloseFrameLocalizer getCloseFrameLocalizer(){
        return new EnglishCloseFrameLocalizer();
    }

    @Override
    public String getTestMenuName() {
        return "Tests";
    }
}
