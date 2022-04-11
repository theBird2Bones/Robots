package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;

public class JFrameWithCustomClose extends JFrame {
    public JFrameWithCustomClose(ResourceBundle bundle){
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onCloseAppEvent(JFrameWithCustomClose.this);
            }
        });
    }

    protected void onCloseAppEvent(Component frame){
        CloseFrame.closeApp(frame);
    }
}
