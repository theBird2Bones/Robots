package gui.innerWindows;

import gui.JInternalFrameWithCustomClose;
import gui.visualizers.GamePanel;
import localizer.LocalizationKey;

import java.awt.*;
import java.util.ResourceBundle;

import javax.swing.JPanel;

public class GameWindow extends JInternalFrameWithCustomClose {

    public GameWindow(ResourceBundle bundle, Dimension d) {
        super(bundle, bundle.getString(LocalizationKey.GAME_WINDOW_NAME.value()),
                true, true,
                true, true);

        JPanel panel = new JPanel(new BorderLayout());
//        panel.add(new RobotPanel(d), BorderLayout.CENTER);
        panel.add(new GamePanel(), BorderLayout.CENTER);
        getContentPane().add(panel);

        pack();
        setSize(d);
    }
}
