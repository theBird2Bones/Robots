package gui.visualizers;

import gui.MainApplicationFrame;
import gui.innerWindows.CoordinatingWindow;
import lombok.Getter;
import objects.entities.Player;
import objects.tiles.PassableTile;
import objects.tiles.Tile;
import utility.InternalFramesManager;
import utility.MapGenerator;
import utility.ObservableLocalization;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class GamePanel extends JPanel {
  private final ArenaPainter arenaPainter;
  private final Timer timer = initTimer();

  private MapGenerator mapCreator;

  @Getter private Tile[][] map;

  @Getter private Map<Point2D, Rectangle> pointToRectangle;

  @Getter private Player player;

  private static Timer initTimer() {
    return new Timer("game paint event generator", true);
  }

  public GamePanel(Dimension d) {
    setSize(d);
    setBackground(Color.black);
    map = MapGenerator.generate();
    mapCreator = new MapGenerator(this);
    pointToRectangle = mapCreator.generatePointToRectangle();
    player = new Player(findAvailablePoint());
    player.getPositionListeners().add((InternalFramesManager.instance().getFrameInstance(CoordinatingWindow.class))) ;
    player.setPath(Player.createRoute(player, map));
    player.setWork(
        new Thread(
            () -> {
              try {
                player.updateNextPosition();
                var current = player.getNextPosition();
                while (true) {
                  player.updateNextPosition();
                  var nextPos = player.getNextPosition();
                  player.notifyPosition();
                  for (int i = 0; i < 100; ++i) {
                    player.setFactPosition(
                        new Point2D.Double(
                            current.x + (nextPos.x - current.x) / 100d * i,
                            current.y + (nextPos.y - current.y) / 100d * i));
                    Thread.sleep(4);
                  }
                  current = nextPos;
/*
                  var prev = player.getNextPosition();
                  for (var e : player.getPath().stream().skip(1).toList()) {
                    player.log(e);
                    for (int i = 0; i < 100; ++i) {
                      player.setFactPosition(
                          new Point2D.Double(
                              prev.x + (e.x - prev.x) / 100d * i,
                              prev.y + (e.y - prev.y) / 100d * i));
                      Thread.sleep(4);
                    }
                    player.setPosition(e);
                    prev = e;
                  }
*/
                }
              } catch (InterruptedException e) {
                System.out.println("oh my");
              }
            }));

    arenaPainter = new ArenaPainter(this);
    player.start();

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
        50);

    addMouseListener(
        new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            requestFocusInWindow();
          }
        });

    addKeyListener(
        new KeyAdapter() {
          @Override
          public void keyPressed(KeyEvent e) {
/*
            if (e.getKeyCode() == KeyEvent.VK_L) {
              player.stop();
              map = MapGenerator.generate();
              player.setPath(Player.createRoute(player, map));
              pointToRectangle = mapCreator.generatePointToRectangle();
              player.setPosition(findAvailablePoint());

              arenaPainter.updateBackground();
              player.setWork(
                  new Thread(
                      () -> {
                        try {
                          while (true) {
                            var prev = player.getPath().get(0);
                            for (var t1 : player.getPath().stream().skip(1).toList()) {
                              for (int i = 0; i < 100; ++i) {
                                player.setFactPosition(
                                    new Point2D.Double(
                                        prev.x + (t1.x - prev.x) / 100d * i,
                                        prev.y + (t1.y - prev.y) / 100d * i));
                                Thread.sleep(4);
                              }
                              player.setPosition(t1);
                              prev = t1;
                            }
                          }
                        } catch (InterruptedException t1) {
                          System.out.println("oh my");
                        }
                      }));

              player.start();
              onRedrawEvent();
            }
*/
          }
        });
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
