package gui;

import localizer.Localizer;

import java.awt.BorderLayout;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class GameWindow extends JInternalFrame {

    public GameWindow(Localizer localizer) {
        super(localizer.getGameWindowsLocalizer().getGameWindowName(),
                true, true, true, true);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new GameVisualizer(), BorderLayout.CENTER);
        getContentPane().add(panel);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                CloseFrame.closeInternalFrame(GameWindow.this, localizer);
            }
        });

        pack();
    }
}
