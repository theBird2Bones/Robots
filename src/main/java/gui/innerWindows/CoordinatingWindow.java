package gui.innerWindows;

import positionObserving.PositionListener;
import localizer.LocalizationKey;
import log.Logger;
import localizer.ChangingLanguage;
import localizer.ObservableLocalization;

import java.awt.*;
import java.util.ResourceBundle;

public class CoordinatingWindow extends LogWindow implements ChangingLanguage, PositionListener {
  public CoordinatingWindow(ResourceBundle bundle) {
    super(Logger.getDefaultLogSource(), bundle);
    this.title = bundle.getString(LocalizationKey.COORDINATING_WINDOW_NAME.value());
  }


  @Override
  public void changeLanguageWith(ResourceBundle bundle) {
    this.title = bundle.getString(LocalizationKey.COORDINATING_WINDOW_NAME.value());
  }

  @Override
  public void update(Point currentPosition, Point nextPosition) {
    var bundle = ObservableLocalization.instance().getBundle();
    logContent.setText(
        "%-19s: %s%n%-21s: %s"
            .formatted(
                bundle.getString(LocalizationKey.POSITION_NAME.value()),
                "(%s, %s)" .formatted(currentPosition.getX(), currentPosition.getY()),
                bundle.getString(LocalizationKey.NEXT_TARGET_NAME.value()),
                "(%s, %s)" .formatted(nextPosition.getX(), nextPosition.getY())
            ));
    logContent.invalidate();
  }


  @Override
  protected void updateLogContent() { }
}
