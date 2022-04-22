package gui;

import utility.InternalFramesManager;
import utility.ObservableLocalization;
import utility.storage.StorableController;

import javax.swing.*;
import java.awt.*;

import static localizer.LocalizationKey.*;
public class CloseFrame {
    public static void closeApp(Component frame){
        if(showDialogBox(frame) == 0){
            frame.setVisible(false);
            StorableController.instance().saveListeners();
            System.exit(0);
        }
    }

    public static void closeInternalFrame(JInternalFrame frame){
        if(showDialogBox(frame) == 0){
            frame.setVisible(false);
            frame.dispose();
            InternalFramesManager.instance().unregisterFrame(frame.getClass());
        }
    }

    private static int showDialogBox(Component frame){
        Object[] option = new Object[] {
                ObservableLocalization.instance().getBundle().getString(OPTION_YES.value()),
                ObservableLocalization.instance().getBundle().getString(OPTION_NO.value()),

        };
        return JOptionPane.showOptionDialog(
                frame,
                ObservableLocalization.instance().getBundle().getString(CLOSING_FRAME_MESSAGE.value()),
                ObservableLocalization.instance().getBundle().getString(CLOSING_FRAME_TITLE.value()),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                option,
                option[0]
        );
    }
}
