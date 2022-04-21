package gui;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.util.ResourceBundle;

public class JInternalFrameExtended extends JInternalFrame {
    public JInternalFrameExtended(ResourceBundle bundle, String title,
                                  boolean resizable, boolean closable,
                                  boolean maximizable, boolean iconifiable){
        super(title, resizable, closable, maximizable, iconifiable);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                CloseFrame.closeInternalFrame(JInternalFrameExtended.this);
            }
        });
    }
}
