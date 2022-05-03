package objects.tiles;

import java.awt.*;

public class DirtTile extends PassableTile {
  @Override
  public Color getColor() {
    return new Color(255, 215, 0);
  }
}
