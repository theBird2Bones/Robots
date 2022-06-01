package gui;

import localizer.ObservableLocalization;

import javax.swing.*;

import static localizer.LocalizationKey.*;

public class StateLoadingFrame {
  public static int showDialogBox() {
    Object[] option =
        new Object[] {
          ObservableLocalization.instance().getBundle().getString(OPTION_YES.value()),
          ObservableLocalization.instance().getBundle().getString(OPTION_NO.value()),
        };

    return JOptionPane.showOptionDialog(
        null,
        ObservableLocalization.instance()
            .getBundle()
            .getString(STATE_LOADING_FRAME_MESSAGE.value()),
        ObservableLocalization.instance().getBundle().getString(STATE_LOADING_FRAME_TITLE.value()),
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,
        option,
        option[0]);
  }
}
