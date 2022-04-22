package gui;

import localizer.LocalizationManager;
import utility.ObservableLocalization;
import utility.storage.StorableController;

import java.awt.Frame;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class RobotsProgram {
    public static void main(String[] args) {
        try {
//      UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
//        UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        ObservableLocalization.instance().changeLocale(StorableController.instance().loadLocale());

    SwingUtilities.invokeLater(
        () -> {
          MainApplicationFrame frame =
              new MainApplicationFrame(ObservableLocalization.instance().getBundle());
          frame.pack();
          frame.setVisible(true);
          frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        });
    }
}
