package localizer.english;

import localizer.CloseFrameLocalizer;

public class EnglishCloseFrameLocalizer implements CloseFrameLocalizer {
    @Override
    public Object[] getOptionsName() {
        return new Object[]{"Yes", "No"};
    }

    @Override
    public String getMessage() {
        return "Close the window?";
    }

    @Override
    public String getTitle() {
        return "Confirmation";
    }
}
