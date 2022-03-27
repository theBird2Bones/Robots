package gui;

import localizer.Localizer;

import java.awt.BorderLayout;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class GameWindow extends JInternalFrame {

    public GameWindow(Localizer localizer) {
        super(localizer.getGameWindowsLocalizer().getGameWindowName(),
                true, true, true, true);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new GameVisualizer(), BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }
}
