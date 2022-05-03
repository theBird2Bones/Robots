package objects.entities;

import lombok.Getter;
import lombok.Setter;
import objects.tiles.PassableTile;
import objects.tiles.Tile;
import objects.weapons.Weapon;

import java.awt.Point;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Player extends Entity {
  @Getter @Setter private List<Point> path;

  @Setter private Thread work;

  public Player(Point position) {
    super(position, 0, new Weapon(), 0, 0, 0, 0, 0);
  }

  public void start() {
    work.start();
  }

  public void stop() {
    work.stop();
  }

  public static List<Point> createRoute(Player player, Tile[][] map) {
    var i = player.getPosition().y;
    var j = player.getPosition().x;
    var passableTiles =
        Arrays.stream(map)
            .mapToLong(l -> Arrays.stream(l).filter(t -> t instanceof PassableTile).count())
            .sum();

    var visited = new HashSet<Point>();
    var res = new LinkedList<Point>();
    res.add(new Point(j, i));
    while (res.size() != passableTiles) {
      var t = res.getLast();
      visited.add(t);

      var discovered = new LinkedList<Point>();
      discovered.add(new Point(t.x + 1, t.y));
      discovered.add(new Point(t.x, t.y + 1));
      discovered.add(new Point(t.x - 1, t.y));
      discovered.add(new Point(t.x, t.y - 1));
      for (var e :
          discovered.stream()
              .filter(x -> x.x >= 0 && x.y >= 0)
              .filter(x -> map[x.y][x.x] instanceof PassableTile)
              .filter(x -> !visited.contains(x))
              .toList()) {
        res.add(e);
        break;
      }
    }
    return res.stream().map(p -> new Point(p.y, p.x)).toList();
  }
}
