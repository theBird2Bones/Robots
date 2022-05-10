package localization;

import java.util.ListResourceBundle;

import static localizer.LocalizationKey.*;

public class Localization_en extends ListResourceBundle {
  @Override
  protected Object[][] getContents() {
    return new Object[][] {
      {CONFIG_MENU_NAME.value(), "Settings"},
      {EXIT_BUTTON_NAME.value(), "Exit"},
      {VIEW_MODE_MENU_NAME.value(), "View Modes"},
      {UNIVERSAL_SCHEME_NAME.value(), "Universal scheme"},
      {SYSTEM_SCHEME_NAME.value(), "System scheme"},
      {LOG_SWITCHER_NAME.value(), "Turn on/off log window"},
      {GAME_SWITCHER_NAME.value(), "Turn on/off game window"},
      {TEST_LOG_BUTTON_NAME.value(), "Message in log"},
      {COORDINATING_WINDOW_NAME.value(), "Robot's coordinates"},
      {GAME_WINDOW_NAME.value(), "Game window"},
      {LOG_WINDOW_NAME.value(), "Work protocol"},
      {CLOSING_FRAME_TITLE.value(), "Confirmation"},
      {CLOSING_FRAME_MESSAGE.value(), "Close the window?"},
      {STATE_LOADING_FRAME_TITLE.value(), "Notice"},
      {STATE_LOADING_FRAME_MESSAGE.value(), "Saved state found, upload?"},
      {OPTION_YES.value(), "Yes"},
      {OPTION_NO.value(), "No"},
      {LANGUAGE_MENU_TITLE.value(), "Switch language"},
      {LANGUAGE_MENU_ENGLISH.value(), "English"},
      {LANGUAGE_MENU_RUSSIAN.value(), "Русский"},
      {NEXT_TARGET_NAME.value(), "Next target"},
      {POSITION_NAME.value(), "Current position"},
      {WINDOWS_MENU_NAME.value(), "Windows"}
    };
  }
}
