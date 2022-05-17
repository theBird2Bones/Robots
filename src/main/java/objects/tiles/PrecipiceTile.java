package objects.tiles;

import java.awt.*;

public class PrecipiceTile extends NotPassableTile {
  public PrecipiceTile(Point position) {
    super(position);
  }

  @Override
  public Color getColor() {
    return Color.black;
  }
}
