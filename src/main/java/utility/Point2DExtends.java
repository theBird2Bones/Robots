package utility;

import java.awt.*;
import java.awt.geom.Point2D;

public class Point2DExtends {
  public static Point roundWithTranslate(Point2D.Double p, int dx, int dy) {
    return new Point(round(p.x + dx), round(p.y + dy));
  }

  public static Point round(Point2D.Double p) {
    return new Point(round(p.x), round(p.y));
  }

  private static int round(double value) {
    return (int) (value + 0.5);
  }
}
