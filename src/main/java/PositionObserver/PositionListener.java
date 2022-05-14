package PositionObserver;

import java.awt.*;
import java.awt.geom.Point2D;

public interface PositionListener {
  void update(Point currentPosition, Point nextPosition);
}
