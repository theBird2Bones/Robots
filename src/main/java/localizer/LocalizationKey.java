package localizer;

public enum LocalizationKey {
  CONFIG_MENU_NAME("configMenuName"),
  EXIT_BUTTON_NAME("exitButtonName"),

  VIEW_MODE_MENU_NAME("viewModeMenuName"),
  UNIVERSAL_SCHEME_NAME("universalSchemeName"),
  SYSTEM_SCHEME_NAME("systemSchemeName"),

  LOG_SWITCHER_NAME("logSwitcherName"),
  GAME_SWITCHER_NAME("gameSwitcherName"),
  COORDINATING_WINDOW_SWITCHER_NAME("coordinatingWindowSwitcherName"),
  TEST_LOG_BUTTON_NAME("testLogButtonName"),

  COORDINATING_WINDOW_NAME("coordinatingWindowName"),
  GAME_WINDOW_NAME("gameWindowName"),
  LOG_WINDOW_NAME("logWindowName"),

  CLOSING_FRAME_TITLE("closingFrameTitle"),
  CLOSING_FRAME_MESSAGE("closingFrameMessage"),

  STATE_LOADING_FRAME_TITLE("stateLoadingFrameTitle"),
  STATE_LOADING_FRAME_MESSAGE("stateLoadingFrameMessage"),

  OPTION_YES("OptionYes"),
  OPTION_NO("OptionNo"),

  LANGUAGE_MENU_TITLE("languages"),
  LANGUAGE_MENU_RUSSIAN("russian"),
  LANGUAGE_MENU_ENGLISH("english"),
  NEXT_TARGET_NAME("goal"),
  POSITION_NAME("position"),

  WINDOWS_MENU_NAME("windowsMenuName");

  private final String value;

  LocalizationKey(String value) {
    this.value = value;
  }

  public String value() {
    return value;
  }
}
