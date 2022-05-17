package gui.innerWindows;

import gui.visualizers.FightPanel;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import java.awt.*;
import java.util.ResourceBundle;

public class FightWindow extends JInternalFrame implements AutoCloseable {

  public FightWindow(ResourceBundle bundle, Dimension d) {
    ((BasicInternalFrameUI) getUI()).setNorthPane(null);
    setBorder(null);

    JPanel panel = new JPanel(new BorderLayout());
    panel.add(new FightPanel(d), BorderLayout.CENTER);
    getContentPane().add(panel);

    pack();
    setSize(d);
  }

  @Override
  public void close() throws Exception {
    this.setClosed(true);
  }
}
