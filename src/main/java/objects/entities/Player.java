package objects.entities;

import gui.innerWindows.CoordinatingWindow;
import lombok.Getter;
import lombok.Setter;
import motionObserving.MotionNotifier;
import objects.tiles.PassableTile;
import objects.tiles.Tile;
import objects.weapons.Weapon;
import positionObserving.PositionListener;
import positionObserving.PositionNotifier;

import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public abstract class Player extends Entity implements PositionNotifier, MotionNotifier {
  @Setter private List<Point> path;
  private Iterator<Point> pathIterator;
  private Point nextPosition;

  @Getter
  protected Color color;

  @Getter @Setter private CoordinatingWindow coordinatingWindow;
  @Getter @Setter private List<PositionListener> positionListeners = new LinkedList<>();

  public Player(){
    this(new Point(0,0));
  }
  public Player(Point position) {
    super(position, 0, new Weapon(), 150, 15, 1, 2);
    color = Color.BLUE;
  }

  public void syncWith(Player another){
    another.path = path;
    another.pathIterator = pathIterator;
    another.nextPosition = nextPosition;
    another.coordinatingWindow = coordinatingWindow;
    another.positionListeners = positionListeners;

    another.setPosition(getPosition());
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

  public void updateNextPosition() {
    if (pathIterator == null || !pathIterator.hasNext()) {
      pathIterator = path.iterator();
    }
    if (nextPosition == null) {
      nextPosition = pathIterator.next();
    }
    setPosition(nextPosition);
    nextPosition = pathIterator.next();
  }

  public Point getNextPosition() {
    if (nextPosition == null) {
      updateNextPosition();
    }
    return nextPosition;
  }

  @Override
  public void notifyPosition() {
    for (var l : positionListeners) {
      l.update(this.getPosition(), this.getNextPosition());
    }
  }

  @Override
  public void subscribe(PositionListener listener) {
    positionListeners.add(listener);
  }

  @Override
  public void move() {
    setHealth(getHealth() + 2);
    updateNextPosition();
    notifyPosition();
  }

  @Override
  public String toString() {
    return "Player";
  }
}
