package gui.visualizers;

import gui.innerWindows.CoordinatingWindow;
import lombok.Getter;
import objects.entities.Player;
import objects.tiles.Tile;
import utility.GameManager;
import utility.InternalFramesManager;
import utility.MapGenerator;
import utility.PlayerManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

public class GamePanel extends JPanel {
  private final ArenaPainter arenaPainter;
  private final Timer timer = initTimer();
  @Getter private final Tile[][] map;
  @Getter private PlayerManager playerManager;

  public GamePanel(Dimension d, MapGenerator mapGenerator, PlayerManager playerManager, GameManager gameManager) {
    setSize(d);
    setBackground(Color.black);

    this.playerManager = playerManager;
    map = mapGenerator.getMap();
    gameManager.startTick();
    arenaPainter = new ArenaPainter(this, mapGenerator, playerManager);

    timer.schedule(
        new TimerTask() {
          @Override
          public void run() {
            if (!isFocusable()) {
              requestFocusInWindow();
            }
            onRedrawEvent();
          }
        },
        0,
        16);

    addMouseListener(
        new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            requestFocusInWindow();
          }
        });
  }

  private static Timer initTimer() {
    return new Timer("game paint event generator", true);
  }


  protected void onRedrawEvent() {
    EventQueue.invokeLater(this::repaint);
  }

  public Dimension getMapSize() {
    return new Dimension(map.length, map[0].length);
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    arenaPainter.paint((Graphics2D) g);
  }
}
