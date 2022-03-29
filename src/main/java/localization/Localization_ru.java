package localization;

import java.util.ListResourceBundle;

public class Localization_ru extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"configMenuName", "Настройки"},
                {"exitButtonName", "Выход"},

                {"viewModeMenuName", "Режим Отображения"},
                {"universalSchemeName", "Универсальная схема"},
                {"systemSchemeName", "Системная схема"},

                {"switcherName", "Вкл / Выкл лог"},
                {"testLogButtonName", "Сообщение в лог"},

                {"gameWindowName", "Меню игры"},

                {"closingFrameTitle", "Подтверждение"},
                {"closingFrameMessage", "Закрыть окно?"},
                {"closingFrameOptionYes", "Да"},
                {"closingFrameOptionNo", "Нет"},

                {"testMenuName", "Тесты"}
        };
    }
}