package gui;

import localizer.LocalizationKey;

import java.awt.*;
import java.util.ResourceBundle;

import javax.swing.JPanel;

public class GameWindow extends JInternalFrameExtended {

    public GameWindow(ResourceBundle bundle, Dimension d) {
        super(bundle, bundle.getString(LocalizationKey.GAME_WINDOW_NAME.value()),
                true, true,
                true, true);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new GamePanel(d), BorderLayout.CENTER);
        getContentPane().add(panel);

        pack();
        setSize(d);
    }
}
