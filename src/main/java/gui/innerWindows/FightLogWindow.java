package gui.innerWindows;


import localizer.LocalizationKey;
import log.FightListener;
import log.Logger;

import java.util.ResourceBundle;

public class FightLogWindow extends LogWindow implements FightListener {
  public FightLogWindow (ResourceBundle bundle) {
    super(Logger.getDefaultLogSource(), bundle);
    this.title = bundle.getString(LocalizationKey.COORDINATING_WINDOW_NAME.value());
  }

  @Override
  public void notifyWith(String message) {
    logContent.setText("%s%n%s%n".formatted(logContent.getText(), message));
    logContent.invalidate();
  }
}
