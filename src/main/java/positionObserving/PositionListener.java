package positionObserving;

import java.awt.*;

public interface PositionListener {
  void update(Point currentPosition, Point nextPosition);
}
