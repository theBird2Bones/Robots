package utility;

import gui.visualizers.GamePanel;
import objects.entities.PlainEnemy;
import objects.tiles.DirtTile;
import objects.tiles.PassableTile;
import objects.tiles.PrecipiceTile;
import objects.tiles.Tile;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MapGenerator {
  private static final int totalWidth = 26;
  private static final int totalHeight = 16;
  private static final int leftOffset = 4;
  private static final int rightOffset = 4;
  private static final int topOffset = 3;
  private static final int bottomOffset = 3;

  private static final int pathWidth = totalWidth - leftOffset - rightOffset;
  private static final int pathHeight = totalHeight - topOffset - bottomOffset;
  private final GamePanel gamePanel;

  public MapGenerator(GamePanel gamePanel) {
    this.gamePanel = gamePanel;
  }

  public static Tile[][] generate() {
    var map = new Tile[totalHeight][totalWidth];
    for (int i = 0; i < totalHeight; i++) {
      for (int j = 0; j < totalWidth; ++j) {
        map[i][j] = new PrecipiceTile(new java.awt.Point(i, j));
      }
    }

    for (int j = 0; j < pathWidth; ++j) {
      map[topOffset][j + rightOffset] =
          new DirtTile(new java.awt.Point(topOffset, j + rightOffset));
    }
    for (int j = 0; j < pathWidth; ++j) {
      map[topOffset + pathHeight - 1][j + rightOffset] =
          new DirtTile(new java.awt.Point(topOffset + pathHeight - 1, j + rightOffset));
    }

    for (int i = 0; i < pathHeight; ++i) {
      map[i + topOffset][rightOffset] =
          new DirtTile(new java.awt.Point(i + topOffset, rightOffset));
    }
    for (int i = 0; i < pathHeight; ++i) {
      map[i + topOffset][rightOffset + pathWidth - 1] =
          new DirtTile(new java.awt.Point(i + topOffset, rightOffset + pathWidth - 1));
    }
    var pointsToBend = generateBends();
    for (var k : pointsToBend.keySet()) {
      bendWay(map, k, pointsToBend.get(k));
    }

    int counter = 1;
    for (var l : map) {
      for (var t : l) {
        if (t instanceof PassableTile) {
          counter++;
          if (counter % 15 == 0) {
            ((PassableTile) t)
                .getEnemies()
                .add(new PlainEnemy(t.getPosition(), 0, null, 100d, 10d, 1, 3d));
          }
        }
      }
    }
    return map;
  }

  private static void bendWay(Tile[][] map, Point start, Point finish) {
    if (start.i == finish.i) {
    } else {
      if (start.i < finish.i) {
        map[finish.i][finish.j] = new DirtTile(new java.awt.Point(finish.i, finish.j));
        map[start.i][start.j] = new PrecipiceTile(new java.awt.Point(start.i, start.j));
        for (int i = start.i + 1; i <= finish.i; ++i) {
          map[i][finish.j - 1] = new DirtTile(new java.awt.Point(i, finish.j - 1));
          map[i][finish.j + 1] = new DirtTile(new java.awt.Point(i, finish.j + 1));
        }
      } else {
        map[finish.i][finish.j] = new DirtTile(new java.awt.Point(finish.i, finish.j));
        map[start.i][start.j] = new PrecipiceTile(new java.awt.Point(start.i, start.j));

        for (int i = start.i - 1; i >= finish.i; --i) {
          map[i][finish.j - 1] = new DirtTile(new java.awt.Point(i, finish.j - 1));
          map[i][finish.j + 1] = new DirtTile(new java.awt.Point(i, finish.j + 1));
        }
      }
    }
  }

  private static Map<Point, Point> generateBends() {
    var res = new HashMap<Point, Point>();
    res.put(
        new Point(topOffset, rightOffset + 3),
        new Point(topOffset + randomBendLength(4), rightOffset + 3));
    res.put(
        new Point(topOffset, rightOffset + 7),
        new Point(topOffset + randomBendLength(3), rightOffset + 7));
    res.put(
        new Point(topOffset, rightOffset + 11),
        new Point(topOffset + randomBendLength(3), rightOffset + 11));

    res.put(
        new Point(topOffset + pathHeight - 1, rightOffset + 3),
        new Point(topOffset + pathHeight - randomBendLength(4) - 1, rightOffset + 3));
    res.put(
        new Point(topOffset + pathHeight - 1, rightOffset + 7),
        new Point(topOffset + pathHeight - randomBendLength(4) - 1, rightOffset + 7));
    res.put(
        new Point(topOffset + pathHeight - 1, rightOffset + 11),
        new Point(topOffset + pathHeight - randomBendLength(4) - 1, rightOffset + 11));

    return res;
  }

  private static int randomBendLength(int maxLength) {
    var r = new Random();
    return r.nextInt(1, maxLength + 1);
  }

  public Map<Point2D, Rectangle> generatePointToRectangle() {
    var result = new HashMap<Point2D, Rectangle>();
    var map = gamePanel.getMap();

    for (int x = 0; x < map.length; x++) {
      for (int y = 0; y < map[0].length; y++) {
        var rec = new Rectangle(x * Tile.SIZE, y * Tile.SIZE, Tile.SIZE, Tile.SIZE);
        result.put(new java.awt.Point(x, y), rec);
      }
    }
    return result;
  }

  private record Point(int i, int j) {
    // because matrix is
    /*
    |a11 a12|
    |a21 a22|
     */
  }
}
