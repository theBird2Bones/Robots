package localizer.russian;

import localizer.LogMenuLocalizer;

public class RussianLogMenuLocalizer implements LogMenuLocalizer {
    @Override
    public String getSwitcherName() {
        return "Вкл/Выкл меню логов";
    }

    @Override
    public String getTestLogButtonName() {
        return "Сообщение в лог";
    }
}
