package localization;

import java.util.ListResourceBundle;

public class Localization_en extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"configMenuName", "Settings"},
                {"exitButtonName", "Exit"},

                {"viewModeMenuName", "View Modes"},
                {"universalSchemeName", "Universal scheme"},
                {"systemSchemeName", "System scheme"},

                {"switcherName", "Turn on/off log menu"},
                {"testLogButtonName", "Message in log"},

                {"gameWindowName", "Game window"},

                {"closingFrameTitle", "Confirmation"},
                {"closingFrameMessage", "Close the window?"},
                {"closingFrameOptionYes", "Yes"},
                {"closingFrameOptionNo", "No"},

                {"testMenuName", "Tests"}
        };
    }
}
