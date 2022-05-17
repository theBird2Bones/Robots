package objects.entities;

import motionObserving.MotionListener;
import positionObserving.PositionListener;
import positionObserving.PositionNotifier;
import gui.innerWindows.CoordinatingWindow;
import lombok.Getter;
import lombok.Setter;
import objects.tiles.PassableTile;
import objects.tiles.Tile;
import objects.weapons.Weapon;

import java.awt.Point;
import java.util.*;

public class Player extends Entity implements PositionNotifier, MotionListener {
  @Setter private List<Point> path;
  private Iterator<Point> pathIterator;
  private Point nextPosition;

  @Getter @Setter private CoordinatingWindow coordinatingWindow;
  @Getter @Setter private List<PositionListener> positionListeners = new LinkedList<>();

  public Player(Point position) {
    super(position, 0, new Weapon(), 300, 15, 1, 2);
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

  public void updateNextPosition(){
    if(pathIterator == null || !pathIterator.hasNext()){
      pathIterator = path.iterator();
    }
    if(nextPosition == null){
      nextPosition = pathIterator.next();
    }
    setPosition(nextPosition);
    nextPosition = pathIterator.next();
  }

  public Point getNextPosition(){
    if (nextPosition == null){
      updateNextPosition();
    }
    return nextPosition;
  }

  @Override
  public void notifyPosition() {
    for(var l: positionListeners){
      l.update(this.getPosition(), this.getNextPosition());
    }
  }

  @Override
  public void subscribe(PositionListener listener) {
    positionListeners.add(listener);
  }

  @Override
  public void move() {
    updateNextPosition();
    notifyPosition();
  }
}
