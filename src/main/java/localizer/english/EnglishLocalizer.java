package localizer.english;

import localizer.MenuBarLocalizer;
import localizer.GameWindowLocalizer;
import localizer.Localizer;
import localizer.CloseFrameLocalizer;


public class EnglishLocalizer implements Localizer {
    @Override
    public MenuBarLocalizer getMenuBarLocalizer() {
        return new EnglishMenuBarLocalizer();
    }

    @Override
    public GameWindowLocalizer getGameWindowsLocalizer() {
        return new EnglishGameWindowLocalizer();
    }

    @Override
    public CloseFrameLocalizer getCloseFrameLocalizer(){
        return new EnglishCloseFrameLocalizer();
    }
  
    public String getTestMenuName() {
        return "Tests";
    }
}
