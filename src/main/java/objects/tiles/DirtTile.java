package objects.tiles;

import java.awt.*;

public class DirtTile extends PassableTile {
  @Override
  public Color getColor() {
    return new Color(180, 87, 43);
  }
}
