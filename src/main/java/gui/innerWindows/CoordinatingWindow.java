package gui.innerWindows;

import localizer.LocalizationKey;
import localizer.LocalizationManager;
import log.Logger;
import objects.entities.Player;
import utility.ObservableLocalization;

import java.awt.*;
import java.util.ResourceBundle;

public class CoordinatingWindow extends LogWindow {
  public CoordinatingWindow(ResourceBundle bundle) {
    super(Logger.getDefaultLogSource(), bundle);
  }

  public void updateLogContent(Player player, Point nextPosition) {
    var bundle = ObservableLocalization.instance().getBundle();
    logContent.setText(
        "%s: %s%n%s: %s"
            .formatted(
                bundle.getString(LocalizationKey.POSITION_NAME.value()),
                "(%s, %s)" .formatted(player.getPosition().getX(), player.getPosition().getY()),
                bundle.getString(LocalizationKey.NEXT_TARGET_NAME.value()),
                "(%s, %s)" .formatted(nextPosition.getX(), nextPosition.getY())
            ));
    logContent.invalidate();
  }
}
