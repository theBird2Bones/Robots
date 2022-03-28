package localizer;

public interface Localizer {
    MenuBarLocalizer getMenuBarLocalizer();

    GameWindowLocalizer getGameWindowsLocalizer();

    CloseFrameLocalizer getCloseFrameLocalizer();

    String getTestMenuName();
}
