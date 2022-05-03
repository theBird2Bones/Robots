package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;

public class JFrameExtended extends JFrame {
    public JFrameExtended(){
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onCloseAppEvent(JFrameExtended.this);
            }
        });
  }

  protected void onCloseAppEvent(Component frame) {
    CloseFrame.closeApp(frame);
  }
}
