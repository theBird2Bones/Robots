package localizer;

import gui.GameWindow;

public interface Localizer {
    ConfigLocalizer getConfigLocalizer();

    GameWindowLocalizer getGameWindowsLocalizer();

    String getTestMenuName();
}
