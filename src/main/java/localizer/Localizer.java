package localizer;

public interface Localizer {
    ConfigLocalizer getConfigLocalizer();

    GameWindowLocalizer getGameWindowsLocalizer();

    CloseFrameLocalizer getCloseFrameLocalizer();

    String getTestMenuName();
}
