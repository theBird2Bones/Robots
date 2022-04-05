package gui;

import localizer.LocalizationKey;

import java.awt.*;
import java.util.ResourceBundle;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class GameWindow extends JInternalFrame {

    public GameWindow(ResourceBundle bundle, Dimension d) {
        super(bundle.getString(LocalizationKey.GAME_WINDOW_NAME.value()),
                true, true, true, true);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new GamePanel(d), BorderLayout.CENTER);
        getContentPane().add(panel);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                CloseFrame.closeInternalFrame(GameWindow.this, bundle);
            }
        });

        pack();
        setSize(d);
    }
}
