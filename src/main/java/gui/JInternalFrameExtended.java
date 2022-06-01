package gui;

import utility.storage.StorableController;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.beans.PropertyVetoException;
import java.util.ResourceBundle;

public class JInternalFrameExtended extends JInternalFrame {
    private boolean isClosingNotifyShown = true;

    public JInternalFrameExtended(ResourceBundle bundle, String title,
                                  boolean resizable, boolean closable,
                                  boolean maximizable, boolean iconifiable){
        super(title, resizable, closable, maximizable, iconifiable);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                if(isClosingNotifyShown){
                    CloseFrame.closeInternalFrame(JInternalFrameExtended.this);
                }
            }
        });

        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameIconified(InternalFrameEvent e) {
                try {
                    setIcon(false);
                } catch (PropertyVetoException ex) {
                    ex.printStackTrace();
                }
                setVisible(false);
            }
        });

        StorableController.instance().addListener(this);
        StorableController.instance().setUpFrame(this);
    }

    public void setIsClosingNotification(boolean isShown){
        isClosingNotifyShown = isShown;
    }
}
