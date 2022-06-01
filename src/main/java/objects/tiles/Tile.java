package objects.tiles;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

public abstract class Tile {
  public static final int SIZE = 32;

  public abstract Color getColor();

  @Getter @Setter private Point position;
  public Tile(Point position){
      this.position = position;
  }
}
