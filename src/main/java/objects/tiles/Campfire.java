package objects.tiles;

import java.awt.*;

public class Campfire extends PassableTile {
  public Campfire(Point position) {
    super(position);
  }

  @Override
  public Color getColor() {
    return new Color(180, 16, 140);
  }
}
