package objects.tiles;

import java.awt.*;

public class DirtTile extends PassableTile {
  public DirtTile(Point position) {
    super(position);
  }

  @Override
  public Color getColor() {
    return new Color(255, 215, 0);
  }
}
