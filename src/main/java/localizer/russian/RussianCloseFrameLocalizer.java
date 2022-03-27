package localizer.russian;

import localizer.CloseFrameLocalizer;

public class RussianCloseFrameLocalizer implements CloseFrameLocalizer {
    @Override
    public Object[] getOptionsName() {
        return new Object[]{"Да", "Нет"};
    }

    @Override
    public String getMessage() {
        return "Закрыть окно?";
    }

    @Override
    public String getTitle() {
        return "Подтверждение";
    }
}
