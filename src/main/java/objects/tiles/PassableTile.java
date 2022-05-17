package objects.tiles;

import lombok.Getter;
import objects.entities.Enemy;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public abstract class PassableTile extends Tile {
  @Getter private List<Enemy> enemies = new LinkedList<>();

  public PassableTile(Point position) {
    super(position);
  }
}
