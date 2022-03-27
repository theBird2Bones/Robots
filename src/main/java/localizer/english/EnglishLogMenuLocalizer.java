package localizer.english;

import localizer.LogMenuLocalizer;

public class EnglishLogMenuLocalizer implements LogMenuLocalizer {
    @Override
    public String getSwitcherName() {
        return "Turn on/off log menu";
    }

    @Override
    public String getTestLogButtonName() {
        return "Message in log";
    }
}
