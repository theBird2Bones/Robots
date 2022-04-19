package gui;

import localizer.LocalizationKey;
import utility.ObservableLocalization;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

import static localizer.LocalizationKey.*;

public class CloseFrame {
  public static void closeApp(Component frame) {
    if (showDialogBox(frame) == 0) {
      frame.setVisible(false);
      System.exit(0);
    }
  }

  public static void closeInternalFrame(JInternalFrame frame) {
    if (showDialogBox(frame) == 0) {
      frame.setVisible(false);
      frame.dispose();
    }
  }

  private static int showDialogBox(Component frame) {
    Object[] option =
        new Object[] {
          ObservableLocalization.instance().getBundle().getString(CLOSING_FRAME_OPTION_YES.value()),
          ObservableLocalization.instance().getBundle().getString(CLOSING_FRAME_OPTION_NO.value()),
        };
    return JOptionPane.showOptionDialog(
        frame,
        ObservableLocalization.instance().getBundle().getString(CLOSING_FRAME_MESSAGE.value()),
        ObservableLocalization.instance().getBundle().getString(CLOSING_FRAME_TITLE.value()),
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,
        option,
        option[0]);
  }
}
