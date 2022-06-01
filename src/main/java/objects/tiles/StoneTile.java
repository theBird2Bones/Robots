package objects.tiles;

import java.awt.*;

public class StoneTile extends PassableTile {
  public StoneTile(Point position) {
    super(position);
  }

  @Override
  public Color getColor() {
    return Color.gray;
  }
}
