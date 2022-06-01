package gui.innerWindows;

import gui.JInternalFrameExtended;
import gui.visualizers.GamePanel;
import localizer.LocalizationKey;

import java.awt.*;
import java.util.ResourceBundle;

import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicInternalFrameUI;

public class GameWindow extends JInternalFrameExtended {

    public GameWindow(ResourceBundle bundle, Dimension d) {
        super(bundle, bundle.getString(LocalizationKey.GAME_WINDOW_NAME.value()),
                true, true,
                true, true);


        ((BasicInternalFrameUI)getUI()).setNorthPane(null);
        setBorder(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new GamePanel(d), BorderLayout.CENTER);
        getContentPane().add(panel);

        pack();
        setSize(d);
    }
}
