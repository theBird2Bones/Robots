package gui.innerWindows;

import gui.JInternalFrameExtended;
import gui.visualizers.GamePanel;
import localizer.LocalizationKey;
import objects.entities.Player;
import utility.GameManager;
import utility.MapGenerator;
import utility.PlayerManager;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import java.awt.*;
import java.util.ResourceBundle;

public class GameWindow extends JInternalFrameExtended {

  public GameWindow(
      ResourceBundle bundle,
      Dimension d,
      MapGenerator mapGenerator,
      PlayerManager playerManager,
      GameManager gameManager) {
    super(
        bundle, bundle.getString(LocalizationKey.GAME_WINDOW_NAME.value()), true, true, true, true);

    ((BasicInternalFrameUI) getUI()).setNorthPane(null);
    setBorder(null);

    JPanel panel = new JPanel(new BorderLayout());
    panel.add(new GamePanel(d, mapGenerator, playerManager, gameManager), BorderLayout.CENTER);
    getContentPane().add(panel);

    pack();
    setSize(d);
  }
}
