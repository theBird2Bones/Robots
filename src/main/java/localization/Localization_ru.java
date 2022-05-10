package localization;

import java.util.ListResourceBundle;

import static localizer.LocalizationKey.*;

public class Localization_ru extends ListResourceBundle {
  @Override
  protected Object[][] getContents() {
    return new Object[][] {
      {CONFIG_MENU_NAME.value(), "Настройки"},
      {EXIT_BUTTON_NAME.value(), "Выход"},
      {VIEW_MODE_MENU_NAME.value(), "Режим Отображения"},
      {UNIVERSAL_SCHEME_NAME.value(), "Универсальная схема"},
      {SYSTEM_SCHEME_NAME.value(), "Системная схема"},
      {LOG_SWITCHER_NAME.value(), "Вкл / Выкл окно логов"},
      {GAME_SWITCHER_NAME.value(), "Вкл / Выкл окно игры"},
      {TEST_LOG_BUTTON_NAME.value(), "Сообщение в лог"},
      {GAME_WINDOW_NAME.value(), "Меню игры"},
      {COORDINATING_WINDOW_NAME.value(), "Координаты робота"},
      {LOG_WINDOW_NAME.value(), "Протокол работы"},
      {CLOSING_FRAME_TITLE.value(), "Подтверждение"},
      {CLOSING_FRAME_MESSAGE.value(), "Закрыть окно?"},
      {STATE_LOADING_FRAME_TITLE.value(), "Уведомление"},
      {STATE_LOADING_FRAME_MESSAGE.value(), "Найдено сохраненное состояние, загрузить?"},
      {OPTION_YES.value(), "Да"},
      {OPTION_NO.value(), "Нет"},
      {LANGUAGE_MENU_TITLE.value(), "Смена языка"},
      {LANGUAGE_MENU_ENGLISH.value(), "English"},
      {LANGUAGE_MENU_RUSSIAN.value(), "Русский"},
      {POSITION_NAME.value(), "Текущая позиция"},
      {NEXT_TARGET_NAME.value(), "Следующая цель"},
      {WINDOWS_MENU_NAME.value(), "Окна"}
    };
  }
}
