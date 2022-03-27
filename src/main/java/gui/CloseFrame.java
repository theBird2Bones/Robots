package gui;

import localizer.Localizer;

import javax.swing.*;
import java.awt.*;

public class CloseFrame {
    public static void closeApp(Component frame, Localizer localizer){
        if(showDialogBox(frame, localizer) == 0){
            frame.setVisible(false);
            System.exit(0);
        }
    }

    public static void closeInternalFrame(JInternalFrame frame, Localizer localizer){
        if(showDialogBox(frame, localizer) == 0){
            frame.setVisible(false);
            frame.dispose();
        }
    }

    private static int showDialogBox(Component frame, Localizer localizer){
        Object[] option = {"Да", "Нет"};
        return JOptionPane.showOptionDialog(
                frame,
                "Закрыть окно?",
                "Подтверждение",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                option,
                option[0]
        );
    }
}