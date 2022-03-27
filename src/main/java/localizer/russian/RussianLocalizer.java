package localizer.russian;

import localizer.*;

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
    public CloseFrameLocalizer getCloseFrameLocalizer(){
        return new RussianCloseFrameLocalizer();
    }

    @Override
    public String getTestMenuName() {
        return "Тесты";
    }
}
