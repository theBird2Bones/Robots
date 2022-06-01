package gui.visualizers;

import gui.innerWindows.CoordinatingWindow;
import lombok.Getter;
import objects.entities.Player;
import objects.tiles.PassableTile;
import objects.tiles.Tile;
import utility.GameManager;
import utility.InternalFramesManager;
import utility.MapGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class GamePanel extends JPanel {
  private final ArenaPainter arenaPainter;
  private final Timer timer = initTimer();

  private final MapGenerator mapCreator;

  @Getter private final Tile[][] map;

  @Getter private final Map<Point2D, Rectangle> pointToRectangle;

  @Getter private final Player player;

  public GamePanel(Dimension d) {
    setSize(d);
    setBackground(Color.black);
    map = MapGenerator.generate();
    mapCreator = new MapGenerator(this);

    pointToRectangle = mapCreator.generatePointToRectangle();

    player = new Player(findAvailablePoint());
    player.subscribe(InternalFramesManager.instance().getFrameInstance(CoordinatingWindow.class));
    player.setPath(Player.createRoute(player, map));

    var configuredGameManager = GameManager.configInstance(map, player, true, 200);
    arenaPainter = new ArenaPainter(this);

    configuredGameManager.subscribe(player);
    configuredGameManager.startTick();

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

  private Point findAvailablePoint() {
    for (int x = 0; x < map.length; x++) {
      for (int y = 0; y < map[0].length; y++) {
        if (map[x][y] instanceof PassableTile) {
          return new Point(y, x);
        }
      }
    }
    return new Point(0, 0);
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
