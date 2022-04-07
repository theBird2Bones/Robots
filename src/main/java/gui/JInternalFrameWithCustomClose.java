package gui;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import java.util.ResourceBundle;

public class JInternalFrameWithCustomClose extends JInternalFrame {
    public JInternalFrameWithCustomClose(ResourceBundle bundle){
        this(bundle, "", false, false, false, false);
    }

    public JInternalFrameWithCustomClose(ResourceBundle bundle, String title){
        this(bundle, title, false, false, false, false);
    }

    public JInternalFrameWithCustomClose(ResourceBundle bundle, String title, boolean resizable){
        this(bundle, title, resizable, false, false, false);
    }

    public JInternalFrameWithCustomClose(ResourceBundle bundle, String title,
                                         boolean resizable, boolean closable){
        this(bundle, title, resizable, closable, false, false);
    }

    public JInternalFrameWithCustomClose(ResourceBundle bundle, String title,
                                         boolean resizable, boolean closable,
                                         boolean maximizable){
        this(bundle, title, resizable, closable, maximizable, false);
    }

    public JInternalFrameWithCustomClose(ResourceBundle bundle, String title,
                                         boolean resizable, boolean closable,
                                         boolean maximizable, boolean iconifiable){
        super(title, resizable, closable, maximizable, iconifiable);

        ((BasicInternalFrameUI)getUI()).setNorthPane(null);
        setBorder(null);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                CloseFrame.closeInternalFrame(JInternalFrameWithCustomClose.this, bundle);
            }
        });
    }
}
