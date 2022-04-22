package utility;

import objects.tiles.DirtTile;
import objects.tiles.PrecipiceTile;
import objects.tiles.Tile;

import java.util.*;

public class MapGenerator {
  private static final int totalWidth = 26;
  private static final int totalHeight = 16;
  private static final int leftOffset = 4;
  private static final int rightOffset = 4;
  private static final int topOffset = 3;
  private static final int bottomOffset = 3;

  private static final int pathWidth = totalWidth - leftOffset - rightOffset;
  private static final int pathHeight = totalHeight - topOffset - bottomOffset;

  private static HashSet<Point> hashedRoute = new HashSet<>();
  public static Tuple<Tile[][], List<Tuple<Integer, Integer>>> generate() {
    var map = new Tile[totalHeight][totalWidth];
    for (int i = 0; i < totalHeight; i++) {
      for (int j = 0; j < totalWidth; ++j) {
        map[i][j] = new PrecipiceTile();
      }
    }

    for (int j = 0; j < pathWidth; ++j) {
      map[topOffset][j + rightOffset] = new DirtTile();
      hashedRoute.add(new Point(topOffset, j + rightOffset));
    }
    for (int j = 0; j < pathWidth; ++j) {
      map[topOffset + pathHeight - 1][j + rightOffset] = new DirtTile();
      hashedRoute.add(new Point(topOffset + pathHeight - 1, j + rightOffset));
    }

    for (int i = 0; i < pathHeight; ++i) {
      map[i + topOffset][rightOffset] = new DirtTile();
      hashedRoute.add(new Point(i + topOffset, rightOffset));
    }
    for (int i = 0; i < pathHeight; ++i) {
      map[i + topOffset][rightOffset + pathWidth - 1] = new DirtTile();
      hashedRoute.add(new Point(i + topOffset, rightOffset + pathWidth - 1));
    }
    var pointsToBend = generateBends();
    for (var k : pointsToBend.keySet()) {
      bendWay(map, k, pointsToBend.get(k));
    }
    return new Tuple(map,createRoute(hashedRoute));
  }

  private static List<Tuple<Integer, Integer>> createRoute(Set<Point> hashed){
    var res = new LinkedList<Tuple<Integer, Integer>>();
    var visited = new HashSet<Point>();
    var start = new Point(topOffset, rightOffset);
    while(res.size() != hashed.size()){
      var a = new Point(start.i, start.j+1);
      if(hashed.contains(a) && !visited.contains(a)){
        res.add(new Tuple<>(a.i - start.i, a.j - start.j));
        visited.add(a);
        start = a;
        continue;
      }
      a = new Point(start.i + 1, start.j());
      if(hashed.contains(a)  && !visited.contains(a)){
        res.add(new Tuple<>(a.i - start.i, a.j - start.j));
        visited.add(a);
        start = a;
        continue;
      }
      a = new Point(start.i - 1, start.j());
      if(hashed.contains(a) && !visited.contains(a)){
        res.add(new Tuple<>(a.i - start.i, a.j - start.j));
        visited.add(a);
        start = a;
        continue;
      }
      a = new Point(start.i, start.j() - 1);
      if(hashed.contains(a) && !visited.contains(a)){
        res.add(new Tuple<>(a.i - start.i, a.j - start.j));
        visited.add(a);
        start = a;
      }
    }
    return res;
  }

  private static void bendWay(Tile[][] map, Point start, Point finish) {
    if (start.i == finish.i) {
    } else {
      if (start.i < finish.i) {
        map[finish.i][finish.j] = new DirtTile();
        map[start.i][start.j] = new PrecipiceTile();
        hashedRoute.add(new Point(finish.i, finish.j));
        hashedRoute.remove(new Point(start.i, start.j));

        for (int i = start.i + 1; i <= finish.i; ++i) {
          map[i][finish.j - 1] = new DirtTile();
          map[i][finish.j + 1] = new DirtTile();
          hashedRoute.add(new Point(i, finish.j-1));
          hashedRoute.add(new Point(i, start.j+1));
        }
      } else {
        map[finish.i][finish.j] = new DirtTile();
        map[start.i][start.j] = new PrecipiceTile();
        hashedRoute.add(new Point(finish.i, finish.j));
        hashedRoute.remove(new Point(start.i, start.j));

        for (int i = start.i - 1; i >= finish.i; --i) {
          map[i][finish.j - 1] = new DirtTile();
          map[i][finish.j + 1] = new DirtTile();
          hashedRoute.add(new Point(i, finish.j-1));
          hashedRoute.add(new Point(i, start.j+1));
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

  private static record Point(int i, int j) {
    // because matrix is
    /*
    |a11 a12|
    |a21 a22|
     */
  }
}
