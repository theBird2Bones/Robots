package gui.innerWindows;

import gui.visualizers.FightPanel;
import objects.entities.Entity;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import java.awt.*;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class FightWindow extends JInternalFrame implements AutoCloseable {

  public FightWindow(ResourceBundle bundle, Dimension d,
                     AtomicReference<Entity> player,
                     List<AtomicReference<Entity>> enemies) {
    ((BasicInternalFrameUI) getUI()).setNorthPane(null);
    setBorder(null);

    JPanel panel = new JPanel(new BorderLayout());
    panel.add(new FightPanel(d, player, enemies), BorderLayout.CENTER);
    getContentPane().add(panel);

    pack();
    setSize(d);
  }

  @Override
  public void close() throws Exception {
    this.setClosed(true);
  }
}
